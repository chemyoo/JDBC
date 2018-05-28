/**
 * 
 */
package com.chemyoo.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 作者 : jianqing.liu
 * @version 创建时间：2017年12月29日 下午4:15:16
 * @since 2017年12月29日 下午4:15:16
 * @description 类说明
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ ElementType.FIELD })
public @interface Field {
	String name() default "";

	boolean primaryKey() default false;

	int length() default 255;
}
