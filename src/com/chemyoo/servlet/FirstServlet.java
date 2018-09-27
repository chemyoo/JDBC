package com.chemyoo.servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chemyoo.bean.User;
import com.chemyoo.connect.pool.ConnectionPoolsManager;
import com.chemyoo.connect.pool.ConnectionService;

/** 
 * @author Author : jianqing.liu
 * @version version : created time：2018年9月27日 下午5:53:34 
 * @since since from 2018年9月27日 下午5:53:34 to now.
 * @description class description
 */
@WebServlet(name = "firstServlet", urlPatterns = "/test/firstServlet.do", displayName="Frist Servlet") 
public class FirstServlet extends BaseServlet {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -9137486292731669596L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		ConnectionService<User> service = new ConnectionService<User>(){};
		try {
			super.process(req, resp, service.findById("20180927153002799J"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
