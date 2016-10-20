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
 * Utility class to declare the ganglia core metrics. These declarations were
 * derived by monitoring {@link IGangliaMetadataMessage}s as generated by
 * ganglia services.
 * <p>
 * Note: {@link GangliaService} can (and will) pickup metadata declarations from
 * the ganglia network, including running gmond instances. Normally, the
 * {@link GangliaService} will wait a bit until these declarations are
 * discovered from the ganglia network. That way it picks up the values of TMax
 * and DMax which are already configured auto-magically. The role of this class
 * is to get those definitions right in case there is no ganglia network running
 * from which it can capture the as configured metric metadata declarations.
 * 
 * <h2>Metadata capture</h2>
 * 
 * The following is a capture of the metadata records as generated by gmond and
 * captured by the {@link GangliaListener}. This forms the basis of the
 * declarations in this class.
 * 
 * <pre>
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=heartbeat, spoof=false, metricType=UINT32, metricName2=heartbeat, units=, slope=unspecified, tmax=20, dmax=0, extraValues={TITLE="heartbeat"},DESC="Last heartbeat"},GROUP="core"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=cpu_num, spoof=false, metricType=UINT16, metricName2=cpu_num, units=CPUs, slope=zero, tmax=1200, dmax=0, extraValues={TITLE="CPU Count"},DESC="Total number of CPUs"},GROUP="cpu"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=cpu_speed, spoof=false, metricType=UINT32, metricName2=cpu_speed, units=MHz, slope=zero, tmax=1200, dmax=0, extraValues={TITLE="CPU Speed"},DESC="CPU Speed in terms of MHz"},GROUP="cpu"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=mem_total, spoof=false, metricType=FLOAT, metricName2=mem_total, units=KB, slope=zero, tmax=1200, dmax=0, extraValues={TITLE="Memory Total"},DESC="Total amount of memory displayed in KBs"},GROUP="memory"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=swap_total, spoof=false, metricType=FLOAT, metricName2=swap_total, units=KB, slope=zero, tmax=1200, dmax=0, extraValues={TITLE="Swap Space Total"},DESC="Total amount of swap space displayed in KBs"},GROUP="memory"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=boottime, spoof=false, metricType=UINT32, metricName2=boottime, units=s, slope=zero, tmax=1200, dmax=0, extraValues={TITLE="Last Boot Time"},DESC="The last time that the system was started"},GROUP="system"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=machine_type, spoof=false, metricType=STRING, metricName2=machine_type, units=, slope=zero, tmax=1200, dmax=0, extraValues={TITLE="Machine Type"},DESC="System architecture"},GROUP="system"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=os_name, spoof=false, metricType=STRING, metricName2=os_name, units=, slope=zero, tmax=1200, dmax=0, extraValues={TITLE="Operating System"},DESC="Operating system name"},GROUP="system"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=os_release, spoof=false, metricType=STRING, metricName2=os_release, units=, slope=zero, tmax=1200, dmax=0, extraValues={TITLE="Operating System Release"},DESC="Operating system release date"},GROUP="system"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=location, spoof=false, metricType=STRING, metricName2=location, units=(x,y,z), slope=unspecified, tmax=1200, dmax=0, extraValues={TITLE="Location"},DESC="Location of the machine"},GROUP="core"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=gexec, spoof=false, metricType=STRING, metricName2=gexec, units=, slope=zero, tmax=300, dmax=0, extraValues={TITLE="Gexec Status"},DESC="gexec available"},GROUP="core"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=cpu_user, spoof=false, metricType=FLOAT, metricName2=cpu_user, units=%, slope=both, tmax=90, dmax=0, extraValues={TITLE="CPU User"},DESC="Percentage of CPU utilization that occurred while executing at the user level"},GROUP="cpu"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=cpu_system, spoof=false, metricType=FLOAT, metricName2=cpu_system, units=%, slope=both, tmax=90, dmax=0, extraValues={TITLE="CPU System"},DESC="Percentage of CPU utilization that occurred while executing at the system level"},GROUP="cpu"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=cpu_idle, spoof=false, metricType=FLOAT, metricName2=cpu_idle, units=%, slope=both, tmax=90, dmax=0, extraValues={TITLE="CPU Idle"},DESC="Percentage of time that the CPU or CPUs were idle and the system did not have an outstanding disk I/O request"},GROUP="cpu"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=cpu_nice, spoof=false, metricType=FLOAT, metricName2=cpu_nice, units=%, slope=both, tmax=90, dmax=0, extraValues={TITLE="CPU Nice"},DESC="Percentage of CPU utilization that occurred while executing at the user level with nice priority"},GROUP="cpu"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=cpu_aidle, spoof=false, metricType=FLOAT, metricName2=cpu_aidle, units=%, slope=both, tmax=3800, dmax=0, extraValues={TITLE="CPU aidle"},DESC="Percent of time since boot idle CPU"},GROUP="cpu"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=cpu_wio, spoof=false, metricType=FLOAT, metricName2=cpu_wio, units=%, slope=both, tmax=90, dmax=0, extraValues={TITLE="CPU wio"},DESC="Percentage of time that the CPU or CPUs were idle during which the system had an outstanding disk I/O request"},GROUP="cpu"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=cpu_intr, spoof=false, metricType=FLOAT, metricName2=cpu_intr, units=%, slope=both, tmax=90, dmax=0, extraValues={TITLE="CPU intr"},DESC="cpu_intr"},GROUP="cpu"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=cpu_sintr, spoof=false, metricType=FLOAT, metricName2=cpu_sintr, units=%, slope=both, tmax=90, dmax=0, extraValues={TITLE="CPU sintr"},DESC="cpu_sintr"},GROUP="cpu"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=load_one, spoof=false, metricType=FLOAT, metricName2=load_one, units= , slope=both, tmax=70, dmax=0, extraValues={TITLE="One Minute Load Average"},DESC="One minute load average"},GROUP="load"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=load_five, spoof=false, metricType=FLOAT, metricName2=load_five, units= , slope=both, tmax=325, dmax=0, extraValues={TITLE="Five Minute Load Average"},DESC="Five minute load average"},GROUP="load"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=load_fifteen, spoof=false, metricType=FLOAT, metricName2=load_fifteen, units= , slope=both, tmax=950, dmax=0, extraValues={TITLE="Fifteen Minute Load Average"},DESC="Fifteen minute load average"},GROUP="load"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=proc_run, spoof=false, metricType=UINT32, metricName2=proc_run, units= , slope=both, tmax=950, dmax=0, extraValues={TITLE="Total Running Processes"},DESC="Total number of running processes"},GROUP="process"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=proc_total, spoof=false, metricType=UINT32, metricName2=proc_total, units= , slope=both, tmax=950, dmax=0, extraValues={TITLE="Total Processes"},DESC="Total number of processes"},GROUP="process"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=mem_free, spoof=false, metricType=FLOAT, metricName2=mem_free, units=KB, slope=both, tmax=180, dmax=0, extraValues={TITLE="Free Memory"},DESC="Amount of available memory"},GROUP="memory"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=mem_shared, spoof=false, metricType=FLOAT, metricName2=mem_shared, units=KB, slope=both, tmax=180, dmax=0, extraValues={TITLE="Shared Memory"},DESC="Amount of shared memory"},GROUP="memory"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=mem_buffers, spoof=false, metricType=FLOAT, metricName2=mem_buffers, units=KB, slope=both, tmax=180, dmax=0, extraValues={TITLE="Memory Buffers"},DESC="Amount of buffered memory"},GROUP="memory"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=mem_cached, spoof=false, metricType=FLOAT, metricName2=mem_cached, units=KB, slope=both, tmax=180, dmax=0, extraValues={TITLE="Cached Memory"},DESC="Amount of cached memory"},GROUP="memory"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=swap_free, spoof=false, metricType=FLOAT, metricName2=swap_free, units=KB, slope=both, tmax=180, dmax=0, extraValues={TITLE="Free Swap Space"},DESC="Amount of available swap memory"},GROUP="memory"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=bytes_out, spoof=false, metricType=FLOAT, metricName2=bytes_out, units=bytes/sec, slope=both, tmax=300, dmax=0, extraValues={TITLE="Bytes Sent"},DESC="Number of bytes out per second"},GROUP="network"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=bytes_in, spoof=false, metricType=FLOAT, metricName2=bytes_in, units=bytes/sec, slope=both, tmax=300, dmax=0, extraValues={TITLE="Bytes Received"},DESC="Number of bytes in per second"},GROUP="network"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=pkts_in, spoof=false, metricType=FLOAT, metricName2=pkts_in, units=packets/sec, slope=both, tmax=300, dmax=0, extraValues={TITLE="Packets Received"},DESC="Packets in per second"},GROUP="network"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=pkts_out, spoof=false, metricType=FLOAT, metricName2=pkts_out, units=packets/sec, slope=both, tmax=300, dmax=0, extraValues={TITLE="Packets Sent"},DESC="Packets out per second"},GROUP="network"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=disk_total, spoof=false, metricType=DOUBLE, metricName2=disk_total, units=GB, slope=both, tmax=1200, dmax=0, extraValues={TITLE="Total Disk Space"},DESC="Total available disk space"},GROUP="disk"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=disk_free, spoof=false, metricType=DOUBLE, metricName2=disk_free, units=GB, slope=both, tmax=180, dmax=0, extraValues={TITLE="Disk Space Available"},DESC="Total free disk space"},GROUP="disk"}}}
 * GangliaMetadataMessage{ recordType=METADATA, hostName=bigdata08, metricName=part_max_used, spoof=false, metricType=FLOAT, metricName2=part_max_used, units=%, slope=both, tmax=180, dmax=0, extraValues={TITLE="Maximum Disk Space Used"},DESC="Maximum percent used for all partitions"},GROUP="disk"}}}
 * </pre>
 * 
 * TODO This is missing declarations for the following metrics (I am not
 * observing those metrics under Ubuntu). While it might be difficult to get at
 * the mtu for some platforms, it is quite odd that sys_clock is not being
 * reported.
 * 
 * <pre>
 * mtu             Network maximum transmission unit (module sys_module)
 * sys_clock       Time as reported by the system clock (module sys_module)
 * </pre>
 */
public class GangliaCoreMetricDecls extends AbstractMetrics 
// FIXME implements IGangliaMetadataFactory 
{

	/**
	 * The heartbeat interval in seconds -or- ZERO (0) if we will not be
	 * sending out the ganglia host heartbeat.
	 * 
	 * @see IGangliaDefaults#HEARTBEAT_INTERVAL
	 */
	protected final int heartbeatInterval;
	
	/**
	 * Note: Some metrics have non-default values of slope, tmax, and dmax. For
	 * example, {@link #load_fifteen()}. The constructor values will not
	 * override the values for such metrics.
	 * 
	 * @param hostName
	 *            The name of this host.
	 * @param slope
	 *            The default value to use in the declarations.
	 * @param tmax
	 *            The value of tmax to use in the declarations.
	 * @param dmax
	 *            The value of dmax to use in the declarations.
	 * @param heartbeatInterval
	 *            The heartbeat interval in seconds -or- ZERO (0) if we will not
	 *            be sending out the ganglia host heartbeat.
	 */
	public GangliaCoreMetricDecls(final String hostName,
			final GangliaSlopeEnum slope, final int tmax, final int dmax,
			final int heartbeatInterval) {

		super(hostName, slope, tmax, dmax);

		if (heartbeatInterval < 0)
			throw new IllegalArgumentException();
		
		this.heartbeatInterval = heartbeatInterval;

	}

	/*
	 * Ganglia protocol metrics (heartbeat, gexec).
	 */
	
	static public final IGangliaMetadataMessage heartbeat(
			final String hostName, final int heartbeatInterval) {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"heartbeat", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.UINT32,// metricType
				"heartbeat",// metric name (again)
				"", // units
				GangliaSlopeEnum.unspecified,//
				heartbeatInterval,// tmax
				0, // dmax,//
				getMap(GROUP_CORE, "heartbeat", "Last heartbeat"));
	}

	public final IGangliaMetadataMessage heartbeat() {

		return heartbeat(hostName, heartbeatInterval);
		
	}

	public final IGangliaMetadataMessage gexec() {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"gexec", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.STRING,// metricType
				"gexec",// metric name (again)
				"", // units
				GangliaSlopeEnum.zero,//
				300,// tmax is *always* 20 for the heartbeat.
				0, // dmax,//
				getMap(GROUP_CORE, "Gexec Status", "gexec available"));
	}

	/*
	 * memory
	 */

	public final IGangliaMetadataMessage mem_free() {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"mem_free", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.FLOAT,// metricType
				"mem_free",// metric name (again)
				"KB", // units
				slope,//
				tmax,//
				dmax,//
				getMap(GROUP_MEMORY, "Free Memory",
						"Amount of available memory"));
	}

	public final IGangliaMetadataMessage mem_shared() {
		return new GangliaMetadataMessage(
				// metric_id
				hostName,// host
				"mem_shared", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.FLOAT,// metricType
				"mem_shared",// metric name (again)
				"KB", // units
				slope,//
				tmax,//
				dmax,//
				getMap(GROUP_MEMORY, "Shared Memory", "Amount of shared memory"));
	}

	public final IGangliaMetadataMessage mem_buffers() {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"mem_buffers", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.FLOAT,// metricType
				"mem_buffers",// metric name (again)
				"KB", // units
				slope,//
				tmax,//
				dmax,//
				getMap(GROUP_MEMORY, "Memory Buffers",
						"Amount of buffered memory"));
	}

	public final IGangliaMetadataMessage mem_cached() {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"mem_cached", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.FLOAT,// metricType
				"mem_cached",// metric name (again)
				"KB", // units
				slope,//
				tmax,//
				dmax,//
				getMap(GROUP_MEMORY, "Cached Memory", "Amount of cached memory"));
	}
		
	public final IGangliaMetadataMessage mem_total() {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"mem_total", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.FLOAT,// metricType
				"mem_total",// metric name (again)
				"KB", // units
				GangliaSlopeEnum.zero,//slope,//
				1200,//tmax,//
				dmax,//
				getMap(GROUP_MEMORY,"Memory Total", "Total amount of memory"));
	}
	
	public final IGangliaMetadataMessage swap_free() {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"swap_free", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.FLOAT,// metricType
				"swap_free",// metric name (again)
				"KB", // units
				slope,//
				tmax,//
				dmax,//
				getMap(GROUP_MEMORY,"Free Swap Space", "Amount of available swap memory"));
	}

	public final IGangliaMetadataMessage swap_total() {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"swap_total", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.FLOAT,// metricType
				"swap_total",// metric name (again)
				"KB", // units
				GangliaSlopeEnum.zero,//slope,//
				1200,// tmax
				dmax,//
				getMap(GROUP_MEMORY,"Swap Space Total", "Total amount of swap space"));
	}

	/*
	 * Load.
	 */

	public final IGangliaMetadataMessage load_one() {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"load_one", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.FLOAT,// metricType
				"load_one",// metric name (again)
				"", // units
				GangliaSlopeEnum.both,// slope,//
				70,// tmax
				0,// dmax,//
				getMap(GROUP_LOAD, "One Minute Load Average",
						"One minute load average"));
	}
	
	public final IGangliaMetadataMessage load_five() {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"load_five", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.FLOAT,// metricType
				"load_five",// metric name (again)
				"", // units
				GangliaSlopeEnum.both,// slope,//
				325,// tmax
				0,// dmax,//
				getMap(GROUP_LOAD, "Five Minute Load Average",
						"Five minute load average"));
	}

	public final IGangliaMetadataMessage load_fifteen() {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"load_fifteen", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.FLOAT,// metricType
				"load_fifteen",// metric name (again)
				"", // units
				GangliaSlopeEnum.both,// slope,//
				950,// tmax
				0,// dmax,//
				getMap(GROUP_LOAD, "Fifteen Minute Load Average",
						"Fifteen minute load average"));
	}

	public final IGangliaMetadataMessage proc_run() {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"proc_run", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.UINT32,// metricType
				"proc_run",// metric name (again)
				"", // units
				GangliaSlopeEnum.both,// slope,//
				950,// tmax
				0,// dmax,//
				getMap(GROUP_PROCESS, "Total Running Processes",
						"Total number of running processes"));
	}

	public final IGangliaMetadataMessage proc_total() {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"proc_total", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.UINT32,// metricType
				"proc_total",// metric name (again)
				"", // units
				GangliaSlopeEnum.both,// slope,//
				950,// tmax
				0,// dmax,//
				getMap(GROUP_PROCESS, "Total Processes",
						"Total number of processes"));
	}

	/*
	 * CPU.
	 */
	
	public final IGangliaMetadataMessage cpu_user() {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"cpu_user", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.FLOAT,// metricType
				"cpu_user",// metric name (again)
				"%", // units
				GangliaSlopeEnum.both,// slope,//
				90,// tmax
				dmax,// dmax,//
				getMap(GROUP_CPU, "CPU User",
						"Percentage of CPU utilization that occurred while executing at the user level"));
	}

	public final IGangliaMetadataMessage cpu_system() {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"cpu_system", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.FLOAT,// metricType
				"cpu_system",// metric name (again)
				"%", // units
				GangliaSlopeEnum.both,// slope,//
				90,// tmax
				dmax,// dmax,//
				getMap(GROUP_CPU, "CPU System",						
						"Percentage of CPU utilization that occurred while executing at the system level"));
	}

	public final IGangliaMetadataMessage cpu_idle() {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"cpu_idle", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.FLOAT,// metricType
				"cpu_idle",// metric name (again)
				"%", // units
				GangliaSlopeEnum.both,// slope,//
				90,// tmax
				dmax,// dmax,//
				getMap(GROUP_CPU, "CPU Idle",
						"Percentage of time that the CPU or CPUs were idle and the system did not have an outstanding disk I/O request")//
		);
	}

	public final IGangliaMetadataMessage cpu_nice() {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"cpu_nice", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.FLOAT,// metricType
				"cpu_nice",// metric name (again)
				"%", // units
				GangliaSlopeEnum.both,// slope,//
				90,// tmax
				dmax,// dmax,//
				getMap(GROUP_CPU, "CPU Nice",
						"Percentage of CPU utilization that occurred while executing at the user level with nice priority"));
	}
	
	public final IGangliaMetadataMessage cpu_aidle() {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"cpu_aidle", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.FLOAT,// metricType
				"cpu_aidle",// metric name (again)
				"%", // units
				GangliaSlopeEnum.both,// slope,//
				3800,// tmax
				dmax,// dmax,//
				getMap(GROUP_CPU, "CPU aidle","Percent of time since boot idle CPU")//
		);
	}

	public final IGangliaMetadataMessage cpu_wio() {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"cpu_wio", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.FLOAT,// metricType
				"cpu_wio",// metric name (again)
				"%", // units
				GangliaSlopeEnum.both,// slope,//
				90,// tmax
				dmax,// dmax,//
				getMap(GROUP_CPU, "CPU wio",
						"Percentage of time that the CPU or CPUs were idle during which the system had an outstanding disk I/O request"));
	}

	public final IGangliaMetadataMessage cpu_intr() {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"cpu_intr", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.FLOAT,// metricType
				"cpu_intr",// metric name (again)
				"%", // units
				GangliaSlopeEnum.both,// slope,//
				90,// tmax
				dmax,// dmax,//
				getMap(GROUP_CPU, null/* title */, null));
	}

	public final IGangliaMetadataMessage cpu_sintr() {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"cpu_sintr", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.FLOAT,// metricType
				"cpu_sintr",// metric name (again)
				"%", // units
				GangliaSlopeEnum.both,// slope,//
				90,// tmax
				dmax,// dmax,//
				getMap(GROUP_CPU, null/* title */, null));
	}

	/*
	 * Network.
	 */

	public final IGangliaMetadataMessage bytes_out() {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"bytes_out", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.FLOAT,// metricType
				"bytes_out",// metric name (again)
				"bytes/sec", // units
				GangliaSlopeEnum.both,// slope,//
				300,// tmax
				0,// dmax,//
				getMap(GROUP_NETWORK,"Bytes Sent","Number of bytes out per second")//
		);
	}

	public final IGangliaMetadataMessage bytes_in() {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"bytes_in", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.FLOAT,// metricType
				"bytes_in",// metric name (again)
				"bytes/sec", // units
				GangliaSlopeEnum.both,// slope,//
				300,// tmax
				0,// dmax,//
				getMap(GROUP_NETWORK,"Bytes Received","Number of bytes in per second")//
		);
	}

	public final IGangliaMetadataMessage pkts_in() {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"pkts_in", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.FLOAT,// metricType
				"pkts_in",// metric name (again)
				"packets/sec", // units
				GangliaSlopeEnum.both,// slope,//
				300,// tmax
				0,// dmax,//
				getMap(GROUP_NETWORK,"Packets Received", "Packets in per second"));
	}

	public final IGangliaMetadataMessage pkts_out() {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"pkts_out", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.FLOAT,// metricType
				"pkts_out",// metric name (again)
				"packets/sec", // units
				GangliaSlopeEnum.both,// slope,//
				300,// tmax
				0,// dmax,//
				getMap(GROUP_NETWORK,"Packets Sent", "Packets out per second"));
	}
	
	/*
	 * Disk.
	 */

	public final IGangliaMetadataMessage disk_total() {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"disk_total", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.DOUBLE,// metricType
				"disk_total",// metric name (again)
				"GB", // units
				GangliaSlopeEnum.both,// slope,//
				1200,// tmax
				0,// dmax,//
				getMap(GROUP_DISK, "Total Disk Space",
						"Total available disk space"));
	}

	public final IGangliaMetadataMessage disk_free() {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"disk_total", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.DOUBLE,// metricType
				"disk_total",// metric name (again)
				"GB", // units
				GangliaSlopeEnum.both,// slope,//
				tmax,// tmax
				0,// dmax,//
				getMap(GROUP_DISK, "Disk Space Available",
						"Total free disk space"));
	}

	public final IGangliaMetadataMessage part_max_used() {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"part_max_used", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.FLOAT,// metricType
				"part_max_used",// metric name (again)
				"%", // units
				GangliaSlopeEnum.both,// slope,//
				tmax,// tmax
				0,// dmax,//
				getMap(GROUP_DISK,"Maximum Disk Space Used",
						"Maximum percent used for all partitions"));
	}

	/*
	 * Metadata
	 */

	public final IGangliaMetadataMessage boottime() {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"boottime", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.UINT32,// metricType
				"boottime",// metric name (again)
				"s", // units
				GangliaSlopeEnum.zero,// slope,//
				1200,// tmax
				0,// dmax,//
				getMap(GROUP_SYSTEM, "Last Boot Time",
						"The last time that the system was started"));
	}

	public final IGangliaMetadataMessage machine_type() {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"machine_type", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.STRING,// metricType
				"machine_type",// metric name (again)
				"", // units
				GangliaSlopeEnum.zero,// slope,//
				1200,// tmax
				0,// dmax,//
				getMap(GROUP_SYSTEM,"Machine Type","System architecture"));
	}

	public final IGangliaMetadataMessage os_name() {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"os_name", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.STRING,// metricType
				"os_name",// metric name (again)
				"", // units
				GangliaSlopeEnum.zero,// slope,//
				1200,// tmax
				0,// dmax,//
				getMap(GROUP_SYSTEM, "Operating System",
						"Operating system name"));
	}

	public final IGangliaMetadataMessage os_release() {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"os_release", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.STRING,// metricType
				"os_release",// metric name (again)
				"", // units
				GangliaSlopeEnum.zero,// slope,//
				1200,// tmax
				0,// dmax,//
				getMap(GROUP_SYSTEM, "Operating System Release",
						"Operating system release"));
	}

	public final IGangliaMetadataMessage cpu_num() {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"cpu_num", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.UINT16,// metricType
				"cpu_num",// metric name (again)
				"CPUs", // units
				GangliaSlopeEnum.zero,// slope,//
				1200,// tmax
				0,// dmax,//
				getMap(GROUP_CPU, "CPU Count", "Total number of CPUs"));
	}

	public final IGangliaMetadataMessage cpu_speed() {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"cpu_speed", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.UINT32,// metricType
				"cpu_speed",// metric name (again)
				"MHz", // units
				GangliaSlopeEnum.zero,// slope,//
				1200,// tmax
				0,// dmax,//
				getMap(GROUP_CPU,"CPU Speed","CPU Speed in terms of MHz")
		);
	}

	public final IGangliaMetadataMessage location() {
		return new GangliaMetadataMessage(
		// metric_id
				hostName,// host
				"location", // metric name
				false,// spoof
				// metadata record
				GangliaMessageTypeEnum.STRING,// metricType
				"location",// metric name (again)
				"(x,y,z)", // units
				GangliaSlopeEnum.unspecified,// slope,//
				1200,// tmax
				0,// dmax,//
				getMap(null/* group */, null/* title */,
						"Location of the machine"));
	}

	/**
	 * Declare some well known OS/platform level metrics (optional step).
	 * 
	 * @param gangliaState
	 *            The metrics will be declared to this state object.
	 * 
	 *            FIXME Why never invoked?
	 */
	public void declare(final GangliaState gangliaState) {

		gangliaState.putIfAbsent(heartbeat());
		gangliaState.putIfAbsent(gexec());

		// memory
		gangliaState.putIfAbsent(mem_free());
		gangliaState.putIfAbsent(mem_shared());
		gangliaState.putIfAbsent(mem_buffers());
		gangliaState.putIfAbsent(mem_cached());
		gangliaState.putIfAbsent(mem_total());
		gangliaState.putIfAbsent(swap_free());
		gangliaState.putIfAbsent(swap_total());

		// load
		gangliaState.putIfAbsent(load_one());
		gangliaState.putIfAbsent(load_five());
		gangliaState.putIfAbsent(load_fifteen());
		gangliaState.putIfAbsent(proc_run());
		gangliaState.putIfAbsent(proc_total());

		// CPU
		gangliaState.putIfAbsent(cpu_user());
		gangliaState.putIfAbsent(cpu_system());
		gangliaState.putIfAbsent(cpu_idle());
		gangliaState.putIfAbsent(cpu_nice());
		gangliaState.putIfAbsent(cpu_aidle());
		gangliaState.putIfAbsent(cpu_wio());
		gangliaState.putIfAbsent(cpu_intr());
		gangliaState.putIfAbsent(cpu_sintr());

		// Network
		gangliaState.putIfAbsent(bytes_in());
		gangliaState.putIfAbsent(bytes_out());
		gangliaState.putIfAbsent(pkts_in());
		gangliaState.putIfAbsent(pkts_out());

		// Disk
		gangliaState.putIfAbsent(disk_total());
		gangliaState.putIfAbsent(disk_free());
		gangliaState.putIfAbsent(part_max_used());

		// Metadata
		gangliaState.putIfAbsent(boottime());
		gangliaState.putIfAbsent(machine_type());
		gangliaState.putIfAbsent(os_name());
		gangliaState.putIfAbsent(os_release());
		gangliaState.putIfAbsent(cpu_num());
		gangliaState.putIfAbsent(cpu_speed());
		gangliaState.putIfAbsent(location());
		
	}

}
