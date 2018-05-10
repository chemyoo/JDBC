package com.chemyoo.utils;
/** 
 * @author 作者 : jianqing.liu
 * @version 创建时间：2018年5月10日 上午9:45:03 
 * @since 2018年5月10日 上午9:45:03 
 * @description 类说明 
 */

import org.apache.log4j.Logger;

public abstract class BaseLogger<M> {
	protected Logger log = Logger.getLogger(getClass().getGenericSuperclass().getClass());
}
