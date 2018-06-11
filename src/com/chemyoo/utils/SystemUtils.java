package com.chemyoo.utils;

import java.net.URL;
import java.util.Properties;

/**
 * @author 作者 : jianqing.liu
 * @version 创建时间：2018年5月25日 上午11:51:11
 * @since 2018年5月25日 上午11:51:11
 * @description 类说明
 */
public class SystemUtils {

	private SystemUtils() {
	}

	/**
	 * 获取系统所有变量属性
	 * 
	 * @return
	 */
	public static Properties getProperties() {
		return System.getProperties();
	}

	/**
	 * 换行符
	 * 
	 * @return System.lineSeparator();
	 */
	public static String getLineSeparator() {
		return System.getProperty("line.separator");
	}

	/**
	 * 盘符分隔符/路径分隔符
	 * 
	 * @return
	 */
	public static String getPathSeparator() {
		return System.getProperty("path.separator");
	}

	/**
	 * 文件路径分隔符
	 * 
	 * @return
	 */
	public static String getFileSeparator() {
		return System.getProperty("file.separator");
	}

	/**
	 * Java 运行时环境版本
	 * 
	 * @return
	 */
	public static String getJavaVersion() {
		return System.getProperty("java.version");
	}

	/**
	 * Java 运行时环境供应商
	 * 
	 * @return
	 */
	public static String getJavaVendor() {
		return System.getProperty("java.vendor");
	}

	/**
	 * Java 运行时环境供应商的URL
	 * 
	 * @return
	 */
	public static String getJavaVendorUrl() {
		return System.getProperty("java.vendo.url");
	}

	/**
	 * Java 安装目录
	 * 
	 * @return
	 */
	public static String getJavaHome() {
		return System.getProperty("java.home");
	}

	/**
	 * Java 虚拟机规范版本
	 * 
	 * @return
	 */
	public static String getJavaVmSpecificationVersion() {
		return System.getProperty("java.vm.specification.version");
	}

	/**
	 * Java 虚拟机规范版本
	 * 
	 * @return
	 */
	public static String getJavaVmSpecificationVendor() {
		return System.getProperty("java.vm.specification.vendor");
	}

	/**
	 * Java 虚拟机规范名称
	 * 
	 * @return
	 */
	public static String getJavaVmSpecificationName() {
		return System.getProperty("java.vm.specification.name");
	}

	/**
	 * Java 虚拟机实现版本
	 * 
	 * @return
	 */
	public static String getJavaVmVersion() {
		return System.getProperty("java.vm.version");
	}

	/**
	 * Java 虚拟机实现供应商
	 * 
	 * @return
	 */
	public static String getJavaVmVendor() {
		return System.getProperty("java.vm.vendor");
	}

	/**
	 * Java 虚拟机实现名称
	 * 
	 * @return
	 */
	public static String getJavaVmName() {
		return System.getProperty("java.vm.name");
	}

	/**
	 * Java 运行时环境规范版本
	 * 
	 * @return
	 */
	public static String getJavaSpecificationVersion() {
		return System.getProperty("java.specification.version");
	}

	/**
	 * Java 运行时环境规范供应商
	 * 
	 * @return
	 */
	public static String getJavaSpecificationVendor() {
		return System.getProperty("java.specification.vendor");
	}

	/**
	 * Java 运行时环境规范名称
	 * 
	 * @return
	 */
	public static String getJavaSpecificationName() {
		return System.getProperty("java.specification.name");
	}

	/**
	 * Java 类格式版本号
	 * 
	 * @return
	 */
	public static String getJavaClassVersion() {
		return System.getProperty("java.class.version");
	}

	/**
	 * Java 类路径
	 * 
	 * @return
	 */
	public static String getJavaClassPath() {
		return System.getProperty("java.class.path");
	}

	/**
	 * 加载库时搜索的路径列表
	 * 
	 * @return
	 */
	public static String getJavaLibraryPath() {
		return System.getProperty("java.library.path");
	}

	/**
	 * 默认的临时文件路径
	 * 
	 * @return
	 */
	public static String getJavaIoTmpdir() {
		return System.getProperty("java.io.tmpdir");
	}

	/**
	 * 要使用的 JIT 编译器的名称
	 * 
	 * @return
	 */
	public static String getJavaCompiler() {
		return System.getProperty("java.compiler");
	}

	/**
	 * 一个或多个扩展目录的路径
	 * 
	 * @return
	 */
	public static String getJavaExtDirs() {
		return System.getProperty("java.ext.dirs");
	}

	/**
	 * 操作系统的名称
	 * 
	 * @return
	 */
	public static String getOSName() {
		return System.getProperty("os.name");
	}

	/**
	 * 操作系统的架构
	 * 
	 * @return
	 */
	public static String getOSArch() {
		return System.getProperty("os.arch");
	}

	/**
	 * 操作系统的版本
	 * 
	 * @return
	 */
	public static String getOSVersion() {
		return System.getProperty("os.version");
	}

	/**
	 * 当前登入操作系统的用户名称
	 * 
	 * @return
	 */
	public static String getUserName() {
		return System.getProperty("user.name");
	}

	/**
	 * 用户的主目录
	 * 
	 * @return
	 */
	public static String getUserHome() {
		return System.getProperty("user.home");
	}

	/**
	 * 用户的当前工作目录
	 * 
	 * @return
	 */
	public static String getUserDir() {
		return System.getProperty("user.dir");
	}

	/**
	 * 获取当前工程类(.class)存放根路径
	 * 
	 * @return
	 */
	public static String getClassPath() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		/**
		 * 同上
		 * SystemUtils.class.getClassLoader().getResource("").getPath();
		 */
		URL resourceUrl = classLoader.getResource("/");
		if (resourceUrl == null) {
			resourceUrl = classLoader.getResource("");
		}
		return resourceUrl.getPath();
	}
	/**
	 * 获得tomcat安装路径
	 * @return
	 */
	public static String getTomcatHome() {
		return System.getProperty("catalina.home");
	}

}
