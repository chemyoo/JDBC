package com.chemyoo.utils;

import com.chemyoo.entiry.GeographicCoordinates;

/** 
 * Copy form https://blog.csdn.net/sanyuesan0000/article/details/51683464 by jianqing.liu on 2018/04/25. 
 * @author chemyoo
 * @description 地理坐标转距离工具类
 */  
public class LocationUtils {  
	
	private LocationUtils(){}
	
	/**地球半径(m)*/
    private static final double EARTH_RADIUS = 6378.137 * 1000;  

    /**经纬度转弧度*/
    private static double rad(double d) {  
        return d * Math.PI / 180.0;  
    }  

    /** 
     * 通过经纬度获取距离(单位：米) 
     * @param point1 [经度, 纬度]
     * @param point2 [经度, 纬度]
     * @return 
     */  
    public static double getDistance(GeographicCoordinates point1, GeographicCoordinates point2) {  
        double radLat1 = rad(point1.getLatitude());  
        double radLat2 = rad(point2.getLatitude());  
        double a = radLat1 - radLat2;  
        double b = rad(point1.getLongitude()) - rad(point2.getLongitude());  
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)  
                + Math.cos(radLat1) * Math.cos(radLat2)  
                * Math.pow(Math.sin(b / 2), 2)));  
        return Math.round(distance * 10000d * EARTH_RADIUS) / 10000d;
    }  
}  