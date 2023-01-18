package com.libre.im.system.service.impl;

import com.libre.im.system.service.MonitorService;
import com.libre.toolkit.core.INetUtil;
import com.libre.toolkit.core.RuntimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;
import oshi.util.Util;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author: Libre
 * @Date: 2023/1/8 12:10 AM
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MonitorServiceImpl implements MonitorService {

	private final DecimalFormat df = new DecimalFormat("0.00");

	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	public Map<String, Object> getServerInfo() {

		Map<String, Object> resultMap = new LinkedHashMap<>(8);
		try {
			SystemInfo si = new SystemInfo();
			OperatingSystem os = si.getOperatingSystem();
			HardwareAbstractionLayer hal = si.getHardware();
			// 系统信息
			resultMap.put("sys", getSystemInfo(os));
			// cpu 信息
			resultMap.put("cpu", getCpuInfo(hal.getProcessor()));
			// 内存信息
			resultMap.put("memory", getMemoryInfo(hal.getMemory()));
			// 交换区信息
			resultMap.put("swap", getSwapInfo(hal.getMemory()));
			// 磁盘
			resultMap.put("disk", getDiskInfo(os));
			resultMap.put("time", LocalDateTime.now());
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> getRedisStat() {
		Properties info = (Properties) redisTemplate.execute((RedisCallback<Object>) RedisServerCommands::info);
		Properties commandStats = (Properties) redisTemplate
				.execute((RedisCallback<Object>) connection -> connection.info("commandstats"));
		Assert.notNull(commandStats, "commandStats not be null");

		Object dbSize = redisTemplate.execute((RedisCallback<Object>) RedisServerCommands::dbSize);
		Map<String, Object> result = new HashMap<>(3);
		result.put("info", info);
		result.put("dbSize", dbSize);
		List<Map<String, String>> pieList = new ArrayList<>();
		commandStats.stringPropertyNames().forEach(key -> {
			Map<String, String> data = new HashMap<>(2);
			String property = commandStats.getProperty(key);
			data.put("name", StringUtils.removeStart(key, "cmdstat_"));
			data.put("value", StringUtils.substringBetween(property, "calls=", ",usec"));
			pieList.add(data);
		});
		result.put("commandStats", pieList);
		return result;
	}

	private Map<String, Object> getDiskInfo(OperatingSystem os) {
		Map<String, Object> diskInfo = new LinkedHashMap<>();
		FileSystem fileSystem = os.getFileSystem();
		List<OSFileStore> fsArray = fileSystem.getFileStores();
		for (OSFileStore fs : fsArray) {
			diskInfo.put("total", fs.getTotalSpace() > 0 ? FormatUtil.formatBytes(fs.getTotalSpace()) : "?");
			long used = fs.getTotalSpace() - fs.getUsableSpace();
			diskInfo.put("available", FormatUtil.formatBytes(fs.getUsableSpace()));
			diskInfo.put("used", FormatUtil.formatBytes(used));
			diskInfo.put("usageRate", df.format(used / (double) fs.getTotalSpace() * 100));
		}
		return diskInfo;
	}

	/**
	 * 获取交换区信息
	 */
	private Map<String, Object> getSwapInfo(GlobalMemory memory) {
		Map<String, Object> swapInfo = new LinkedHashMap<>();
		swapInfo.put("total", FormatUtil.formatBytes(memory.getVirtualMemory().getSwapTotal()));
		swapInfo.put("used", FormatUtil.formatBytes(memory.getVirtualMemory().getSwapUsed()));
		swapInfo.put("available", FormatUtil
				.formatBytes(memory.getVirtualMemory().getSwapTotal() - memory.getVirtualMemory().getSwapUsed()));
		swapInfo.put("usageRate", df.format(
				memory.getVirtualMemory().getSwapUsed() / (double) memory.getVirtualMemory().getSwapTotal() * 100));
		return swapInfo;
	}

	/**
	 * 获取内存信息
	 */
	private Map<String, Object> getMemoryInfo(GlobalMemory memory) {
		Map<String, Object> memoryInfo = new LinkedHashMap<>();
		memoryInfo.put("total", FormatUtil.formatBytes(memory.getTotal()));
		memoryInfo.put("available", FormatUtil.formatBytes(memory.getAvailable()));
		memoryInfo.put("used", FormatUtil.formatBytes(memory.getTotal() - memory.getAvailable()));
		memoryInfo.put("usageRate",
				df.format((memory.getTotal() - memory.getAvailable()) / (double) memory.getTotal() * 100));
		return memoryInfo;
	}

	/**
	 * 获取Cpu相关信息
	 */
	private Map<String, Object> getCpuInfo(CentralProcessor processor) {
		Map<String, Object> cpuInfo = new LinkedHashMap<>();
		cpuInfo.put("name", processor.getProcessorIdentifier().getName());
		cpuInfo.put("package", processor.getPhysicalPackageCount() + "个物理CPU");
		cpuInfo.put("core", processor.getPhysicalProcessorCount() + "个物理核心");
		cpuInfo.put("coreNumber", processor.getPhysicalProcessorCount());
		cpuInfo.put("logic", processor.getLogicalProcessorCount() + "个逻辑CPU");
		// CPU信息
		long[] prevTicks = processor.getSystemCpuLoadTicks();
		// 等待 600 ms...
		Util.sleep(600);
		long[] ticks = processor.getSystemCpuLoadTicks();
		long user = ticks[CentralProcessor.TickType.USER.getIndex()]
				- prevTicks[CentralProcessor.TickType.USER.getIndex()];
		long nice = ticks[CentralProcessor.TickType.NICE.getIndex()]
				- prevTicks[CentralProcessor.TickType.NICE.getIndex()];
		long sys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()]
				- prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
		long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()]
				- prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
		long ioWait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()]
				- prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
		long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()]
				- prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
		long softIrq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()]
				- prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
		long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()]
				- prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
		long totalCpu = user + nice + sys + idle + ioWait + irq + softIrq + steal;
		cpuInfo.put("used", df.format(100d * user / totalCpu + 100d * sys / totalCpu));
		cpuInfo.put("idle", df.format(100d * idle / totalCpu));
		return cpuInfo;
	}

	/**
	 * 获取系统相关信息,系统、运行天数、系统IP
	 */
	private Map<String, Object> getSystemInfo(OperatingSystem os) {
		Map<String, Object> systemInfo = new LinkedHashMap<>();
		// jvm 运行时间
		Duration upTime = RuntimeUtil.getUpTime();
		// 系统信息
		systemInfo.put("os", os.toString());
		systemInfo.put("day", formatDuration(upTime));
		systemInfo.put("ip", INetUtil.getHostIp());
		return systemInfo;
	}

	public static String formatDuration(Duration duration) {
		long seconds = duration.getSeconds();
		long absSeconds = Math.abs(seconds);
		String positive = String.format("%d:%02d:%02d", absSeconds / 3600, (absSeconds % 3600) / 60, absSeconds % 60);
		return seconds < 0 ? "-" + positive : positive;
	}

}
