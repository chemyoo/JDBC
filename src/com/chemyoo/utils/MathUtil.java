package com.chemyoo.utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.chemyoo.bean.Point;

/** 
 * @author Author : jianqing.liu
 * @version version : created time：2018年10月19日 下午5:33:49 
 * @since since from 2018年10月19日 下午5:33:49 to now.
 * @description class description
 */
public class MathUtil {
	private MathUtil() {}
	
	/**
	* 一元线性拟合
	* y = a + k*x
	* @param x  
	* @param y
	* r 相关系数<br>
	* r^2 决定系数<br>
	* @自由度 degree of freedom，简称 df;
	* 指的是计算某一统计量时，取值不受限制的变量个数。
	* 通常df=n-k。其中n为样本数量，k为被限制的条件数或变量个数，
	* 或计算某一统计量时用到其它独立统计量的个数。<br>
	* @点数量（数据数量）
	*/ 
	public static double[] lineFitting(List<Point> data) {
		if(data.isEmpty()) {
			return new double[] {};
		}
		int pointCount = data.size();
		double sumX = 0d;
		double sumY = 0d;
		double sumMutiplyX = 0d;
		double sumMutiplyY = 0d;
		double sumMutiplyXY = 0d;
		for(Point point : data) {
			double x = point.getX();
			double y = point.getY();
			sumX += x;
			sumY += y;
			sumMutiplyX += x * x;
			sumMutiplyY += y * y;
			sumMutiplyXY += x * y;
		}
		double averageX = sumX / pointCount;
		double averageY = sumY / pointCount;
		double sumXSquare = 0d;
		double sumXY = 0d;
		for(Point point : data) {
			double x = point.getX();
			double y = point.getY();
			sumXSquare += Math.pow(x - averageX, 2);
			sumXY += (y - averageY) * (x - averageX);
		}
		double k = sumXY / sumXSquare;
		double c = averageY - k * averageX;
		double correlation = (sumMutiplyXY - sumX * sumY / pointCount) 
				/ Math.sqrt((sumMutiplyX - sumX * sumX / pointCount) * (sumMutiplyY - sumY * sumY / pointCount));
		System.out.println("c = " + c + ", k = " + k);
		System.out.println("相关系数：" + correlation);
		int degreeOfFreedom = pointCount - 1 -1;
		double rss = 0d;
		double tss = 0d;
		for (Point point : data)
		{
			double x = point.getX();
			double y = point.getY();
			rss += (y - (c + k * x)) * (y - (c + k * x));
			tss += (y - averageY) * (y - averageY);
		}
		double r2 = 1 - (rss / degreeOfFreedom) / (tss / (pointCount - 1));
		System.out.println("训练数据：" + data);
		System.out.println("解得曲线函数为 ：y = " + k +"x + " + c);
		return new double[] {c, k, correlation, r2, pointCount, degreeOfFreedom};
	}
	
	/**
	 * 排列 {@code A(n,m)}
	 * @return
	 */
	public static BigInteger permutation(int n, int m) {
		if(m > n) {
			throw new IllegalArgumentException("the n(total of number) must more than m(selected element)");
		}
		return factorial(n).divide(factorial(n - m));
	}
	
	/**
	 * 阶乘 {@code (n!)}
	 * @return
	 */
	public static BigInteger factorial(int n) {
		if(n < 0) {
			throw new IllegalArgumentException("the n(total of number) must not be negative.");
		}
		BigInteger value = BigInteger.ONE;
		for(int i = n; i > 0; i --) {
			value = value.multiply(BigInteger.valueOf(i));
		}
		return value;
	}
	
	/**
	 * 组合 {@code C(n,m)} 
	 * @return
	 */
	public static BigInteger combination(int n, int m) {
		return permutation(n, m).divide(factorial(m));
	}
	
	public static void main(String[] args) {
		List<Point> data = new ArrayList<>();
		for(int i = 0; i < 5; i ++) {
			Point p = new Point(i + 1, i * 8 -3);
			data.add(p);
		}
		lineFitting(data);
		System.err.println(combination(9,2).multiply(permutation(4,2)));
	}
	
}
