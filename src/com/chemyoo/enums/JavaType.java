package com.chemyoo.enums;

import java.math.BigDecimal;
import java.util.Date;

import com.chemyoo.annotations.Field;

/**
 * @author 作者 : jianqing.liu
 * @version 创建时间：2018年2月6日 下午2:24:25
 * @since 2018年2月6日 下午2:24:25
 * @description 类说明
 */
public enum JavaType {

	INTEGER(Integer.class, "INT"), 
	DOUBLE(Double.class, "NUMBER"),
	LONG(Long.class, "NUMBER"), 
	SHORT(Short.class,"INT"), 
	CHAR(Character.class, "CHAR"), 
	STRING(String.class, "VARCHAR2"), 
	BIGDECIMAL(BigDecimal.class, "NUMBER"), 
	DATE(Date.class, "TIMESTAMP"), 
	FLOAT(Float.class, "NUMBER"), 
	BOOLEAN(Boolean.class, "char(2)");

	private Class<?> clazz;
	private String sqlType;

	JavaType(Class<?> clazz, String sqlType) {
		this.clazz = clazz;
		this.sqlType = sqlType;
	}

	/**
	 * @return the clazz
	 */
	public Class<?> getClazz() {
		return clazz;
	}

	/**
	 * @return the sqlType
	 */
	public String getSqlType() {
		return sqlType;
	}

	public static String getSqlType(Class<?> dataType, Field field) {
		String sqlType = "varchar2";
		if (dataType != null) {
			JavaType[] values = values();
			int length = 255;
			for (JavaType type : values) {
				if (dataType == type.getClazz()) {
					if (field != null) {
						length = field.length();
					}
					if (type == JavaType.CHAR || type == JavaType.STRING) {
						sqlType = type.getSqlType() + "(" + length + ")";
					} else if (dataType == type.getClazz()) {
						sqlType = type.getSqlType();
					}
					break;
				}
			}
		}
		return sqlType;
	}

}
