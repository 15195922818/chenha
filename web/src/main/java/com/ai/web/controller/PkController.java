package com.ai.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ai.web.entity.User;

@Controller
public class PkController {
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping(value = "/new/pkscore1111")
	public String ScreenManager(ModelMap map,HttpServletRequest request) throws Exception {
		     String myScore="200";
		     String rivalScore = "114";
		     map.put("myScore", "200");
		    map.put("myAnswerCorrect", "3");
		    map.put("rivalScore", "114");
		    map.put("rivalAnswerCorrect", "6");
		    map.put("rivalNickName", "秋风");
		    
		    User user=new User();
			user.setPersonId("1607115463");
			user.setNickName("打豆豆");
			user.setHeadPicture("../static/userHeadPicture/1607115463.png");
		    request.getSession().setAttribute("user", user);
		    
		    List<String> rewardList=new ArrayList<String>();
		    String reward="";
		    if(Integer.parseInt(myScore)>Integer.parseInt(rivalScore)){
		    	reward=rivalScore;
		    	rewardList.add("add");
		    }else{
		    	reward=myScore;
		    	rewardList.add("reduce");
		    }
		    for(int i=0;i<reward.length();i++){
		    	rewardList.add(reward.substring(i,i+1));
	    	}
		    map.put("rewardList", rewardList);
		    
		    
		    int meScore=Integer.parseInt(myScore);
		    if(meScore<=100){
		    	map.put("myLevel", "安全新手");
		    }
		    if(meScore>100&&meScore<=200){
		    	map.put("myLevel", "安全能手");
		    }
		    if(meScore>200&&meScore<=300){
		    	map.put("myLevel", "安全高手");
		    }
		    if(meScore>300){
		    	map.put("myLevel", "安全霸主");
		    }
		    int rivalScore1=Integer.parseInt(rivalScore);
		    if(rivalScore1<=100){
		    	map.put("rivalLevel", "安全新手");
		    }
		    if(rivalScore1>100&&rivalScore1<=200){
		    	map.put("rivalLevel", "安全能手");
		    }
		    if(rivalScore1>200&&rivalScore1<=300){
		    	map.put("rivalLevel", "安全高手");
		    }
		    if(rivalScore1>300){
		    	map.put("rivalLevel", "安全霸主");
		    }
		    
		return "pkscore";
	}
}
