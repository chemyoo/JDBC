package com.chemyoo.connect.pool;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class ConnectionPools implements DataSource{
	
	private static ConnectionPools pools;

    private static volatile Vector<ConnectionObject> connections = new Vector<ConnectionObject>();
    public static Properties DbProps;
    private static Integer maxActive;
    private static Integer maxIdle;
//    private static Integer minIdle;
    /**最大等待获取空闲连接的时间*/
    private static Integer maxWait;
    private static String url;
    private static String user;
    private static String password;
    private static Integer timerTask;

    private Logger logger = Logger.getLogger(getClass().getName());

    static {
        DbProps = ConnectionPoolsManager.DbProps;
        maxActive = Integer.parseInt(DbProps.getProperty("maxActive"));
        maxIdle = Integer.parseInt(DbProps.getProperty("maxIdle"));
        maxWait = Integer.parseInt(DbProps.getProperty("maxWait"));
        url = DbProps.getProperty("url");
        user = DbProps.getProperty("username");
        password = DbProps.getProperty("password");
        timerTask = Integer.parseInt(DbProps.getProperty("timerTask"));
    }

    public static ConnectionPools getPools()
    {
    	if(pools == null)
    	{
    		pools = new ConnectionPools();
    	}
    	return pools;
    }
    
    private ConnectionPools()
    {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int tooManyFree = freeSize() - maxIdle;
                if(tooManyFree > 0)
                {
                    release(tooManyFree);
                }
                if (size() == 0)
                {
                    try {
						newConnection(true);
					} catch (SQLException e) {
						e.printStackTrace();
					}
                }
            }
        }, 12000,(timerTask*1000*60));
    }
    public synchronized void addConnection(Connection connection)
    {
        ConnectionObject conObject = new ConnectionObject(connection,"connection"+connections.size());
        ConnectionPools.connections.add(conObject);
    }

    public synchronized void addConnection(Connection connection,boolean isFree)
    {
        ConnectionObject conObject = new ConnectionObject(connection,"connection"+connections.size());
        conObject.setFree(isFree);
        ConnectionPools.connections.add(conObject);
    }

    public synchronized int size(){
        return connections.size();
    }

    public synchronized int freeSize(){
        int count = 0;
        for(ConnectionObject connection :connections)
        {
            if (connection.isFree())
            {
                count++;
            }
        }
        return count;
    }

    public synchronized Vector<ConnectionObject> getConnections() throws SQLException {
        return ConnectionPools.connections;
    }

    @Override
    public synchronized Connection getConnection() throws SQLException {
        Connection con = null;
        for(ConnectionObject connection :connections)
        {
            if(connection.isFree())
            {
                con = connection.getConnection();
                if(!con.isClosed())
                {
                    connection.setFree(false);
                    break;
                }
                else
                {
                    ConnectionPools.connections.remove(connection);
                    con = null;
                }
            }
        }
        if(con == null)
        {
            int counts = connections.size();
            if(maxActive>counts)
            {
                con = this.newConnection(false);
            }
            else
            {
                con = this.waitToCreateNew(maxWait/6);
                if(con==null)
                    throw new SQLException("获取连接失败！");
            }
        }
        return con;
    }

    public synchronized Connection waitToCreateNew(long timeout) throws SQLException{
        Connection connection = null;
        if(timeout<10*1000)
            timeout = 10*1000;
        int counts = 0;
        try 
        {
           do
           {
               counts++;
               if(counts>=6)
                   break;
               wait(timeout);
           }
           while ((connection = getConnection())==null);

        } 
        catch (InterruptedException e) 
        {
            e.printStackTrace();
        }
        return connection;
    }

    private synchronized Connection newConnection(boolean isFree) throws SQLException{
        // String sql="select count(1) from hr.countries";
        Connection conn = null;
        try 
        {
            Class.forName(DbProps.getProperty("driverClassName"));
            conn = DriverManager.getConnection(url,user,password);
            if (conn != null)
            {
                this.addConnection(conn,isFree);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public synchronized void freeConnection(Connection connection)
    {
        if(connection!=null)
        {
            for (ConnectionObject connectionObj : connections)
            {
                if (connection.equals(connectionObj.getConnection()))
                {
                    connectionObj.freeConnection();
                    break;
                }
            }
        }
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
    	Connection conn = null;
        try 
        {
            Class.forName(DbProps.getProperty("driverClassName"));
            conn = DriverManager.getConnection(url,username,password);
            if (conn != null)
            {
            	if(connections.size()>=maxActive)
            		this.wait();
                this.addConnection(conn);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
			e.printStackTrace();
		}
        return conn;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return maxWait;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    public synchronized boolean release(ConnectionObject connectionObj)
    {
		boolean success = connections.remove(connectionObj);
    	if(success)
    		this.notify();
    	return success;
    }
    
    public synchronized void release(int counts) {
        Enumeration<ConnectionObject> allConnections = connections.elements();
        int closeCounts = 0;
        while (allConnections.hasMoreElements()) {
            ConnectionObject connectionObject = allConnections.nextElement();
            try {
                if(connectionObject.isFree())
                {
                    Connection connection = connectionObject.getConnection();
                    if(!connection.isClosed())
                        connectionObject.getConnection().close();
                    connections.removeElement(connectionObject);
                    closeCounts++;
                    if(closeCounts>=counts)
                        break;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                logger.info("关闭连接："+connectionObject.getConnectionName()+"失败！");
            }
        }
        this.notifyAll();
    }

    public synchronized void releaseAll() {
        Enumeration<ConnectionObject> allConnections = connections.elements();
        while (allConnections.hasMoreElements()) {
            ConnectionObject connectionObject = allConnections.nextElement();
            try {
                Connection connection = connectionObject.getConnection();
                if(!connection.isClosed())
                    connectionObject.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
                logger.info("关闭连接："+connectionObject.getConnectionName()+"失败！");
            }
        }
        connections.removeAllElements();
        this.notifyAll();
    }

    public synchronized <T> List<T> aliasToBean(Class<T> clazz, ResultSet resultSet) throws SQLException, IllegalAccessException, InstantiationException {
        //结果集的元素对象
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columns = rsmd.getColumnCount();
        List<T> list = new ArrayList<T>();
        Field[] fields = clazz.getDeclaredFields();
//        T mybean = bean.newInstance();
//        Field[] fields = getFields(bean);
//        for (Field field : fields) {
//            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), bean);
//            Method setter = pd.getWriteMethod();
//            setter.invoke(mybean, resultset.get(field.getName()));
//        }
        while(resultSet.next()){//对每一条记录进行操作
            T obj = clazz.newInstance();//构造业务对象实体
            //将每一个字段取出进行赋值
            for(int i = 1;i<=columns;i++){
                Object value = resultSet.getObject(i);
                //寻找该列对应的对象属性
                for(int j=0;j<fields.length;j++){
                    Field f = fields[j];
                    //如果匹配进行赋值
                    if(f.getName().equalsIgnoreCase(rsmd.getColumnLabel(i).replace("_","")))
                    {
                        boolean flag = f.isAccessible();
                        f.setAccessible(true);
                        f.set(obj, value);
                        f.setAccessible(flag);
//                        PropertyDescriptor pd = new PropertyDescriptor(f.getName(), clazz);
//                        Method setter = pd.getWriteMethod();
//                        setter.invoke(obj, resultSet.getObject(f.getName()));
                    }
                }
            }
            list.add(obj);
        }
        return  list;
    }

    @SuppressWarnings("unchecked")
    public synchronized <K,V> List<Map<K,V>> aliasToMap(ResultSet resultSet) throws SQLException {
        List<Map<K,V>> list = new ArrayList<Map<K,V>>();
        Map<K,V> row = null;
        if(resultSet != null) {
            while(resultSet.next()) {//对每一条记录进行操作
                row = new HashMap<K,V>();
                ResultSetMetaData metaDatas = resultSet.getMetaData();
                int columnCount = metaDatas.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    K key = (K)metaDatas.getColumnLabel(i).toLowerCase();
                    V value = (V) resultSet.getObject(i);
                    row.put(key, value);
                }
                list.add(row);
            }
        }
        return list;
    }

}
