package com.chemyoo.annotations;
/** 
 * @author Author : jianqing.liu
 * @version version : created time：2018年9月28日 上午9:24:16 
 * @since since from 2018年9月28日 上午9:24:16 to now.
 * @description class description
 */
public @interface Id {
	String name() default "";
	
	int length() default 0;
	
	String dataType() default "VARCHAR(18)";
}
