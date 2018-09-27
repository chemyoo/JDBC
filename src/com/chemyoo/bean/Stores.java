package com.chemyoo.bean;

import java.io.Serializable;
import java.util.Date;
import com.chemyoo.annotations.Field;
import com.chemyoo.annotations.NotField;
import com.chemyoo.annotations.Table;

/**
 * Stores entity. @author MyEclipse Persistence Tools
 */
@Table(name = "stores")
public class Stores extends Model implements Serializable {

	/**
	 * serialVersionUID
	 */
	@NotField
	private static final long serialVersionUID = 9133466602208515848L;
	@Field(name = "PKSTORE", primaryKey = true)
	private String pkStore;
	@Field(name = "STORE_ID")
	private String storeid;
	@Field(name = "STORENAME")
	private String storeName;
	@Field(name = "TS")
	private Date ts;
	@Field(name = "DR")
	private Integer dr;

	// Constructors

	/** default constructor */
	public Stores() {
	}

	/** minimal constructor */
	public Stores(String pkStore) {
		this.pkStore = pkStore;
	}

	/** full constructor */
	public Stores(String pkStore, String storeid, String storename, Date ts, Integer dr) {
		this.pkStore = pkStore;
		this.storeid = storeid;
		this.storeName = storename;
		this.ts = ts;
		this.dr = dr;
	}

	// Property accessors

	public String getPkStore() {
		return this.pkStore;
	}

	public void setPkStore(String pkStore) {
		this.pkStore = pkStore;
	}

	public String getStoreid() {
		return this.storeid;
	}

	public void setStoreid(String storeid) {
		this.storeid = storeid;
	}

	public String getStoreName() {
		return this.storeName;
	}

	public void setStoreName(String storename) {
		this.storeName = storename;
	}

	public Date getTs() {
		return this.ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}

	public Integer getDr() {
		return this.dr;
	}

	public void setDr(Integer dr) {
		this.dr = dr;
	}

}