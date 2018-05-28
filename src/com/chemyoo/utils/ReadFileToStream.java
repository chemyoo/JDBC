package com.chemyoo.utils;

import java.io.*;

public class ReadFileToStream {

	private InputStream inputStream;

	public ReadFileToStream(String filePath) {
		try {
			// 读取文件为文件流
			FileInputStream fileInput = new FileInputStream(filePath);
			inputStream = new BufferedInputStream(fileInput);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public ReadFileToStream(File file) {
		try {
			// 读取文件为文件流
			FileInputStream fileInput = new FileInputStream(file);
			inputStream = new BufferedInputStream(fileInput);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void closeQuietly() {
		IOUtils.closeQuietly(inputStream);
	}

}
