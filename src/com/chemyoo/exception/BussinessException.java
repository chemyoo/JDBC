package com.chemyoo.exception;
/** 
 * @author Author : jianqing.liu
 * @version version : created time：2019年1月14日 上午11:18:46 
 * @since since from 2019年1月14日 上午11:18:46 to now.
 * @description class description
 */
public class BussinessException extends RuntimeException{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3060831559508741655L;

	public BussinessException() {
		super("Bussiness exception");
	}
	
	public BussinessException(String message) {
		super(message);
	}
	
	public BussinessException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public BussinessException(Throwable cause) {
		super(cause);
	}
	
	public static void main(String[] args) {
		throw new BussinessException(new Exception("你猜会发生什么"));
	}
}
