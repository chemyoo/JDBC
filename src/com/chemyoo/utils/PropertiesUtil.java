package com.chemyoo.utils;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

	public static Properties getProperties(String filePath) {
		Properties prop = new Properties();
		try {
			ReadFileToStream readFile = new ReadFileToStream(filePath);
			prop.load(readFile.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

}
