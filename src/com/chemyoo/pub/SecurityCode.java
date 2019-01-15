package com.chemyoo.pub;

/**
 * 对字符串进行加密
 * 
 * @author chemyoo
 * @since 2016-12-19
 */
public class SecurityCode {

	private SecurityCode() {
	}

	public static synchronized String encrypt(String codestr) {
		char[] letter = {'A', 'B', 'C','D', 'G', 'E', 'F', 'I','H', 'Q','J',
				'K', 'L', 'P', 'M', 'N', 'O', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		StringBuffer strbuff = new StringBuffer("");
		if (!emptyString(codestr)) {
			int length = codestr.length();
			int sum = 0;
			for (int j = 0;; j++) {
				sum += codestr.codePointAt(j);
				if (j >= length - 1)
					break;
			}
			int seed = sum % 10;
			if (seed < 3)
				seed = seed + 2;
			sortLetter(letter, seed);
			int size = letter.length;
			int i = 0;
			for (; i < length; i++) {
				int c = (codestr.codePointAt(i) >> ((i + 1) % 7)) % 100;
				strbuff.append(c);
				if (i == 0)
					strbuff.append(letter[seed]);
				else if (i % 3 == 0)
					strbuff.append(letter[(i - 1) <= size ? (i - 1) : (i % size)]);
				else if (i % 3 == 2)
					strbuff.append(letter[i + 1 <= size ? i : ((i + 1) % size)]);
				else
					strbuff.append(codestr.codePointAt(i >> 2 > length ? i : i >> 1) % 100);
			}
		}
		return strbuff.toString();
	}

	private static void sortLetter(char[] array, int seed) {
		int size = array.length;
		int i = 0;
		for (int end = size; i < end; i++) {
			char temp = array[i];
			int index = i + (i + 1) / seed;
			if (index >= size)
				index = size - i;
			array[i] = array[index];
			array[index] = temp;
		}
	}

	private static boolean emptyString(String str) {
		return (str == null || str.trim().isEmpty());
	}
	
	public static void main(String[] args) {
		for(int i = 0; i < 26; i++) {
			System.err.println((char)(65+i)+":" + SecurityCode.encrypt("" + (char)(65+i)));
		}
		System.err.println(SecurityCode.encrypt("fhafr8efnsvn"));
		// 4H490F2F305E2E001Q6Q903L1L
		// 4J490S2S305C2C001K6K903N1N42
	}
}