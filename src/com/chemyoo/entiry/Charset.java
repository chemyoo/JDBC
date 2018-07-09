package com.chemyoo.entiry;
/** 
 * @author 作者 : jianqing.liu
 * @version 创建时间：2018年7月9日 上午9:42:23 
 * @since 2018年7月9日 上午9:42:23 
 * @description 字符集枚举
 */
public enum Charset {
	/**
	 * 字符集编码：
	 * UTF-8
	 */
	UTF_8("utf-8"),
	/**
	 * 字符集编码：
	 * GBK
	 */
	GBK("gbk"),
	/**
	 * 字符集编码：
	 * ISO-8859-1
	 */
	ISO_8859_1("iso-8859-1"),
	/**
	 * 字符集编码：
	 * GB2312
	 */
	GB2312("gb2312");
	
	private String charset;
	
	Charset(String charset){
		this.charset = charset;
	}
	
	/**
	 * @return 字符集
	 */
	public String getCharset() {
		return this.charset;
	}
}
