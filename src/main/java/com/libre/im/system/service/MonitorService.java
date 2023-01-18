package com.libre.im.system.service;

import java.util.Map;

/**
 * @author: Libre
 * @Date: 2023/1/8 12:09 AM
 */
public interface MonitorService {

	Map<String, Object> getServerInfo();

	Map<String, Object> getRedisStat();

}
