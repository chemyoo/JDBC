package com.chemyoo.utils;

public class WebRoot
{
	private static String web_root = null;

	/**
	 * @return the web_root
	 */
	public static String getWeb_root()
	{
		return web_root;
	}

	/**
	 * @param webRoot the web_root to set
	 */
	public static void setWeb_root(String webRoot)
	{
		if(web_root == null)
			web_root = webRoot;
	}
}
