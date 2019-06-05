package com.sgcc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)

/**
 * 
 * 
 * <p>
 * Title: Validate
 * </p>
 * 
 * <p>
 * Description: 数据校验类型
 * </p>
 * 
 * @author mengjinyuan
 * 
 * @date 2019年3月19日
 */
public @interface Validate {
	public enum Types {
		NOT_NULL, 
		DATE,
		EMAIL,
		CHINESE,
		ID_CARD, 
		INTERGER, 
		IP4,
		LATIN,
		MAX_LEN,
		NOT_EMPTY,
		NUMBER
	};

	public double min() default 0;

	public double max() default 0;

	public String message();

	public Types type();
}
