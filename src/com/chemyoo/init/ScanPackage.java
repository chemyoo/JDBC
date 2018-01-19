package com.chemyoo.init;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.chemyoo.utils.ChemyooUtils;
import com.chemyoo.utils.ReadFileToStream;
import com.sun.org.apache.bcel.internal.util.ClassLoader;

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
		String beanPath = config.getInitParameter("beanPackage");
		this.createSql(beanPath);
	}
	
	public void createSql(String beanPath){
		String classPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
		System.err.println(classPath);
		File file = new File(classPath+beanPath.replace(".", ChemyooUtils.getFileSeparator()));
		File[] childrenFiles = file.listFiles();
		for(File f : childrenFiles)
		{
			ClassLoader.getSystemResource(f.getPath());
		}
	}

}
