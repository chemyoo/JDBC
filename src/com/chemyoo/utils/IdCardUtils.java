package com.chemyoo.utils;

public class IdCardUtils {
	
	private IdCardUtils() {}
	
	private static int[] w = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
	private static char[] verifyCode = "10X98765432".toCharArray();
	private static char zero = '0';

	public static boolean isValid(String id) {
		if (id.length() < 18) {
			return false;
		}
		char[] c = id.toCharArray();
		int sum = 0;
		for (int i = 0; i < w.length; i++) {
			sum += (c[i] - zero) * w[i];
		}
		char ch = verifyCode[sum % 11];
		return c[17] == ch;

	}

}
