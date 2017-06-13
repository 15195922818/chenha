package com.ai.core.exceptions;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class MyExceptionHandler extends SimpleMappingExceptionResolver {
	
	private static final Logger logger = LoggerFactory.getLogger(MyExceptionHandler.class);
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex) {
		
		
		logger.error(ex.getMessage(),ex);
		
		StringWriter sw=new StringWriter();
		PrintWriter pw=new PrintWriter(sw);
		ex.printStackTrace(pw);
  
		String viewName = determineViewName(ex, request);
		if (viewName != null) {
			Integer statusCode = determineStatusCode(request, viewName);
			if (statusCode != null) {
				applyStatusCodeIfPossible(request, response, statusCode);
			}
			String excep = sw.toString();
			excep = excep.substring(excep.indexOf(":")+1);
			
			request.setAttribute("exceptionCode", statusCode);
			request.setAttribute("exceptionMessage", ex.getMessage());// 异常信息
			request.setAttribute("exceptionCause", excep);// 异常详细信息
			
			return getModelAndView(viewName,ex, request);
		}
		else {
			return null;
		}
	}

}
