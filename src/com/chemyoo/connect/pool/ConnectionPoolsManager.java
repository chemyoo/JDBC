package com.chemyoo.connect.pool;

public class ConnectionPoolsManager {
    //全局唯一连接池
    /**连接池对象*/
    private static ConnectionPools instanse = null;
    /**获取连接池*/
    public static ConnectionPools getInstanse()
    {
        if(instanse==null)
        {
            CreateConnectionPools();
        }
        return instanse;
    }
    /**初始化连接池*/
    private synchronized static void CreateConnectionPools()
    {
        if(instanse == null)
            instanse = new ConnectionPools();
    }

}
