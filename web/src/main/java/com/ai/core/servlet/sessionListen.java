package com.ai.core.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;
import javax.servlet.http.HttpSessionListener;

public class sessionListen implements HttpSessionListener{

	@Override
	public void sessionCreated(HttpSessionEvent sessionEvent) {
		System.out.println("11");
		String userJobNumber="";
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		// TODO Auto-generated method stub
		System.out.println("destory");
	}
	
	public String getRemoteHost(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
    }
}
