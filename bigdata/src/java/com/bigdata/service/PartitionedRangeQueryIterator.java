/**

Copyright (C) SYSTAP, LLC 2006-2007.  All rights reserved.

Contact:
     SYSTAP, LLC
     4501 Tower Road
     Greensboro, NC 27410
     licenses@bigdata.com

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; version 2 of the License.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/
package com.bigdata.service;

import java.util.NoSuchElementException;

import org.apache.log4j.Logger;

import com.bigdata.btree.AbstractKeyRangeIndexProcedure;
import com.bigdata.btree.BytesUtil;
import com.bigdata.btree.DelegateTuple;
import com.bigdata.btree.ITuple;
import com.bigdata.btree.ITupleFilter;
import com.bigdata.btree.ITupleIterator;
import com.bigdata.io.SerializerUtil;
import com.bigdata.journal.NoSuchIndexException;
import com.bigdata.mdi.PartitionLocator;
import com.bigdata.util.InnerCause;

/**
 * Class supports range query across one or more index partitions.
 * <p>
 * Each partition is mapped onto a single {@link DataServiceRangeIterator}
 * query. In turn, the {@link DataServiceRangeIterator} may make several queries
 * to the data service per partition. The actual #of queries made to the data
 * service depends on the #of index entries that are visited per partition and
 * the capacity specified to the ctor.
 * 
 * @author <a href="mailto:thompsonbry@users.sourceforge.net">Bryan Thompson</a>
 * @version $Id$
 */
public class PartitionedRangeQueryIterator implements ITupleIterator {

    protected static final transient Logger log = Logger
            .getLogger(PartitionedRangeQueryIterator.class);
    
    /**
     * The index on which the range query is being performed.
     */
    private final ClientIndexView ndx;
    
    /**
     * Iterator traversing the index partition locators spanned by the query.
     */
    private ITupleIterator locatorItr;
    
    /**
     * The timestamp from the {@link ClientIndexView}.
     */
    private final long timestamp;

    /**
     * The first key to visit -or- <code>null</code> iff no lower bound (from
     * the ctor).
     */
    private final byte[] fromKey;
    
    /**
     * The first key to NOT visit -or- <code>null</code> iff no upper bound
     * (from the ctor).
     */
    private final byte[] toKey;

    /**
     * This controls the #of results per data service query.
     */
    private final int capacity;

    /**
     * These flags control whether keys and/or values are requested. If
     * neither keys nor values are requested, then this is just a range
     * count operation and you might as well use rangeCount instead.
     */
    private final int flags;
    
    private final ITupleFilter filter;
    
    /**
     * The last key that was visited on the {@link #src} iterator. This is used
     * in case we trap a {@link NoSuchIndexException}. It provides the
     * exclusive lower bound for the query against the new index partitions
     * identified when we refresh our {@link PartitionLocator}s. This is
     * <code>null</code> initially and is set to the non-<code>null</code>
     * value of each key that we visit by {@link #next()}.
     */
    private byte[] lastKeyVisited = null;

    /**
     * The metadata for the current index partition.
     */
    private PartitionLocator locator = null;

    /**
     * The #of partitions that have been queried so far. There will be one
     * {@link DataServiceRangeIterator} query issued per partition.
     * 
     * @deprecated The #of partitions is a bit tricky since splits and introduce
     *             new partitions.
     */
    private int nparts = 0;
    
    /**
     * The #of enties visited so far.
     */
    private long nvisited = 0;
    
    /**
     * The {@link DataServiceRangeIterator} reading from the current index
     * partition.
     */
    private DataServiceRangeIterator src;
   
    /**
     * When true, the entire key range specified by the client has been
     * visited and the iterator is exhausted (i.e., all done).
     */
    private boolean exhausted = false;

    /**
     * The #of index partitions queried so far.
     * 
     * @deprecated The #of partitions is a bit tricky since splits and introduce
     *             new partitions.
     */
    public int getPartitionCount() {
        
        return nparts;
        
    }
    
    /**
     * The #of entries visited so far (not the #of entries scanned, which
     * can be much greater if a filter is in use).
     */
    public long getVisitedCount() {
        
        return nvisited;
        
    }
    
    public PartitionedRangeQueryIterator(ClientIndexView ndx, byte[] fromKey,
            byte[] toKey, int capacity, int flags, ITupleFilter filter) {

        if (ndx == null) {

            throw new IllegalArgumentException();
            
        }
        
        if (capacity < 0) {

            throw new IllegalArgumentException();
            
        }

        this.ndx = ndx;
        this.timestamp = ndx.getTimestamp();
        this.fromKey = fromKey;
        this.toKey = toKey;
        this.capacity = capacity;
        this.flags = flags;
        this.filter = filter;

        // scan spanned index partition locators in key order.
        this.locatorItr = ndx.locatorScan(fromKey, toKey);

    }

    public boolean hasNext() {

        if (exhausted) {

            return false;
            
        }

        if (locator == null) {

            // Setup query for the first partition.
            
            if (!nextPartition()) {
                
                return false;
                
            }

        }
        
        assert src != null;

        try {

            if(src.hasNext()) {
            
                // More from the current source iterator.
                
                return true;
                
            }
            
        } catch(RuntimeException ex) {
            
            if(InnerCause.isInnerCause(ex, NoSuchIndexException.class)) {
                
                /*
                 * Handle NoSuchIndexException. This exception indicates that we
                 * have a stale index partition locator. This can happen when
                 * index partitions are split, joined, or moved.
                 */

                // clear since invalid.
                locator = null;
                
                // Re-start the locator scan.
                locatorItr = ndx.locatorScan(currentFromKey(), toKey);
                
                // Recursive query.
                return hasNext();
                
            }
            
        }
        
        /*
         * The current index partition is empty, but there are other index
         * partitions left to query.
         * 
         * Each source iterator reads from one index partition. (The source
         * iterator is itself a chunked iterator so it may issue multiple remote
         * requests to consume the data available on a given index partition).
         */
        
        if(nextPartition()) {
        
            /*
             * Recursive query since the index partition might be empty.
             */
            
            return hasNext();
            
        }
        
        /*
         * Exausted.
         */
        
        exhausted = true;
        
        return false;
        
    }

    /**
     * The next [fromKey] that we are supposed to visit. If we have not visited
     * anything, then we are still on the original {@link #fromKey}. Otherwise
     * we take the successor of the {@link #lastKeyVisited}.
     */
    private byte[] currentFromKey() {

        final byte[] currentFromKey = lastKeyVisited == null //
            ? this.fromKey//
            : BytesUtil.successor(lastKeyVisited)//
            ;

        return currentFromKey;
        
    }
    
    /**
     * Issues a new range query against the next index partititon.
     */
    private boolean nextPartition() {

        assert ! exhausted;
        
        if(!locatorItr.hasNext()) return false;
        
        ITuple tuple = locatorItr.next();
        
        locator = (PartitionLocator)SerializerUtil.deserialize(tuple.getValue());
        
        // submit query to the next partition.
        
        rangeQuery();

        assert src != null;

        return true;

    }
    
    /**
     * Issues a range query against the current {@link #locator}.
     */
    private void rangeQuery() {

        assert ! exhausted;
        
        assert locator != null;

        try {

            /*
             * Note: The range query request is formed such that it addresses
             * only those keys that actually lie within the partition and also
             * within the caller's given key range. This has two benefits:
             * 
             * (1) The data service can check the range and report an error for
             * clients that appear to be requesting data for index partitions
             * that have been relocated.
             * 
             * (2) It avoids double-counting (or possible under-counting) when
             * an index partition join (or split) causes the partition bounds to
             * be greater than was originally anticipated.
             */

            final byte[] _fromKey = AbstractKeyRangeIndexProcedure
                    .constrainFromKey(currentFromKey(), locator);

            final byte[] _toKey = AbstractKeyRangeIndexProcedure
                    .constrainToKey(toKey, locator);
            
            final int partitionId = locator.getPartitionId();
            
            log.info("name=" + ndx.getName() + ", tx=" + timestamp + ", partition="
                    + partitionId + ", fromKey=" + BytesUtil.toString(_fromKey)
                    + ", toKey=" + BytesUtil.toString(_toKey));
            
            /*
             * The data service for the current index partition.
             * 
             * @todo this should failover.
             */
            final IDataService dataService = ndx.getDataService(locator);
            
            /*
             * Iterator will visit all data on that index partition.
             * 
             * Note: This merely initializes the variables on the iterator, but
             * it DOES NOT send the request to the data service. That does not
             * happen until you call [src.hasNext()].
             */
            
            src = new DataServiceRangeIterator(ndx, dataService, DataService
                    .getIndexPartitionName(ndx.getName(), partitionId),
                    ndx.getTimestamp(), _fromKey, _toKey, capacity, flags, filter);

            // increment the #of partitions visited.
            nparts++;

        } catch (Exception ex) {

            throw new RuntimeException(ex);

        }

    }

    public ITuple next() {

        if (!hasNext()) {

            throw new NoSuchElementException();
            
        }
        
        nvisited++;

        final long nvisited = this.nvisited;
        
        final ITuple sourceTuple = src.next();

        this.lastKeyVisited = sourceTuple.getKey();

        assert lastKeyVisited != null;
        
        return new DelegateTuple( sourceTuple ) {
            
            public long getVisitCount() {
                
                return nvisited;
                
            }
            
        };
        
    }

    /**
     * Batch delete behind semantics.
     * 
     * @see DataServiceRangeIterator#remove()
     */
    public void remove() {
        
        if (src == null)
            throw new IllegalStateException();
        
        src.remove();
        
    }
    
}
