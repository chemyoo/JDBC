package com.chemyoo.connect.pool;

import java.util.Properties;

import com.chemyoo.utils.PropertiesUtil;

public class ConnectionPoolsManager {
	// 全局唯一连接池
	/** 连接池对象 */
	private static ConnectionPools instanse = null;

	public static Properties DbProps = null;
	
	private ConnectionPoolsManager(){}

	static {
		DbProps = PropertiesUtil.getProperties("databaseConfig.properties");
	}

	/** 获取连接池 */
	public static ConnectionPools getInstanse() {
		if (instanse == null) {
			createConnectionPools();
		}
		return instanse;
	}

	/** 初始化连接池 */
	private static synchronized void createConnectionPools() {
		if (instanse == null)
			instanse = ConnectionPools.getPools();
	}

}
