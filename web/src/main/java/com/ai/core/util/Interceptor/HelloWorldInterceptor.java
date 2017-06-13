package com.ai.core.util.Interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ai.core.util.utils.CheckNull;
import com.ai.web.controller.LoginController;
import com.ai.web.entity.Signature;
import com.ai.web.service.QuestionSevice;

public class HelloWorldInterceptor implements HandlerInterceptor  {
	
	@Autowired
	private QuestionSevice questionSevice;
	
	@Autowired
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Override  
	public boolean preHandle(HttpServletRequest request,  
	        HttpServletResponse response, Object handler) throws Exception {
		
		//log.debug("拦截成功");
		
	    return true;  
	}  
	  
	@Override  
	public void postHandle(HttpServletRequest request,  
	        HttpServletResponse response, Object handler,  
	        ModelAndView modelAndView) throws Exception
	{
		
		//分享代码
		/*Signature signature = new Signature().new SignatureG(request,(
				CheckNull.isNull(modelAndView)?new ModelAndView():modelAndView).getModelMap());
		
		String ticketLog = signature.getTicket();
		
		String accessTokenLog = signature.getAccessToken();
		
		SimpleDateFormat smd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		String dateStr = smd.format(new Date());
		
		Map<String,Object> logMap = new HashMap<String,Object>();
		
		logMap.put("ticketLog", ticketLog);
		
		logMap.put("accessTokenLog", accessTokenLog);
		
		logMap.put("dateStr", dateStr);
		
		questionSevice.insertLog(logMap);
		
	    System.out.println("Post-handle");
	    
	    log.debug("拦截成功");*/
	    
	}
	  
	@Override  
	public void afterCompletion(HttpServletRequest request,  
	        HttpServletResponse response, Object handler, Exception ex)  
	        throws Exception
	{
		
	  // log.debug("After completion handle");
	    
	}
}