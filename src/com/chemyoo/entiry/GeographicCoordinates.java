package com.chemyoo.entiry;

/**
 * @author 作者 : jianqing.liu
 * @version 创建时间：2018年4月26日 下午3:18:38
 * @since 2018年4月26日 下午3:18:38
 * @description 地理坐标（经度，维度） 格式错误或不合法将会设置为0.0D
 */
public class GeographicCoordinates {

	public GeographicCoordinates(double longitude, double latitude) {
		if (validateLatitude(latitude))
			this.latitude = latitude;
		if (validateLongitude(longitude))
			this.longitude = longitude;
	}

	/** 经度 */
	private double longitude = 0.0D;

	/** 纬度 */
	private double latitude = 0.0D;

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(double longitude) {
		if (validateLongitude(longitude))
			this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(double latitude) {
		if (validateLatitude(latitude))
			this.latitude = latitude;
	}

	private boolean validateLongitude(double longitude) {
		return (longitude >= -180D && longitude <= 180D);
	}

	private boolean validateLatitude(double latitude) {
		return (latitude >= -90D && latitude <= 90D);
	}

}
