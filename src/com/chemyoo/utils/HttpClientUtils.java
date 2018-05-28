package com.chemyoo.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/**
 * @author 作者 : jianqing.liu
 * @version 创建时间：2018年5月10日 上午9:34:17
 * @since 2018年5月10日 上午9:34:17
 * @description 后台打开网页工具，从一个URL获取返回结果的工具
 */
public class HttpClientUtils {

	private static Logger logger = Logger.getLogger(HttpClientUtils.class);

	private HttpClientUtils() {
	}

	/**
	 * 打开一个连接并获取返回内容
	 * 
	 * @param domain
	 *            域名或IP地址
	 * @return
	 */
	public static String getRedirectUrl(String domain) {
		String data = null;
		InputStream inputStream = null;
		InputStreamReader isReader = null;
		BufferedReader bufferedReader = null;
		try {
			// 通过URL从互联网中读取一个网页
			URL url = new URL(domain);
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				inputStream = httpConnection.getInputStream();
				isReader = new InputStreamReader(inputStream);
				bufferedReader = new BufferedReader(isReader);
				data = IOUtils.toString(bufferedReader);
			} else {
				logger.error("打开网页失败...");
			}
		} catch (Exception e) {
			logger.error("打开网页发生异常:", e);
		} finally {
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(isReader);
			IOUtils.closeQuietly(bufferedReader);
		}
		return data;
	}
	
	/**
	 * 从网络地址下载文件至本地
	 * 
	 * @param domain 地址
	 * @param downLoadPath 文件存放路径
	 * @throws IOException
	 */
	public static void downloadFromInternet(String domain, String downLoadPath) throws IOException{
		FileUtils.copyURLToFile(new URL(domain), new File(downLoadPath));
	}
	
}
