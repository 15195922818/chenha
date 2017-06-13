package com.ai.core.exceptions;

import java.util.HashMap;
import java.util.Map;

public abstract class ExceptionType {

	public static String DATABASE_TYPE = "数据库异常";
	public static String DATABASE_CODE = "21000";

	public static String INTERFACE_TYPE = "接口异常";
	public static String INTERFACE_CODE = "22000";

	public static String SYSTEM_TYPE = "系统异常";
	public static String SYSTEM_CODE = "23000";

	public static Map<ExceptionStatus, String> getCodeMape() {
		Map<ExceptionStatus, String> codeMaps = new HashMap<ExceptionStatus, String>();
		codeMaps.put(ExceptionStatus.DATABASE_STATUS, DATABASE_CODE);
		codeMaps.put(ExceptionStatus.INTERFACE_STATUS, INTERFACE_CODE);
		codeMaps.put(ExceptionStatus.SYSTEM_STATUS, SYSTEM_CODE);
		return codeMaps;
	}

	public static Map<ExceptionStatus, String> getTypeMape() {
		Map<ExceptionStatus, String> codeMaps = new HashMap<ExceptionStatus, String>();
		codeMaps.put(ExceptionStatus.DATABASE_STATUS, DATABASE_TYPE);
		codeMaps.put(ExceptionStatus.INTERFACE_STATUS, INTERFACE_TYPE);
		codeMaps.put(ExceptionStatus.SYSTEM_STATUS, SYSTEM_TYPE);
		return codeMaps;
	}

}
