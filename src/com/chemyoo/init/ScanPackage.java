package com.chemyoo.init;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/** 
 * @author 作者 : jianqing.liu
 * @version 创建时间：2018年1月8日 上午10:45:46 
 * @since 2018年1月8日 上午10:45:46 
 * @description 类说明 
 */
public class ScanPackage extends HttpServlet {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3231708832390454562L;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String beanPath = config.getServletContext().getInitParameter("beanPackage");
		this.createSql(beanPath);
	}
	
	public void createSql(String beanPath){
		
	}

}
