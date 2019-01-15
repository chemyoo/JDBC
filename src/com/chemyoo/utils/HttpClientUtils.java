package com.chemyoo.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.chemyoo.entiry.Charset;
import com.chemyoo.enums.FileType;

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
				isReader = new InputStreamReader(inputStream, Charset.UTF_8.getCharset());
				bufferedReader = new BufferedReader(isReader);
				data = IOUtils.toString(bufferedReader);
			} else {
				logger.error("打开网页失败...");
			}
			httpConnection.disconnect();
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
	
	public static String dowload(String domain,String fileName) {
		InputStream inputStream = null;
		InputStreamReader isReader = null;
		BufferedReader bufferedReader = null;
		String fileExt = null;
		try (OutputStream outStream = new FileOutputStream(fileName)){
			// 通过URL从互联网中读取一个网页
			URL url = new URL(domain);
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				inputStream = httpConnection.getInputStream();
				long contentLength = httpConnection.getContentLengthLong();
				String size = String.format("文件大小%.2fMB，", contentLength * 1F / (1024 * 1024));
				byte[] b = new byte[28];
				long count = 0;
				for(int ch = -1,index = 0;(ch = inputStream.read()) != -1;index ++) {
					outStream.write(ch);
					if(index < 28) {
						b[index] = (byte) ch;
					}
					count ++;
					System.err.println(String.format("下载进度%.2f", count * 100F / contentLength) + "%");
				}
				System.err.println(size);
				fileExt = FileType.getFileType(ChemyooUtils.bytesToHexString(b));
				outStream.flush();
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
		return fileExt;
	}
	/**
	 * 下载时同时进行文件命名校正
	 * @param domain
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static String downloadFileWithCheckFileType(String domain,String fileName) throws IOException {
		String fileType = dowload(domain, fileName);
		String finalName = fileName;
		if(fileType != FileType.UNKNOWN.getName() && !fileName.endsWith(fileType) && 
				!(fileName.endsWith(FileType.XLSX_DOCX.getName()) && fileType == FileType.ZIP.getName())) {
			finalName = fileName+ "." + fileType;
			FileUtils.copyFile(new File(fileName), new File(finalName));
			FileUtils.deleteQuietly(new File(fileName));
		}
		return finalName;
	}
	
	public static String post(String text,String link) {
		String result = "";
        try {
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "Keep-Alive");//保持长链接
            conn.setRequestProperty("Charset", "UTF-8");
            // 设置文件类型:
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            // 设置接收类型否则返回415错误
            // conn.setRequestProperty("accept","*/*")此处为设置接受所有类型，以此来防范返回415;
            conn.setRequestProperty("accept","application/json");
            // 往服务器里面发送数据
            if (text != null && !text.isEmpty()) {
                byte[] writebytes = ("text=" + text).getBytes();
                // 设置文件长度
                conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                OutputStream outwritestream = conn.getOutputStream();
                outwritestream.write(writebytes);
                outwritestream.flush();
                outwritestream.close();
            }
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                result = IOUtils.toString(conn.getInputStream());
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
		return result;
	}
	
}
