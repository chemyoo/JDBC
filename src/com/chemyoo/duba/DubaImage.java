package com.chemyoo.duba;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.chemyoo.utils.HttpClientUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DubaImage {
	
	private static Logger logger = Logger.getLogger(DubaImage.class);
	
	private static final String M_URL = "http://list.wallpaper.cloud.duba.net/wallpaper/web?cb=jQuery111305475931089974584_1565162916654&photo_type=5&photo_cnt=24&photo_id=%d&cityid=101280701&tryno=3004&_=1565162916658";
	
	private static final String F_URL = "http://list.wallpaper.cloud.duba.net/wallpaper/web?cb=jQuery111305475931089974584_1565162916663&photo_type=1&photo_cnt=24&photo_id=%d&cityid=101280701&tryno=3004&_=1565162916745";
	
	private static final String imgSrc = "http://wallpaperm.cmcm.com/%s.jpg";
	
	private static ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
	
	public static JSONObject getResourseMd5Values(String url) {
		try {
			String data = HttpClientUtils.getRedirectUrl(url);
			int index = data.indexOf('(');
			String split = data.substring(index + 1);
			String jsonStr = split.substring(0, split.length() - 1);
			return JSONObject.fromObject(jsonStr);
		} catch (Exception e) {
			logger.error(e);
		} 
		return JSONObject.fromObject("{}");
	}
	
	public static void main(String[] args) {
		service.submit(new DownMPic());
		service.submit(new DownFPic());
		service.shutdown();
	}
	
	static class DownMPic implements Runnable {

		@Override
		public void run() {
			int total = 19580;
			for(int i = 360; i <= total; i = i + 24) {
				String url = String.format(M_URL, i);
				JSONObject json = getResourseMd5Values(url);
				if(json.isEmpty()) continue;
				JSONArray array = json.getJSONArray("pic");
				for(int j = 0, length = array.size(); j < length; j ++) {
					String value = array.getJSONObject(j).getString("md5");
					String imgurl = String.format(imgSrc, value);
					logger.info("DownMPic正在下载第" + (i + j + 1) + "张图片");
					DubaImage.dowload(imgurl, "D:/picture/spider/images/20190807/" + value + ".jpg");
				}
			}
			logger.info("DownMPic运行完成");
		}
	}
	
	static class DownFPic implements Runnable {

		@Override
		public void run() {
			int total = 21958;
			for(int i = 48; i <= total; i = i + 24) {
				String url = String.format(F_URL, i);
				JSONObject json = getResourseMd5Values(url);
				if(json.isEmpty()) continue;
				JSONArray array = json.getJSONArray("pic");
				for(int j = 0, length = array.size(); j < length; j ++) {
					String value = array.getJSONObject(j).getString("md5");
					String imgurl = String.format(imgSrc, value);
					logger.info("DownFPic正在下载第" + (i + j + 1) + "张图片");
					DubaImage.dowload(imgurl, "D:/picture/spider/images/20190807/" + value + ".jpg");
				}
			}
			logger.info("DownFPic运行完成");
		}
	}
	
	public static void dowload(String domain,String fileName) {
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
				try (OutputStream outStream = new FileOutputStream(fileName)){
					inputStream = httpConnection.getInputStream();
					long contentLength = httpConnection.getContentLengthLong();
					String size = String.format("文件大小%.2fMB", contentLength * 1F / (1024 * 1024));
					System.err.println(size);
					List<Integer> list = new ArrayList<>();
					for(int c = inputStream.read(); c != -1; c = inputStream.read()) {
						list.add(c);
						int listsize = list.size();
						if(listsize >= 1024 * 128) {
							outStream.write(toBytes(list));
							outStream.flush();
							list.clear();
						}
					}
					if(!list.isEmpty()) {
						outStream.write(toBytes(list));
						outStream.flush();
					}
				}
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
	}
	
	private static byte[] toBytes(List<Integer> list) {
		int listsize = list.size();
		byte[] bytes = new byte[listsize];
		for(int i = 0; i < listsize; i++) {
			bytes[i] = list.get(i).byteValue();
		}
		return bytes;
	}
	
}
