package com.chemyoo.connect.pool;

import java.sql.Connection;
import java.util.Date;

import com.chemyoo.utils.TimeUtils;

class ConnectionObject {

	private String connectionName;

	private Connection connection;

	private boolean isFree = true;

	private Date lastModifyTime;

	public ConnectionObject(Connection connection, String connectionName) {
		this.connection = connection;
		this.isFree = true;
		this.connectionName = connectionName;
		this.lastModifyTime = TimeUtils.getNow();
	}

	public Connection getConnection() {
		return connection;
	}

	public void freeConnection() {
		this.isFree = true;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public boolean isFree() {
		return isFree;
	}

	public void setFree(boolean free) {
		isFree = free;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public String getConnectionName() {
		return connectionName;
	}

	public void setConnectionName(String connectionName) {
		this.connectionName = connectionName;
	}
}
