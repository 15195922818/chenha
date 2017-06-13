package com.ai.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ai.core.constant.Constant;
import com.ai.web.entity.Auth;
import com.ai.web.entity.Auth.AuthG;

@Controller
public class AuthController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping(value = "/new/auth")
	public String Auth(ModelMap mv,HttpServletRequest request) throws ClientProtocolException, IOException{
		StringBuffer url = request.getRequestURL();
		String appid = Constant.SERVICE_APPID;
		String redirect_uri = Constant.CALL_URL;
		String response_type = Constant.CODE;
		String scope = Constant.SNSAPI_USERINFO;
		String state = Constant.STATE;
		String wechat_redirect = Constant.WECHAT_REDIRECT;
		String call_url = Constant.CALL_URL;
		AuthG authG = new Auth().new AuthG(appid,redirect_uri,response_type,scope,state,wechat_redirect,call_url);
		mv.put("isAuth", Constant.ISAUTH);
		mv.put("getUrl", authG.getGet_url());
		log.debug("getUrl======"+authG.getGet_url());
		return "auth";
	}
}
