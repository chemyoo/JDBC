package com.chemyoo.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.util.Date;

/**
 * @author Author : jianqing.liu
 * @version version : created time：2018年9月26日 下午6:06:17
 * @since since from 2018年9月26日 下午6:06:17 to now.
 * @description class description
 */
public class Model extends AbstractModel {

	@Override
	public String toString() {
		String className = this.getClass().getSimpleName();
		StringBuilder buffer = new StringBuilder("{").append(className + ": {");
		Field[] fields = this.getClass().getDeclaredFields();
		try {
			for (Field field : fields) {
				Class<?> clazz = field.getType();
				// 是基本类型/简单类型
				if ((clazz.isPrimitive() || clazz == Short.class || clazz == Double.class || clazz == Float.class
						|| clazz == Byte.class || clazz == Character.class || clazz == String.class
						|| clazz == Integer.class || clazz == Long.class || clazz == Date.class
						|| clazz == Boolean.class) && !Modifier.isStatic(field.getModifiers())) {
					field.setAccessible(true);
					Object value = field.get(this);
					field.setAccessible(false);
					if (value instanceof String) {
						buffer.append(field.getName()).append(": ").append(this.formatString(value.toString()));
					} else if (value instanceof Date) {
						DateFormat format = DateFormat.getDateTimeInstance();
						buffer.append(field.getName() + ": \"" + format.format(value) + "\", ");
					} else {
						buffer.append(field.getName() + ": " + value + ", ");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int length = buffer.length();
		// if end with comma, slice it.
		if (buffer.lastIndexOf(", ") == length - 2)
			buffer.delete(length - 2, length);

		buffer.append("}}");
		return buffer.toString();
	}

	private String formatString(String str) {
		StringBuilder stringBuilder = new StringBuilder();
		if (str.startsWith("{") && str.endsWith("}"))
			stringBuilder.append(str).append(", ");
		else
			stringBuilder.append("\"").append(str).append("\", ");

		return stringBuilder.toString();
	}
	
}
