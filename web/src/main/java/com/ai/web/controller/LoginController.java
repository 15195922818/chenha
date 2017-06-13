package com.ai.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
	
	private static final Logger log = LoggerFactory
			.getLogger(LoginController.class);
	
	@RequestMapping(value = { "/user/login" })
	public String login(Model model) {
		return "login";
	}
	
	@RequestMapping(value = { "/user/logout" })
	public void logout(HttpServletRequest request,HttpServletResponse response,Model model) {
		HttpSession session = request.getSession();
		if(session != null){
			session.invalidate();
		}
		
		try {
			String path= request.getContextPath();
			response.sendRedirect(path+"/user/login");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 登陆验证
	 * @param request
	 * @param model
	 * @return 登陆结果
	 */
	@RequestMapping(value = { "/user/login/submit" })
	@ResponseBody
	public Object loginSubmit(HttpServletRequest request,Model model,HttpSession session) {
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		session.setAttribute("username", userName);

		Map<String, String> map = new HashMap<String, String>();
		if(("superusr".equals(userName)) && ("admin".equals(password))){
			map.put("error", "0");
		} else {
			map.put("error", "2");
			map.put("msg", "用户名或密码不正确");
		}
		return map;
	}
}
