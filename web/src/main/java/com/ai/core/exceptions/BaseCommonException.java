package com.ai.core.exceptions;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author maxg3
 * 
 *         公共异常
 * 
 */
public class BaseCommonException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private static Map<ExceptionStatus, String> codeMaps = ExceptionType
			.getCodeMape();
	private static Map<ExceptionStatus, String> typeMaps = ExceptionType
			.getTypeMape();
	private static final Logger logger = LoggerFactory
			.getLogger(BaseCommonException.class);
	// 异常码
	private String exceptionCode;

	// 异常类型
	private String exceptionMessage;

	// 异常原因
	private String exceptionCause;

	public BaseCommonException(ExceptionStatus status, String cause, Exception e) {
		this.exceptionMessage = typeMaps.get(status);
		this.exceptionCause = cause;
		this.exceptionCode = codeMaps.get(status);
		logger.error(exceptionCode, e);
	}

	public BaseCommonException(ExceptionStatus status, String cause) {
		this.exceptionMessage = typeMaps.get(status);
		this.exceptionCause = cause;
		this.exceptionCode = codeMaps.get(status);
		logger.error(exceptionCode, cause);
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public String getExceptionCause() {
		return exceptionCause;
	}

	public void setExceptionCause(String exceptionCause) {
		this.exceptionCause = exceptionCause;
	}

	public String getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

}
