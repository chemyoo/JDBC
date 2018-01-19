package com.chemyoo.bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.chemyoo.annotations.Field;
import com.chemyoo.annotations.NotField;
import com.chemyoo.annotations.Table;

/**
 * Stores entity. @author MyEclipse Persistence Tools
 */
@Table(name="stores")
public class Stores implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 9133466602208515848L;
	@Field(name = "PKSTORE")
	private String pkStore;
	@Field(name = "STORE_ID")
	private String storeid;
	@Field(name = "STORENAME")
	private String storename;
	@Field(name = "TS")
	private String ts;
	@Field(name = "DR")
	private Integer dr;
	@NotField
	private Set productInfos = new HashSet(0);
	@NotField
	private Set checkIns = new HashSet(0);
	@NotField 
	private Set checkOuts = new HashSet(0);

	// Constructors

	/** default constructor */
	public Stores() {
	}

	/** minimal constructor */
	public Stores(String pkStore) {
		this.pkStore = pkStore;
	}

	/** full constructor */
	public Stores(String pkStore, String storeid, String storename, String ts,
			Integer dr, Set productInfos, Set checkIns, Set checkOuts) {
		this.pkStore = pkStore;
		this.storeid = storeid;
		this.storename = storename;
		this.ts = ts;
		this.dr = dr;
		this.productInfos = productInfos;
		this.checkIns = checkIns;
		this.checkOuts = checkOuts;
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

	public String getStorename() {
		return this.storename;
	}

	public void setStorename(String storename) {
		this.storename = storename;
	}

	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public Integer getDr() {
		return this.dr;
	}

	public void setDr(Integer dr) {
		this.dr = dr;
	}

	public Set getProductInfos() {
		return this.productInfos;
	}

	public void setProductInfos(Set productInfos) {
		this.productInfos = productInfos;
	}

	public Set getCheckIns() {
		return this.checkIns;
	}

	public void setCheckIns(Set checkIns) {
		this.checkIns = checkIns;
	}

	public Set getCheckOuts() {
		return this.checkOuts;
	}

	public void setCheckOuts(Set checkOuts) {
		this.checkOuts = checkOuts;
	}

	@Override
	public String toString() {
		return "Stores{" +
				"pkStore='" + pkStore + '\'' +
				", storeid='" + storeid + '\'' +
				", storename='" + storename + '\'' +
				", ts='" + ts + '\'' +
				", dr=" + dr +
				", productInfos=" + productInfos +
				", checkIns=" + checkIns +
				", checkOuts=" + checkOuts +
				'}';
	}
}