package com.chemyoo.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author 作者 : jianqing.liu
 * @version 创建时间：2018年4月16日 上午9:30:23
 * @since 2018年4月16日 上午9:30:23
 * @description 类说明
 */
public class IOUtils {
	private IOUtils() {
	}

	public static void closeQuietly(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (IOException ioe) {
			// ignore
		}
	}
}
