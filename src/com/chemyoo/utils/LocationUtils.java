package com.chemyoo.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.apache.log4j.Logger;
import com.chemyoo.entiry.GeographicCoordinates;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Copy form https://blog.csdn.net/sanyuesan0000/article/details/51683464 by
 * jianqing.liu on 2018/04/25.
 * 
 * @author chemyoo
 * @description 地理坐标转距离工具类
 */
public class LocationUtils {

	private static Logger logger = Logger.getLogger(LocationUtils.class);

	private LocationUtils() {
	}

	/** 地球半径(m) */
	private static final double EARTH_RADIUS = 6378.137 * 1000;

	private static final String DEVELOPER_KEY = "a9c628770e0737a6388829211283e8ad";

	private static final String API_URL = "http://restapi.amap.com/v3/geocode/geo?address=%s&output=JSON&key=%s";

	/**
	 * 通过地址从高德地图获取其经纬度
	 * 
	 * @param address
	 *            地址（行政区划，地址，城市名称，城市全拼）
	 * @return
	 */
	public static String getCoordinate(String address) {
		try {
			address = URLEncoder.encode(address, "UTF-8");
			String url = String.format(API_URL, address, DEVELOPER_KEY);
			JSONObject json = JSONObject.fromObject(HttpClientUtils.getRedirectUrl(url));
			if (json.getInt("status") == 1) {
				JSONArray array = json.getJSONArray("geocodes");
				if (!array.isEmpty()) {
					return array.getJSONObject(0).getString("location");
				}
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException:", e);
		}
		return "";
	}

	/** 经纬度转弧度 */
	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 通过经纬度获取距离(单位：米)
	 * 
	 * @param point1
	 *            [经度, 纬度]
	 * @param point2
	 *            [经度, 纬度]
	 * @return
	 */
	public static double getDistance(GeographicCoordinates point1, GeographicCoordinates point2) {
		double radLat1 = rad(point1.getLatitude());
		double radLat2 = rad(point2.getLatitude());
		double a = radLat1 - radLat2;
		double b = rad(point1.getLongitude()) - rad(point2.getLongitude());
		double distance = 2 * Math.asin(Math.sqrt(
				Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		return Math.round(distance * 10000d * EARTH_RADIUS) / 10000d;
	}
}