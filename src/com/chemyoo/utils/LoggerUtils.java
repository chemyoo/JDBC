package com.chemyoo.utils;
/** 
 * @author 作者 : jianqing.liu
 * @version 创建时间：2018年5月10日 上午9:45:03 
 * @since 2018年5月10日 上午9:45:03 
 * @description 类说明 
 */

import org.apache.log4j.Logger;

public abstract class LoggerUtils {

	private LoggerUtils() {
	}

	private static Logger log = Logger.getRootLogger();

	public static void logError(Class<?> clazz, String message) {
		log.error(LoggerUtils.buildString(clazz, message));
	}

	public static void logWarn(Class<?> clazz, String message) {
		log.warn(LoggerUtils.buildString(clazz, message));
	}
	
	public static void logInfo(Class<?> clazz, String message) {
		log.info(LoggerUtils.buildString(clazz, message));
	}

	public static void logError(Class<?> clazz, String message, Exception e) {
		log.error(LoggerUtils.buildString(clazz, message), e);
	}

	private static String buildString(Class<?> clazz, String message) {
		StringBuilder stringBuffer = new StringBuilder();
		stringBuffer.append(TimeUtils.convertDateToString(TimeUtils.getNow(), "yyyy-MM-dd HH:mm:ss"));
		return stringBuffer.append(" ").append(clazz.getName()).append(" : ").append(message).toString();
	}
}
