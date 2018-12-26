package com.chemyoo.bean;

import org.apache.commons.lang.Validate;

/** 
 * @author Author : jianqing.liu
 * @version version : created time：2018年10月19日 下午5:42:34 
 * @since since from 2018年10月19日 下午5:42:34 to now.
 * @description class description
 */
public class Point {
	
	private double x;
	private double y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(double[] point) {
		Validate.isTrue(point.length >= 2, "");
		this.x = point[0];
		this.y = point[1];
	}

	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[x=" + x + ", y=" + y + "]";
	}
	
}
