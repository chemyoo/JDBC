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
	
	Integer(Integer.class,"INT"),
	Double(Double.class,"NUMBER"),
	Long(Long.class,"INT"),
	Short(Short.class,"INT"),
	Char(Character.class,"CHAR"),
	String(String.class,"VARCHAR2"),
	BigDecimal(BigDecimal.class,"NUMBER"),
	Date(Date.class,"TIMESTAMP"),
	Float(Float.class,"NUMBER"),
	Boolean(Boolean.class,"char(2)");
	
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
	 * @param clazz the clazz to set
	 */
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	/**
	 * @return the sqlType
	 */
	public String getSqlType() {
		return sqlType;
	}

	/**
	 * @param sqlType the sqlType to set
	 */
	public void setSqlType(String sqlType) {
		this.sqlType = sqlType;
	}
	
	public static String getSqlType(Class<?> dataType,Field field) {
		String sqlType = "varchar2";
		if(dataType != null) {
			JavaType[] values = values();
			int length = 255;
			for(JavaType type : values) {
				if(dataType == type.getClazz()) {
					if(field != null) {
						length = field.length();
					}
					if(type == JavaType.Char || type == JavaType.String) {
						sqlType = type.getSqlType() + "(" + length + ")";
					}
					else if(dataType == type.getClazz()) {
						sqlType = type.getSqlType();
					}
					break;
				}
			}
		}
		return sqlType;
	}

}
