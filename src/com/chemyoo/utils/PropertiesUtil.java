package com.chemyoo.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 单例模式
 * 获取自定义Properties文件所有属性
 * @author chemyoo
 */
public class PropertiesUtil {
	
	private PropertiesUtil() {}
	
	private static Properties instanse = new Properties();

	public static Properties getProperties(String fileName) {
		return getProperties(new File(SystemUtils.getClassPath() + SystemUtils.getFileSeparator() + fileName));
	}
	
	public static Properties getProperties(File file) {
		if(instanse.size() > 0) {
			return instanse;
		}
		initProperties(file);
		return instanse;
	}
	
	private static synchronized void initProperties(final File filePath) {
		if(instanse.size() > 0) {
			return;
		}
		try (
				FileInputStream fileInput = new FileInputStream(filePath);
				InputStream inputStream = new BufferedInputStream(fileInput);
			) {
			instanse.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

}
