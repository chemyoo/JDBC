package com.chemyoo.connect.pool;

import java.util.Properties;

import com.chemyoo.utils.PropertiesUtil;

public class ConnectionPoolsManager {
	// 全局唯一连接池
	/** 连接池对象 */
	private static ConnectionPools instanse = null;

	public static Properties DbProps = null;

	static {
		String webRoot = System.getProperty("web.root");
		DbProps = PropertiesUtil.getProperties(webRoot + "WEB-INF/classes/databaseConfig.properties");
	}

	/** 获取连接池 */
	public static ConnectionPools getInstanse() {
		if (instanse == null) {
			CreateConnectionPools();
		}
		return instanse;
	}

	/** 初始化连接池 */
	private synchronized static void CreateConnectionPools() {
		if (instanse == null)
			instanse = ConnectionPools.getPools();
	}

}
