package com.chemyoo.servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chemyoo.connect.pool.ConnectionPoolsManager;

/** 
 * @author Author : jianqing.liu
 * @version version : created time：2018年9月27日 下午6:31:43 
 * @since since from 2018年9月27日 下午6:31:43 to now.
 * @description class description
 */
@WebServlet(name = "get free Connection Size", urlPatterns = "/test/freeSize.do", displayName="query the free connection") 
public class FreeConnectSize extends BaseServlet {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4391194042684519652L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		try {
			super.process(req, resp, ConnectionPoolsManager.getInstanse().freeSize());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
