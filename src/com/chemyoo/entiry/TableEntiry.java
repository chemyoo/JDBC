package com.chemyoo.entiry;

import java.util.ArrayList;
import java.util.List;

/** 
 * @author 作者 : jianqing.liu
 * @version 创建时间：2018年1月20日 下午2:09:12 
 * @since 2018年1月20日 下午2:09:12 
 * @description 类说明 
 */
public class TableEntiry {
	
	private String tableName;
	
	private List<ColunmEntiry> colunms;

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the colunms
	 */
	public List<ColunmEntiry> getColunms() {
		return colunms;
	}

	/**
	 * @param colunms the colunms to set
	 */
	public void setColunms(List<ColunmEntiry> colunms) {
		this.colunms = colunms;
	}
	
	public void addColunm(ColunmEntiry colunm) {
		if(this.colunms == null) {
			this.colunms = new ArrayList<ColunmEntiry>();
		}
		this.colunms.add(colunm);
	}
	
	public void addColunms(List<ColunmEntiry> colunms) {
		if(this.colunms == null) {
			this.colunms = new ArrayList<ColunmEntiry>();
		}
		this.colunms.addAll(colunms);
	}
}
