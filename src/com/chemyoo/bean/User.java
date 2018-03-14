package com.chemyoo.bean;

import java.util.Date;

import com.chemyoo.annotations.Field;
import com.chemyoo.annotations.Table;
import com.chemyoo.utils.TimeUtils;

/** 
 * @author 作者 : jianqing.liu
 * @version 创建时间：2018年2月11日 上午9:31:56 
 * @since 2018年2月11日 上午9:31:56 
 * @description 类说明 
 */
@Table(name = "user_info")
public class User {
	
	@Field(length = 32, primaryKey = true)
	private String pk;
	
	private String userCode;
	
	private String userName;
	
	private Integer role;
	
	private Date createTime;
	
	private Integer dr;
	
	private Date modifiedTime = TimeUtils.getNow();

	/**
	 * @return the pk
	 */
	public String getPk() {
		return pk;
	}

	/**
	 * @param pk the pk to set
	 */
	public void setPk(String pk) {
		this.pk = pk;
	}

	/**
	 * @return the userCode
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * @param userCode the userCode to set
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the role
	 */
	public Integer getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Integer role) {
		this.role = role;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the dr
	 */
	public Integer getDr() {
		return dr;
	}

	/**
	 * @param dr the dr to set
	 */
	public void setDr(Integer dr) {
		this.dr = dr;
	}

	/**
	 * @return the modifiedTime
	 */
	public Date getModifiedTime() {
		return modifiedTime;
	}

	/**
	 * @param modifiedTime the modifiedTime to set
	 */
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	
}
