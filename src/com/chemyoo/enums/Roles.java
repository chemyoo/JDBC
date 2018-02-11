package com.chemyoo.enums;
/** 
 * @author 作者 : jianqing.liu
 * @version 创建时间：2018年2月11日 上午11:12:59 
 * @since 2018年2月11日 上午11:12:59 
 * @description 类说明 
 */
public enum Roles {
	
	UNKNOWN(-1,"未知代号"),
	VISITOR(1,"游客/访客/单点登入用户"),
	COMMON(2,"普通用户"),
	ADMINISTRATOR(3,"管理员"),
	ROOT(4,"超级管理员");
	
	private Integer code;
	
	private String name;
	
	Roles(Integer code, String name){
		this.code = code;
		this.name = name;
	}

	/**
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(Integer code) {
		this.code = code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public static String findRoleNameByCode(Integer code) {
		if(code != null) { 
			Roles[] roles = values();
			for(Roles role : roles) {
				if(role.code == code) {
					return role.name;
				}
			}
		}
		return UNKNOWN.name;
	}
	
}
