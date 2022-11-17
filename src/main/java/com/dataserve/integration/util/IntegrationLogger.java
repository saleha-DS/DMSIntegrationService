package com.dataserve.integration.util;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

public class IntegrationLogger {
	
	private static Log LOG = LogFactory.getLog(IntegrationLogger.class);
	
	public static void error(String message) {
		LOG.error(message);
	}
	
	public static void error(String message, Throwable t) {
		LOG.error(message, t);
	}
	
	public static void warn(String message) {
		LOG.warn(message);
	}
	
	public static void warn(String message, Throwable t) {
		LOG.error(message, t);
	}
	
	public static void debug(String message) {
		LOG.error(message);
	}
	
	public static void debug(String message, Throwable t) {
		LOG.error(message, t);
	}
	
	public static void info(String message) {
		LOG.error(message);
	}	
	
}
