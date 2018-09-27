package com.chemyoo.servlet;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chemyoo.entiry.Charset;

/** 
 * @author Author : jianqing.liu
 * @version version : created time：2018年9月27日 下午5:46:14 
 * @since since from 2018年9月27日 下午5:46:14 to now.
 * @description class description
 */
public abstract class BaseServlet extends HttpServlet {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5341216471522033720L;
	
	public void process(HttpServletRequest req, HttpServletResponse resp, Object res) throws IOException {
		String charset = Charset.GB2312.getCharset();
		req.setCharacterEncoding(charset);//加这句是为了适应POST请求乱码...
		resp.setCharacterEncoding(charset);    
		resp.setContentType("application/json; charset=" + charset);
		resp.getWriter().write(res.toString());
	}
	
}
