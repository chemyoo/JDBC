package com.chemyoo.utils;

import java.math.BigDecimal;

/**
 * @author 作者 : jianqing.liu
 * @version 创建时间：2018年5月11日 下午1:22:32
 * @since 2018年5月11日 下午1:22:32
 * @description 关于数字的工具类
 */
public class NumberUtils {

	private NumberUtils() {
	}

	/**
	 * 为浮点型数据设置精度
	 * 
	 * @param num
	 * @param scale
	 *            小数位数
	 * @return
	 */
	public static double setDoubleScale(double num, int scale) {
		return BigDecimal.valueOf(num).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 为浮点型数据设置精度
	 * 
	 * @param num
	 * @param scale
	 *            小数位数
	 * @return
	 */
	public static double setDoubleScale2(double num, int scale) {
		long base = 1;
		for (int i = 0; i < scale; i++) {
			base *= 10;
		}
		return Math.round(num * base) / (base * 1.0);
	}

}
