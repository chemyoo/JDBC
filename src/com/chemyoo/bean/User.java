package com.chemyoo.bean;

import java.util.Date;

import com.chemyoo.annotations.Field;
import com.chemyoo.annotations.Table;
import com.chemyoo.utils.TimeUtil;

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
	
	private Date modifiedTime = TimeUtil.getNow();

}
