package com.ai.core.util.utils;

import java.util.UUID;

/**
 * @author shaoxy UUID生成工具类
 */
public abstract class UUIDUtils {

	/**
	 * 生成去除-的UUID
	 * 
	 * @return UUID
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
