/*
   Copyright (C) SYSTAP, LLC 2006-2012.  All rights reserved.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package com.bigdata.ganglia;

/**
 * Interface for collecting metrics from a local (embedded) process.
 */
public interface IGangliaMetricsCollector {

	/**
	 * Collect and report metric values.
	 * 
	 * @param reporter
	 *            Where to report the collected metric values.
	 */
	public void collect(final IGangliaMetricsReporter reporter);
	
}
