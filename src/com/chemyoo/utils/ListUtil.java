package com.chemyoo.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;


/**
 * @author 作者 : jianqing.liu
 * @version 创建时间：2018年1月4日 上午11:29:03
 * @since 2018年1月4日 上午11:29:03
 * @description 类说明
 */
public class ListUtil {
	private ListUtil() {}

	public static <T> List<Object> getFieldValues(List<T> list, String fieldName) {
		Validate.notEmpty(fieldName, "fieldName 不能为空！");
		List<Object> fieldValues = new ArrayList<Object>();
		if (ChemyooUtils.isNotEmpty(list)) {
			try {
				for (T type : list) {
					Field f = type.getClass().getDeclaredField(fieldName);
					if(f == null) {
						break;
					}
					f.setAccessible(true);
					fieldValues.add(f.get(type));
					f.setAccessible(false);
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
		return fieldValues;
	}

	
	public static <T> List<Map<String,Object>> listBeanToListMap(List<T> list,String... jsonInculdeFields)
	{
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		if (ChemyooUtils.isEmpty(list) || jsonInculdeFields == null || jsonInculdeFields.length == 0) {
			return returnList;
		}
		Map<String, Object> map = null;
		List<String> getMethodsFields = new ArrayList<String>();
		for (String fieldName : jsonInculdeFields) {
			if(StringUtils.isNotEmpty(fieldName)) {
				getMethodsFields.add("get"+fieldName.toLowerCase().trim());
			}
		}
		try {
			for (T t : list) {
				map = new TreeMap<String, Object>();
				Method[] methods = t.getClass().getDeclaredMethods();
				for (Method method : methods) {
					int lastIndexOf = getMethodsFields.lastIndexOf(method.getName().toLowerCase());
					if (lastIndexOf > -1) {
						map.put(jsonInculdeFields[lastIndexOf].trim(), method.invoke(t));
					}
				}
				if(!map.isEmpty()) {
					returnList.add(map);
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	public static <T> List<Map<String,Object>> beanToListMap(T bean,String... jsonInculdeFields)
	{
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		if (bean == null || jsonInculdeFields == null || jsonInculdeFields.length == 0) {
			return returnList;
		}
		Map<String, Object> map = null;
		List<String> getMethodsFields = new ArrayList<String>();
		for (String fieldName : jsonInculdeFields) {
			if (StringUtils.isNotEmpty(fieldName)) {
				getMethodsFields.add("get"+fieldName.toLowerCase().trim());
			}
		}
		try {
			map =  new TreeMap<String, Object>();
			Method[] methods = bean.getClass().getDeclaredMethods();
			for (Method method : methods) {
				int lastIndexOf = getMethodsFields.lastIndexOf(method.getName().toLowerCase());
				if (lastIndexOf > -1) {
					map.put(jsonInculdeFields[lastIndexOf].trim(), method.invoke(bean));
				}
			}
			if(!map.isEmpty()) {
				returnList.add(map);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return returnList;
	}
}
