package com.chemyoo.init;

import com.chemyoo.connect.pool.ConnectionPoolsManager;
import com.chemyoo.connect.pool.ConnectionPools;
import com.chemyoo.utils.LocalMac;
import com.chemyoo.utils.WebRoot;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.sql.*;
import java.util.*;

public class InitServlet extends HttpServlet{

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1630033657517767460L;
	private Logger logger = Logger.getLogger(this.getClass());

    public InitServlet(){}

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        String webAppRootKey = context.getInitParameter("webAppRootKey");
        String webRoot = context.getRealPath("/");
        WebRoot.setWeb_root(webAppRootKey);
        System.setProperty(webAppRootKey,webRoot);
        logger.info("参数初始化，将项目路径设置到系统变量中！");
        logger.info("设置-->"+webAppRootKey+" = "+webRoot);

        //Statement stm=null;
        Properties DbProps = ConnectionPoolsManager.DbProps;
        boolean isConnected = testConnectDataBaseAndKeepIsActive(DbProps);

        logger.info(DbProps.getProperty("url")+" 数据库连接"+(isConnected?"成功！" : "失败！"));
//        final CountDownLatch cdl = new CountDownLatch(35);
//        for(int i=0;i<35;i++)
//        {
//            Thread thread = new Thread(){
//                @Override
//                public void run() {
//                    ConnectionPools pools = ConnectionPoolsManager.getInstanse();
//                    try {
//                        Connection connection = pools.getConnection();
//                        TimeUnit.SECONDS.sleep(5);
//                        pools.freeConnection(connection);
////                        cdl.countDown();
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            };
//            thread.start();
//        }
//        try
//        {
//            cdl.await();
//        }
//        catch (InterruptedException e)
//        {
//            e.printStackTrace();
//        }
        //ConnectionPools pools = ConnectionPoolsManager.getInstanse();
        //pools.releaseAll();
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                ConnectionPools pools = ConnectionPoolsManager.getInstanse();
//                System.err.println("当前线程池连接数："+pools.size());
//                System.err.println("当前线程池活动连接数："+pools.freeSize());
//            }
//        },0,30*1000);

//        ConnectionPools pools = ConnectionPoolsManager.getInstanse();
//        try {
//            Connection connection = pools.getConnection();
//            Statement stm = connection.createStatement();
//            stm.executeUpdate("SELECT * FROM hr.SYS_USER");
//            ResultSet resultSet = stm.getResultSet();
//            List<Stores> s = pools.aliasToBean(Stores.class,resultSet);
//            List<Map<String,Object>> maps = pools.aliasToMap(resultSet);
//            for (Stores st:s) {
//                System.err.println(st.toString());
//            }
//            for(Map map:maps)
//            {
//                System.err.println(map.toString());
//            }
//            pools.freeConnection(connection);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        catch (InstantiationException e) {
//            e.printStackTrace();
//        }

    }

    @Override
    public void destroy() {
        super.destroy();
    }

    private boolean testConnectDataBaseAndKeepIsActive(Properties props)
    {
        //String driver="oracle.jdbc.driver.OracleDriver";
        String url = props.getProperty("url");
        String user = props.getProperty("username");
        String password = props.getProperty("password");
        // String sql="select count(1) from hr.countries";
        Connection conn = null;
        boolean isConnected = false;
        try {
            Class.forName(props.getProperty("driverClassName"));
            conn = DriverManager.getConnection(url,user,password);
            
            if (conn != null)
            {
                isConnected = true;
                ConnectionPools pools = ConnectionPoolsManager.getInstanse();
                pools.addConnection(conn);
            }
//            stm=conn.createStatement();
//            int number=stm.executeUpdate(sql);
//            ResultSet rs = stm.getResultSet();
//            while (rs.next())
//            {
//                logger.info("----->"+rs.getInt(1));
//                logger.info("----->"+rs.getObject(1).getClass());
//            }
//            stm.close();
//            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isConnected;
    }
}
