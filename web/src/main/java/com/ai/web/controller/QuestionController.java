package com.ai.web.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.core.constant.Constant;
import com.ai.core.util.utils.CheckNull;
import com.ai.core.util.utils.EmojiFiter;
import com.ai.core.util.utils.UUIDUtils;
import com.ai.web.entity.GameRecord;
import com.ai.web.entity.OpenId;
import com.ai.web.entity.PkQuestion;
import com.ai.web.entity.PkRecord;
import com.ai.web.entity.Question;
import com.ai.web.entity.RefreshToken;
import com.ai.web.entity.RivalResponse;
import com.ai.web.entity.ShowPicture;
import com.ai.web.entity.SnsapiUserinfo;
import com.ai.web.entity.User;
import com.ai.web.service.QuestionSevice;
import com.github.sd4324530.fastweixin.util.JSONUtil;

@Controller
public class QuestionController {
	    private static Map<String,PkRecord> pkMap = new HashMap<String,PkRecord>();
	    private static Map<String,List<PkQuestion>> pkQuestionMap = new HashMap<String,List<PkQuestion>>();
	    private static Map<String,List<String>> questionIdMap= new HashMap<String,List<String>>();
	    private static Map<String,String> validateMap = new HashMap<String,String>();
	@Autowired
	private QuestionSevice questionSevice;
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping(value = "/new/homePage")
	public String homePage(ModelMap mv,String token,HttpServletRequest request) throws ClientProtocolException, IOException {
		
		HttpSession session = request.getSession();
		
		String code = request.getParameter("code");
		
		log.debug("code123======"+code);
		
		OpenId openId = new OpenId();
		
		if(!CheckNull.isNull(code))
		{
			openId = new OpenId().new OpenIdG(Constant.SERVICE_APPID,Constant.SERVICE_SECRET,code,Constant.OPENID_GRANT_TYPE);
		}
		if("40029".equals(openId.getErrcode()) || "40029" == openId.getErrcode()){
			log.debug("err======="+openId.getErrcode());
			return "redirect:/new/auth";
		}
		/*Signature signature = new Signature().new SignatureG(request,mv);
		String ticketLog = signature.getTicket();
		String accessTokenLog = signature.getAccessToken();
		log.debug("ticketLoghomePage====="+ticketLog);
		log.debug("accessTokenLoghomePage======="+accessTokenLog);
		SimpleDateFormat smd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String dateStr = smd.format(new Date());
		Map<String,Object> logMap = new HashMap<String,Object>();
		logMap.put("ticketLog", ticketLog);
		logMap.put("accessTokenLog", accessTokenLog);
		logMap.put("dateStr", dateStr);
		logMap.put("openId", openId.getOpenid());
		questionSevice.insertLog(logMap);*/
		
		
		//授权回调函数
		//根据code获取openId等
		log.debug("nickname============="+session.getAttribute("nickname"));
		log.debug(String.valueOf("null".equals(String.valueOf(session.getAttribute("nickname"))) || "".equals(String.valueOf(session.getAttribute("nickname")))));
		if(CheckNull.isNull((session.getAttribute("user")))){
			/*String code = request.getParameter("code");*/
			log.debug("code=============="+code);
			/*OpenId openId = new OpenId().new OpenIdG(Constant.SERVICE_APPID,Constant.SERVICE_SECRET,code,Constant.OPENID_GRANT_TYPE);*/
			log.debug("openId============="+openId);
			String refreshToken = openId.getRefresh_token();
			log.debug("refreshToken123====="+refreshToken);
			log.debug("access_token123123====="+openId.getAccess_token());
			mv.put("code", code);
			mv.put("openId", openId.getOpenid());
			mv.put("unionid", openId.getUnionid());
			mv.put("access_token", openId.getAccess_token());
			mv.put("refresh_token", refreshToken);
			mv.put("all_url", openId.getAll_url());
			
			//刷新access_token（如果需要）
			RefreshToken rrefreshToken = new RefreshToken().new RefreshTokenG(Constant.SERVICE_APPID,Constant.REFRESHTOKEN_GRANT_TYPE,refreshToken);
			String refreshToken_access_token = rrefreshToken.getAccess_token();
			log.debug("access_token12341234====="+refreshToken_access_token);
			String refreshToken_openId = rrefreshToken.getOpenid();
			log.debug("refreshToken_openId============="+refreshToken_openId);
			mv.put("access_token", refreshToken_access_token);
			mv.put("refresh_token", rrefreshToken.getRefresh_token());
			mv.put("openid", refreshToken_openId);
			mv.put("scope", rrefreshToken.getScope());
			mv.put("refrehToekn_all_url", rrefreshToken.getAll_url());
			log.debug("refrehToekn_all_url============="+rrefreshToken.getAll_url());
			//拉取用户信息(需scope为 snsapi_userinfo)
			SnsapiUserinfo snsapiUserinfo = new SnsapiUserinfo().new SnsapiUserinfoG(refreshToken_access_token, refreshToken_openId, Constant.SNSAPIUSERINFO_LANG);
			String snsapiUserinfo_access_token = snsapiUserinfo.getAccess_token();
			String snsapiUserinfo_openId = snsapiUserinfo.getOpenid();
			mv.put("openid", snsapiUserinfo_openId);
			log.debug("openid="+snsapiUserinfo_openId);
			mv.put("nickname", snsapiUserinfo.getNickname());
			log.debug("nickname="+snsapiUserinfo.getNickname());
			mv.put("sex", snsapiUserinfo.getSex());
			mv.put("province", snsapiUserinfo.getProvince());
			mv.put("city", snsapiUserinfo.getCity());
			mv.put("country", snsapiUserinfo.getCountry());
			mv.put("headimgurl", snsapiUserinfo.getHeadimgurl().replaceAll("//", "/").replace("\\", ""));
			mv.put("privilege", snsapiUserinfo.getPrivilege());
			mv.put("unionid", snsapiUserinfo.getUnionid());
			mv.put("snsapiUserinfo_all_user)", snsapiUserinfo.getAll_url());
			log.debug("snsapiUserinfo_all_user="+snsapiUserinfo.getAll_url());
			session.setAttribute("nickname", snsapiUserinfo.getNickname());
			/*session.setAttribute("sex", snsapiUserinfo.getSex());
			session.setAttribute("headimgurl", snsapiUserinfo.getHeadimgurl().replaceAll("//", "/"));
			session.setAttribute("city", snsapiUserinfo.getCity());*/
					
			User user = new User();
			String nickname = snsapiUserinfo.getNickname();
			log.debug("nickname12="+nickname);
			user.setNickName(EmojiFiter.filterEmoji(nickname));
			log.debug("nickname34="+EmojiFiter.filterEmoji(nickname));
			user.setHeadPicture(snsapiUserinfo.getHeadimgurl().replaceAll("//", "/").replace("\\", ""));
			user.setPersonId(snsapiUserinfo_openId);
			
			session.setAttribute("user", user);
			String HeadPicture = user.getHeadPicture();
			log.debug("===========================nickname=====================");
			log.debug("nickname="+user.getNickName());
			log.debug("HeadPicture="+HeadPicture.replace("//", "/").replace("\\", ""));
			log.debug("PersonId="+user.getPersonId());
			log.debug("city="+new String(snsapiUserinfo.getCity().getBytes(),"UTF-8"));
			log.debug("province="+new String(snsapiUserinfo.getProvince().getBytes(),"UTF-8"));
			log.debug("===========================nickname=====================");
			String personId=snsapiUserinfo_openId;
			User existUser=questionSevice.getUserById(personId);
			log.debug("111111111111111111111111111111111111111111111");
			//log.debug("personId="+existUser.getPersonId()+"   nickname="+existUser.getNickName()+" headpic="+existUser.getHeadPicture());
			log.debug(String.valueOf(existUser==null));
			if(existUser==null){
				log.debug("insert user");
				questionSevice.insertUser(user);
				log.debug("insert user suc");
			}else{
				log.debug("update user");
				questionSevice.updateUser(user);
				log.debug("update user suc");
			}
			log.debug("2222222222222222222222222");
			User existUser1=questionSevice.getUserById(personId);
			log.debug("personId2="+existUser1.getPersonId()+"   nickname2="+existUser1.getNickName()+" headpic2="+existUser1.getHeadPicture());
			  return "homePage";
		}
		return "homePage";
		    
		/*if(session.getAttribute("user")!=null){
			return "homePage";
		}
     	
		User user=new User();
		if(token==null){
		    user=new User();
			user.setPersonId("1607115463");
			user.setNickName("打豆豆");
			user.setHeadPicture("../static/userHeadPicture/1607115463.png");
		    session.setAttribute("user", user);
				return "homePage";
		}else if(token.equals("aaa")){
			user=new User();
			user.setPersonId("1446171473");
			user.setNickName("东风破");
			user.setHeadPicture("../static/userHeadPicture/1607115463.png");
		}else if(token.equals("bbb")){
		    user=new User();
			user.setPersonId("413415346");
			user.setNickName("蛋疼");
			user.setHeadPicture("../static/userHeadPicture/1607115463.png");
		}else if(token.equals("ccc")){
			user=new User();
			user.setPersonId("1299120157");
			user.setNickName("秋风");
			user.setHeadPicture("../static/userHeadPicture/1607115463.png");
		}else{
			user=new User();
			user.setPersonId("1607115463");
			user.setNickName("打豆豆");
			user.setHeadPicture("../static/userHeadPicture/1607115463.png");
		}
		
	    session.setAttribute("user", user);
		return "homePage";*/
	}
	
	@RequestMapping(value = "/new/game")
	public String game(HttpSession session,ModelMap map,HttpServletRequest request) throws ClientProtocolException, IOException {
		
		if(CheckNull.isNull(String.valueOf(((User)session.getAttribute("user")).getNickName()))){
			return "redirect:/new/homePage";
		}
		map.put("nickName", ((User)session.getAttribute("user")).getNickName());
		session.removeAttribute("pictureIdList");
		session.setAttribute("isPermit", true);
		return "game";
	}
	
	@RequestMapping(value = "/new/isPermit")
	@ResponseBody
	public String isPermit(HttpSession session,ModelMap map,HttpServletRequest request) throws ClientProtocolException, IOException {
		
		if(CheckNull.isNull(session.getAttribute("isPermit"))){
			return "failure";
		}
		return "success";
	}
	
	@RequestMapping(value = "/new/getQuestionData", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getQuestionData(HttpServletRequest request,HttpSession session, ModelMap map)
	{
		List<Integer> pictureIdList=(List<Integer>) session.getAttribute("pictureIdList");
		if(pictureIdList==null){
			 pictureIdList=new ArrayList<Integer>();
		}
		Map showMap= new HashMap();
		List list = new ArrayList();
		int questionNumber = questionSevice.getQuestionNumber();
		Random random = new Random(); 
		int questionId=random.nextInt(questionNumber)+1;
		
		while(true){
			if(!pictureIdList.contains(questionId)){
				pictureIdList.add(questionId);
				session.setAttribute("pictureIdList", pictureIdList);
				break;
			}
		  questionId=random.nextInt(questionNumber)+1;
		}
	//	paramMap.put("question_id",""+randomId);
		Question question=questionSevice.getQuestionById(questionId+"");
		Map<Integer,String> pictureMap = new HashMap<Integer,String>();
		int correctIndex=random.nextInt(4)+1;
		ShowPicture correctPicture=new ShowPicture();
		correctPicture.setCorrect(true);
		correctPicture.setPictureUrl(question.getCorrectPicture());
		correctPicture.setShowIndex(correctIndex);
		list.add(correctPicture);
		ShowPicture errorPicture1=new ShowPicture();
		ShowPicture errorPicture2=new ShowPicture();
		ShowPicture errorPicture3=new ShowPicture();
		List<String> result=newGetRandomPicture(question);
		if(correctIndex==1){			
			errorPicture1.setShowIndex(2);
			errorPicture1.setCorrect(false);
			errorPicture1.setPictureUrl(result.get(0));
			
			errorPicture2.setShowIndex(3);
			errorPicture2.setCorrect(false);
			errorPicture2.setPictureUrl(result.get(1));
			
			errorPicture3.setShowIndex(4);
			errorPicture3.setCorrect(false);
			errorPicture3.setPictureUrl(result.get(2));		
		}
		if(correctIndex==2){
			errorPicture1.setShowIndex(1);
			errorPicture1.setCorrect(false);
			errorPicture1.setPictureUrl(result.get(0));
			
			errorPicture2.setShowIndex(3);
			errorPicture2.setCorrect(false);
			errorPicture2.setPictureUrl(result.get(1));
			
			errorPicture3.setShowIndex(4);
			errorPicture3.setCorrect(false);
			errorPicture3.setPictureUrl(result.get(2));	
		}
		if(correctIndex==3){
			errorPicture1.setShowIndex(1);
			errorPicture1.setCorrect(false);
			errorPicture1.setPictureUrl(result.get(0));
			
			errorPicture2.setShowIndex(2);
			errorPicture2.setCorrect(false);
			errorPicture2.setPictureUrl(result.get(1));
			
			errorPicture3.setShowIndex(4);
			errorPicture3.setCorrect(false);
			errorPicture3.setPictureUrl(result.get(2));	
		}
		if(correctIndex==4){
			errorPicture1.setShowIndex(1);
			errorPicture1.setCorrect(false);
			errorPicture1.setPictureUrl(result.get(0));
			
			errorPicture2.setShowIndex(2);
			errorPicture2.setCorrect(false);
			errorPicture2.setPictureUrl(result.get(1));
			
			errorPicture3.setShowIndex(3);
			errorPicture3.setCorrect(false);
			errorPicture3.setPictureUrl(result.get(2));	
		}
		list.add(errorPicture1);
		list.add(errorPicture2);
		list.add(errorPicture3);
		showMap.put("description", question.getQuestionDescription());
		showMap.put("picture", list);
		log.debug("===========================questionData=====================");
		log.debug(JSONUtil.toJson(showMap));
		return JSONUtil.toJson(showMap);
	}
	
	
	@RequestMapping(value = "/new/insertRecord", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String insertRecord(HttpSession session,ModelMap map,String answerCorrect,String score) {
		if(session.getAttribute("user")==null){
			return "redirect:/new/homePage";
		}
		if(Integer.parseInt(score)>210){
			score="0";
		}
		User user=(User) session.getAttribute("user");
			
		GameRecord gameRecord =new GameRecord();
		gameRecord.setGameId(UUIDUtils.getUUID());
		gameRecord.setPersonId(user.getPersonId());
		Date d=new Date();
		DateFormat fmt=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		gameRecord.setGameTime(fmt.format(d));
		gameRecord.setGameWeek(getWeekNumber()+"");
		gameRecord.setJifen(score);
		
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put("personId", user.getPersonId());
		paramMap.put("gameWeek", getWeekNumber()+"");
		try {
			session.setAttribute("gameScore", score);
			session.setAttribute("answerCorrect", answerCorrect);
			String maxOfWeek=questionSevice.getMaxOfWeek(paramMap);
			if(maxOfWeek==null){
				gameRecord.setIsWeekMax("1");	
				session.setAttribute("maxScore",score);
			}else{
				if(Integer.parseInt(score)>Integer.parseInt(maxOfWeek)){
					session.setAttribute("maxScore",score);
					gameRecord.setIsWeekMax("1");			
					questionSevice.updatePreviousMax(paramMap);
				}else{
					session.setAttribute("maxScore",maxOfWeek);
					gameRecord.setIsWeekMax("0");
				}
			}
			//添加有效标记
			gameRecord.setExtendField1("1");
			questionSevice.insertGameRecord(gameRecord);
		} catch (NumberFormatException e) {
			
		     return "failure";
		}
		   session.removeAttribute("isPermit");
		   return "success";
		
	}
	
	@RequestMapping(value = "/new/onePersonEnd")
	public String onPersonEnd(HttpSession session,ModelMap map,HttpServletRequest request) throws ClientProtocolException, IOException {
		if(session.getAttribute("user")==null){
			return "redirect:/new/homePage";
		}
		User user=(User) session.getAttribute("user");
		map.put("headPicture", user.getHeadPicture());
		map.put("nickName", user.getNickName());
	    map.put("maxScore", session.getAttribute("maxScore"));
	    map.put("answerCorrect", session.getAttribute("answerCorrect"));
	    map.put("gameScore", session.getAttribute("gameScore"));
	    int score=Integer.parseInt((String)session.getAttribute("gameScore"));
	    if(score<=100){
	    	map.put("levelName", "安全新手");
	    }
	    if(score>100&&score<=200){
	    	map.put("levelName", "安全能手");
	    }
	    if(score>200&&score<=300){
	    	map.put("levelName", "安全高手");
	    }
	    if(score>300){
	    	map.put("levelName", "安全霸主");
	    }
	      return "onePersonEnd";
	//	 return "onePersonEnd";
	}

	public static List<String> getRandomPicture(Question question){
		List<String> result=new ArrayList<String>();
		Random random = new Random(); 
		String[] ss={"1","2","3","4","5"};  //{"1","2","3","4","5","6","7","8","9"}
		List<Integer> list=new ArrayList<Integer>();
	    int a=random.nextInt(ss.length)+1;
	    list.add(a);
	    
	    while(true){
	    	int b=random.nextInt(ss.length)+1;
	    	if(list.size()<3){
	    		if(!list.contains(b)){
	    			list.add(b);
	    			continue;
	    		}
	    		continue;
	    	}
	       break;	    
	    }
	   for(int num:list){
		     if(num==1){
		    	 result.add(question.getErrorPicture1()); 
		  	   continue;
		     }
			 if(num==2){
				 result.add(question.getErrorPicture2()); 
			  	   continue;	   
		    }
			 if(num==3){
				 result.add(question.getErrorPicture3()); 
			  	   continue;
			 }
			 if(num==4){
				 result.add(question.getErrorPicture4()); 
			  	   continue;
			 }
			 if(num==5){
				 result.add(question.getErrorPicture5()); 
			  	   continue;
			 }
			/* if(num==6){
				 result.add(question.getErrorPicture6()); 
			  	   continue;
			 }
			 if(num==7){
				 result.add(question.getErrorPicture7()); 
			  	   continue;
			 }
			 if(num==8){
				 result.add(question.getErrorPicture8()); 
			  	   continue;
			 }
			 if(num==9){
				 result.add(question.getErrorPicture9()); 
			  	   continue;
			 }*/
	   }
	   return result;
	}
	
	public static int getWeekNumber(){
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
	    return calendar.get(Calendar.WEEK_OF_YEAR);	
	}
	
	public static List<String> newGetRandomPicture(Question question){
		int errorNum=0;
		List<String> results=new ArrayList<String>();
		Random random = new Random(); 
	
		while(errorNum<3){
			int randomIndex=random.nextInt(9)+1;
			if(randomIndex==1){
				String result=question.getErrorPicture1();
				if(result==null){continue;}
				result=result.trim();
				if(result.equals("")){
					continue;
				}else{
					if(!results.contains(result)){
						results.add(result);
						errorNum++;
					}	
					continue;
				}
			}
			
			if(randomIndex==2){
				String result=question.getErrorPicture2();
				if(result==null){continue;}
				result=result.trim();
				if(result.equals("")){
					continue;
				}else{
					if(!results.contains(result)){
						results.add(result);
						errorNum++;
					}	
					continue;
				}
			}
			
			if(randomIndex==3){
				String result=question.getErrorPicture3();
				if(result==null){continue;}
				result=result.trim();
				if(result.equals("")){
					continue;
				}else{
					if(!results.contains(result)){
						results.add(result);
						errorNum++;
					}			
					continue;
				}
			}
			
			if(randomIndex==4){
				String result=question.getErrorPicture4();
				if(result==null){continue;}
				result=result.trim();
				if(result.equals("")){
					continue;
				}else{
					if(!results.contains(result)){
						results.add(result);
						errorNum++;
					}			
					continue;
				}
			}
			
			if(randomIndex==5){
				String result=question.getErrorPicture5();
				if(result==null){continue;}
				result=result.trim();
				if(result.equals("")){
					continue;
				}else{
					if(!results.contains(result)){
						results.add(result);
						errorNum++;
					}			
					continue;
				}
			}
			
			if(randomIndex==6){
				String result=question.getErrorPicture6();
				if(result==null){continue;}
				result=result.trim();
				if(result.equals("")){
					continue;
				}else{
					if(!results.contains(result)){
						results.add(result);
						errorNum++;
					}			
					continue;
				}
			}
			
			if(randomIndex==7){
				String result=question.getErrorPicture7();
				if(result==null){continue;}
				result=result.trim();		
				if(result.equals("")){
					continue;
				}else{
					if(!results.contains(result)){
						results.add(result);
						errorNum++;
					}			
					continue;
				}
			}
			
			if(randomIndex==8){
				String result=question.getErrorPicture8();
				if(result==null){continue;}
				result=result.trim();
				if(result.equals("")){
					continue;
				}else{
					if(!results.contains(result)){
						results.add(result);
						errorNum++;
					}			
					continue;
				}
			}
			
			if(randomIndex==9){
				String result=question.getErrorPicture9();
				if(result==null){continue;}
				result=result.trim();
				if(result.equals("")){
					continue;
				}else{
					if(!results.contains(result)){
						results.add(result);
						errorNum++;
					}			
					continue;
				}
			}
		}
		
		
	       return results;
	}
	
	@RequestMapping(value = "/new/lookRules")
	public String lookRules(HttpSession session,ModelMap map,HttpServletRequest request) throws ClientProtocolException, IOException{	
        return "rules";
	}
	
	
	
	
	
	//-----------------------双人PK-----------------------//
			@RequestMapping(value = "/new/matches", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
			@ResponseBody
			public String matches(HttpSession session,ModelMap map,HttpServletRequest request) throws Exception {		
				synchronized (String.class) {						
					long time1 = System.currentTimeMillis();
					String myId = ((User) session.getAttribute("user")).getPersonId();
					String rivalId = "";
					boolean isContains = false;
					String pkId="";
					while(true){
						System.out.println("--------------------------------------------------------");
						/*//判断是否已经退出
						User me=questionSevice.getUserById(myId);
						if(me.getDoubleGame()==null){
							String pkId1=(String)session.getAttribute("pkId");
							//进入游戏后退出
							if(pkId1!=null){
								//两人均中途退出
								if(pkMap.get(pkId1).isLeave()){
									 session.removeAttribute("pkId");
									 pkMap.remove(pkId1);
								     pkQuestionMap.remove(pkId1);
								     questionIdMap.remove(pkId1);
								}
								//一人中途退出
								else{												
								   pkMap.get(pkId1).setLeave(true);
								   session.removeAttribute("pkId");
								}
							}
							return "homePage";
						}*/
						//判断是否超时
						long time2=System.currentTimeMillis();
						if((time2-time1)>30000){
							questionSevice.updateUserToOne(myId);
							return "failure";
						}
						//获取pk清单
						List<PkRecord> pkList = questionSevice.getPkList(myId);
						//判断是否包含用户
						if (pkList.size() != 0) {
							for (PkRecord p : pkList) {
								if (p.getPersonId1().equals(myId)) {
									rivalId=p.getPersonId2();
									session.setAttribute("pkId",p.getPkId());
									pkId=p.getPkId();
									isContains = true;
									break;
								}
								if (p.getPersonId2().equals(myId)) {
									rivalId=p.getPersonId1();
									session.setAttribute("pkId",p.getPkId());
									pkId=p.getPkId();
									isContains = true;
									break;
								}
							}
						}

						if (isContains) {
							//恢复用户状态(同时恢复)
						//	questionSevice.updateUserToOne(myId);
						//	questionSevice.updateUserToOne(rivalId);
							Map<String,String> idsMap=new HashMap<String,String>();
							idsMap.put("myId", myId);
							idsMap.put("rivalId",rivalId);
							questionSevice.updatePkToOne(idsMap);
							//更新pk准备情况
							questionSevice.updateReadyNum(pkId);
							return "success";
						}
						/*//判断是否已经退出
						User me=questionSevice.getUserById(myId);
						if(me.getDoubleGame()==null){
							String pkId1=(String)session.getAttribute("pkId");
							//进入游戏后退出
							if(pkId1!=null){
								//两人均中途退出
								if(pkMap.get(pkId1).isLeave()){
									 session.removeAttribute("pkId");
									 pkMap.remove(pkId1);
								     pkQuestionMap.remove(pkId1);
								     questionIdMap.remove(pkId1);
								}
								//一人中途退出
								else{												
								   pkMap.get(pkId1).setLeave(true);
								   session.removeAttribute("pkId");
								}
							}
							return "homePage";
						}*/
						//创建pkList对
						//获取pk用户
						List<User> pkUser=new ArrayList<User>();
						//和用户寻找对手退出共享同一把锁(防止用户退出时如果寻找到对手插入记录)
						synchronized (Integer.class) {
							//判断是否已经退出
							User me=questionSevice.getUserById(myId);
							if(me.getDoubleGame()==null){
								String pkId1=(String)session.getAttribute("pkId");
								//进入游戏后退出
								if(pkId1!=null){
									//两人均中途退出
									if(pkMap.get(pkId1).isLeave()){
										 session.removeAttribute("pkId");
										 pkMap.remove(pkId1);
									     pkQuestionMap.remove(pkId1);
									     questionIdMap.remove(pkId1);
									     //删除pk记录
									     questionSevice.deletePkRecoed(pkId1);
									}
									//一人中途退出
									else{		
										//标记离去,让对手不再等待
									   pkMap.get(pkId1).setLeave(true);
									   session.removeAttribute("pkId");
									}
								}
								return "homePage";
							}
							pkUser = questionSevice.getPkUser(myId);
						}
						
						if (pkUser.size()==0){
							Thread.currentThread().sleep(1000);
							continue;
						}
						Random random = new Random();
						int rivalNumber = random.nextInt(pkUser.size());				
						rivalId=pkUser.get(rivalNumber).getPersonId();
						Map<String, String> paramMap = new HashMap<String, String>();				
						paramMap.put("pkId",UUIDUtils.getUUID());
						paramMap.put("personId1", myId);
						paramMap.put("personId2", rivalId);
						questionSevice.insertPkRecord(paramMap);
						
					}
				}
				
			}
			
			@RequestMapping(value = "/new/lookRival")
			public String lookRival(HttpSession session,ModelMap map,HttpServletRequest request,String token) {	
				User user=(User)session.getAttribute("user");
				Map<String,String> paramMap=new HashMap<String,String>();
				paramMap.put("personId", user.getPersonId());
				paramMap.put("gameWeek", getWeekNumber()+"");			
		        String maxOfWeek=questionSevice.getMaxOfWeek(paramMap);
		        if(maxOfWeek==null){
		        	map.put("maxScore", 0);
		        }else{
		        	map.put("maxScore", maxOfWeek);
		        }
		        questionSevice.updateUserToTwo(user.getPersonId());
				 return "lookRival"; 	
			}
			
			
			@RequestMapping(value = "/new/isReady", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
			@ResponseBody
			public String isReady(HttpSession session,ModelMap map,HttpServletRequest request) throws Exception {	
				String pkId=(String)session.getAttribute("pkId");
				PkRecord pkRecord=questionSevice.getPkRecordById(pkId);
				long time1 = System.currentTimeMillis();				
				while(true){
					/*long time2 = System.currentTimeMillis();
					if((time2-time1)>100000){
						questionSevice.deletePkRecoed(pkId);
						session.removeAttribute("pkId");
						return "timeOut";
						}
					if(pkRecord==null){
						session.removeAttribute("pkId");
						return "failure";
					}*/
					if(pkRecord!=null){
						if(Integer.parseInt(pkRecord.getReadyNum())>=2){
							questionSevice.updatePkStatus(pkId);
							 pkMap.put(pkId, pkRecord);
							 //计入游戏的权利
								session.setAttribute("isPermit", true);
							 return "success"; 	
						}
					}								
					Thread.currentThread().sleep(1000);
					pkRecord=questionSevice.getPkRecordById(pkId);
				}	
		 		
			}
			 
			
			@RequestMapping(value = "/new/twoPersonGame")
			public String twoPersonGame(HttpSession session,ModelMap map,HttpServletRequest request) {	
				String pkId=(String)session.getAttribute("pkId");
				User me=(User)session.getAttribute("user");
				User rival = null;
				String myId=me.getPersonId();
				PkRecord pkRecord = pkMap.get(pkId);
				if(pkRecord.getPersonId1().equals(myId)){
					 pkMap.get(pkId).setPerson1Name(me.getNickName());
					rival = questionSevice.getUserById(pkRecord.getPersonId2());
					 pkMap.get(pkId).setPerson2Name(rival.getNickName());
				}else{
					 pkMap.get(pkId).setPerson2Name(me.getNickName());
					 rival = questionSevice.getUserById(pkRecord.getPersonId1());
					 pkMap.get(pkId).setPerson1Name(rival.getNickName());
				}
				 map.put("rival", rival);
				 return "twoPersonGame"; 	
			}
			
			@RequestMapping(value = "/new/getPkQuestionData", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
			@ResponseBody
			public String getPkQuestionData(HttpServletRequest request,HttpSession session, ModelMap map,String questionIndex) 
			{
				int questionIndex1 = Integer.parseInt(questionIndex);
				String pkId=(String)session.getAttribute("pkId");	
				synchronized (pkMap.get(pkId)) {
				//	PkQuestion pkQuestion = pkQuestionMap.get(pkId).get(questionIndex1);
					if ( pkQuestionMap.get(pkId) != null &&  pkQuestionMap.get(pkId).size()== questionIndex1) {
					//	if (Integer.parseInt(questionIndex) == pkQuestion.getQuestionIndex()) {
						PkQuestion pkQuestion = pkQuestionMap.get(pkId).get(questionIndex1-1);
							return JSONUtil.toJson(pkQuestion.getShowPictueMap());
					//	}
						/*if (Integer.parseInt(questionIndex) > pkQuestion
								.getQuestionIndex()) {
							//请求数据
							List<String> questionIdList = new ArrayList<String>();
							if (questionIdMap.get(pkId) != null) {
								questionIdList = questionIdMap.get(pkId);
							}
							int questionNumber = questionSevice.getQuestionNumber();
							Random random = new Random();
							int questionId = random.nextInt(questionNumber) + 1;
							while (true) {
								if (!questionIdList.contains(questionId + "")) {
									questionIdList.add(questionId + "");
									questionIdMap.put(pkId, questionIdList);
									break;
								}
								questionId = random.nextInt(questionNumber) + 1;
							}
							Question question = questionSevice
									.getQuestionById(questionId + "");
							Map<String, Object> showMap = new HashMap<String, Object>();
							List<ShowPicture> list = new ArrayList<ShowPicture>();
							int correctIndex = random.nextInt(4) + 1;
							ShowPicture correctPicture = new ShowPicture();
							correctPicture.setCorrect(true);
							correctPicture.setPictureUrl(question.getCorrectPicture());
							correctPicture.setShowIndex(correctIndex);
							list.add(correctPicture);
							ShowPicture errorPicture1 = new ShowPicture();
							ShowPicture errorPicture2 = new ShowPicture();
							ShowPicture errorPicture3 = new ShowPicture();
							List<String> result = getRandomPicture(question);
							if (correctIndex == 1) {
								errorPicture1.setShowIndex(2);
								errorPicture1.setCorrect(false);
								errorPicture1.setPictureUrl(result.get(0));

								errorPicture2.setShowIndex(3);
								errorPicture2.setCorrect(false);
								errorPicture2.setPictureUrl(result.get(1));

								errorPicture3.setShowIndex(4);
								errorPicture3.setCorrect(false);
								errorPicture3.setPictureUrl(result.get(2));
							}
							if (correctIndex == 2) {
								errorPicture1.setShowIndex(1);
								errorPicture1.setCorrect(false);
								errorPicture1.setPictureUrl(result.get(0));

								errorPicture2.setShowIndex(3);
								errorPicture2.setCorrect(false);
								errorPicture2.setPictureUrl(result.get(1));

								errorPicture3.setShowIndex(4);
								errorPicture3.setCorrect(false);
								errorPicture3.setPictureUrl(result.get(2));
							}
							if (correctIndex == 3) {
								errorPicture1.setShowIndex(1);
								errorPicture1.setCorrect(false);
								errorPicture1.setPictureUrl(result.get(0));

								errorPicture2.setShowIndex(2);
								errorPicture2.setCorrect(false);
								errorPicture2.setPictureUrl(result.get(1));

								errorPicture3.setShowIndex(4);
								errorPicture3.setCorrect(false);
								errorPicture3.setPictureUrl(result.get(2));
							}
							if (correctIndex == 4) {
								errorPicture1.setShowIndex(1);
								errorPicture1.setCorrect(false);
								errorPicture1.setPictureUrl(result.get(0));

								errorPicture2.setShowIndex(2);
								errorPicture2.setCorrect(false);
								errorPicture2.setPictureUrl(result.get(1));

								errorPicture3.setShowIndex(3);
								errorPicture3.setCorrect(false);
								errorPicture3.setPictureUrl(result.get(2));
							}
							list.add(errorPicture1);
							list.add(errorPicture2);
							list.add(errorPicture3);
							showMap.put("description",
									question.getQuestionDescription());
							showMap.put("picture", list);
							//更新数据
							pkQuestion.setShowPictueMap(showMap);
							pkQuestion.setQuestionIndex(questionIndex1);
							pkQuestion.setResponseNum(0);
							pkQuestionMap.get(pkId).add(pkQuestion);
						//	.put(pkId, pkQuestion);

							return JSONUtil.toJson(showMap);
						}*/
					} else {
						List<String> questionIdList = new ArrayList<String>();
						Map<String, Object> showMap = new HashMap<String, Object>();
						if (questionIdMap.get(pkId) != null) {
							questionIdList = questionIdMap.get(pkId);
						}
						int questionNumber = questionSevice.getQuestionNumber();
						Random random = new Random();
						int questionId = random.nextInt(questionNumber) + 1;
						while (true) {
							if (!questionIdList.contains(questionId + "")) {
								questionIdList.add(questionId + "");
								//更新问题Id
								questionIdMap.put(pkId, questionIdList);
								break;
							}
							questionId = random.nextInt(questionNumber) + 1;
						}
						Question question = questionSevice.getQuestionById(questionId + "");
						List<ShowPicture> list = new ArrayList<ShowPicture>();
						int correctIndex = random.nextInt(4) + 1;
						ShowPicture correctPicture = new ShowPicture();
						correctPicture.setCorrect(true);
						correctPicture.setPictureUrl(question.getCorrectPicture());
						correctPicture.setShowIndex(correctIndex);
						list.add(correctPicture);
						ShowPicture errorPicture1 = new ShowPicture();
						ShowPicture errorPicture2 = new ShowPicture();
						ShowPicture errorPicture3 = new ShowPicture();
						List<String> result = newGetRandomPicture(question);
						if (correctIndex == 1) {
							errorPicture1.setShowIndex(2);
							errorPicture1.setCorrect(false);
							errorPicture1.setPictureUrl(result.get(0));

							errorPicture2.setShowIndex(3);
							errorPicture2.setCorrect(false);
							errorPicture2.setPictureUrl(result.get(1));

							errorPicture3.setShowIndex(4);
							errorPicture3.setCorrect(false);
							errorPicture3.setPictureUrl(result.get(2));
						}
						if (correctIndex == 2) {
							errorPicture1.setShowIndex(1);
							errorPicture1.setCorrect(false);
							errorPicture1.setPictureUrl(result.get(0));

							errorPicture2.setShowIndex(3);
							errorPicture2.setCorrect(false);
							errorPicture2.setPictureUrl(result.get(1));

							errorPicture3.setShowIndex(4);
							errorPicture3.setCorrect(false);
							errorPicture3.setPictureUrl(result.get(2));
						}
						if (correctIndex == 3) {
							errorPicture1.setShowIndex(1);
							errorPicture1.setCorrect(false);
							errorPicture1.setPictureUrl(result.get(0));

							errorPicture2.setShowIndex(2);
							errorPicture2.setCorrect(false);
							errorPicture2.setPictureUrl(result.get(1));

							errorPicture3.setShowIndex(4);
							errorPicture3.setCorrect(false);
							errorPicture3.setPictureUrl(result.get(2));
						}
						if (correctIndex == 4) {
							errorPicture1.setShowIndex(1);
							errorPicture1.setCorrect(false);
							errorPicture1.setPictureUrl(result.get(0));

							errorPicture2.setShowIndex(2);
							errorPicture2.setCorrect(false);
							errorPicture2.setPictureUrl(result.get(1));

							errorPicture3.setShowIndex(3);
							errorPicture3.setCorrect(false);
							errorPicture3.setPictureUrl(result.get(2));
						}
						list.add(errorPicture1);
						list.add(errorPicture2);
						list.add(errorPicture3);
						showMap.put("description",
								question.getQuestionDescription());
						showMap.put("picture", list);
						//更新问题数据
						PkQuestion pkQuestion = new PkQuestion();
						pkQuestion.setShowPictueMap(showMap);
						pkQuestion.setQuestionIndex(Integer.parseInt(questionIndex));
						pkQuestion.setResponseNum(0);
						if(pkQuestionMap.get(pkId) != null){
							pkQuestionMap.get(pkId).add(pkQuestion);
						}
						else{
						List<PkQuestion> pkQuestionList = new ArrayList<PkQuestion>();
						pkQuestionMap.put(pkId, pkQuestionList);
						pkQuestionMap.get(pkId).add(pkQuestion);
						}
						return JSONUtil.toJson(showMap);
					}
				}	
			}
			
			/**
			 * 
			 * @param request
			 * @param session
			 * @param map
			 * @param questionIndex
			 * @return
			 * 恢复用户游戏状态
			 */
			@RequestMapping(value = "/new/updateUserToOne", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
			@ResponseBody
			public String updateUserToOne(HttpServletRequest request,HttpSession session, ModelMap map,String questionIndex) 
			{
		         User user=(User)session.getAttribute("user");
			     String myId=user.getPersonId();
			     try {
					synchronized (Integer.class) {
						questionSevice.updateUserToOne(myId);
					}
				} catch (Exception e) {
					
					return "failure";
				}
			     return "success";
			}
			
			@RequestMapping(value = "/new/updateResponseNumAndScore", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
			@ResponseBody
			public String updateResponseNumAndScore(HttpSession session, ModelMap map,String score,String answerCorrect,String questionIndex) throws Exception 
			{
				
				int questionNum1 = Integer.parseInt(questionIndex)-1;
			   String pkId=(String)session.getAttribute("pkId");
		       String myId=((User)session.getAttribute("user")).getPersonId();
		       PkRecord pkRecord=pkMap.get(pkId);
		       if((pkRecord.getPersonId1()).equals(myId)){
		    	   pkMap.get(pkId).setPerson1Score(score);
		    	   pkMap.get(pkId).setPerson1Correct(answerCorrect);
		       }else{
		    	   pkMap.get(pkId).setPerson2Score(score);
		    	   pkMap.get(pkId).setPerson2Correct(answerCorrect);
		       }
		      //更新回答次数
		       synchronized (pkQuestionMap.get(pkId).get(questionNum1)) {
		    	   pkQuestionMap.get(pkId).get(questionNum1).setResponseNum(pkQuestionMap.get(pkId).get(questionNum1).getResponseNum()+1);
		        }
		       long time1=System.currentTimeMillis();
		       while(true){  	  
		    	   if(pkMap.get(pkId).isLeave()){
		    		   pkQuestionMap.get(pkId).get(questionNum1).setResponseNum(pkQuestionMap.get(pkId).get(questionNum1).getResponseNum()+1);
		    	   }
		    	   if((pkQuestionMap.get(pkId).get(questionNum1).getResponseNum())>=2){
		    		   return "success";
		    	   }		    	  
		    	   long time2=System.currentTimeMillis();
		    	   if((time2-time1)>40000){
		    		   pkMap.get(pkId).setLeave(true);
		    	   }
		    	   Thread.currentThread().sleep(1000);
		       }
		       
			}
			
			
			@RequestMapping(value = "/new/getRivalCorrectNumAndScore", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
			@ResponseBody
			public String getRivalCorrectNumAndScore(HttpSession session, ModelMap map) 
			{
				String myId=(String)((User)session.getAttribute("user")).getPersonId();
				String pkId=(String)session.getAttribute("pkId");
				RivalResponse rivalReponse =new RivalResponse();
				if(pkMap.get(pkId).getPersonId1().endsWith(myId)){
					if(pkMap.get(pkId).getPerson2Correct()==null){
						pkMap.get(pkId).setPerson2Correct("0");
					}
					if(pkMap.get(pkId).getPerson2Score()==null){
						pkMap.get(pkId).setPerson2Score("0");
					}
					rivalReponse.setCorrectNum(Integer.parseInt(pkMap.get(pkId).getPerson2Correct()));
					rivalReponse.setScore(Integer.parseInt(pkMap.get(pkId).getPerson2Score()));
				}else{
					if(pkMap.get(pkId).getPerson1Correct()==null){
						pkMap.get(pkId).setPerson1Correct("0");
					}
					if(pkMap.get(pkId).getPerson1Score()==null){
						pkMap.get(pkId).setPerson1Score("0");
					}
					rivalReponse.setCorrectNum(Integer.parseInt(pkMap.get(pkId).getPerson1Correct()));
					rivalReponse.setScore(Integer.parseInt(pkMap.get(pkId).getPerson1Score()));
				}
			   return JSONUtil.toJson(rivalReponse);
		       
			}
			
			
			//双人pk结束页面
			@RequestMapping(value = "/new/pkscore")
			public String ScreenManager(ModelMap map,HttpSession session) throws Exception {
				//禁止返回进入游戏
				  session.removeAttribute("isPermit");
			    String myId=((User)session.getAttribute("user")).getPersonId();
			    //更新pk剩余次数
				questionSevice.updatePkTimes(myId);
			    String pkId =(String)session.getAttribute("pkId");
			    PkRecord pkRecord = pkMap.get(pkId);
			    log.debug("判断前person1Score:"+pkRecord.getPerson1Score()+"-----判断前person2Score:"+pkRecord.getPerson2Score());

			    if(Integer.parseInt(pkRecord.getPerson1Score())>420){
			    	pkRecord.setPerson1Correct("0");
			    }
          if(Integer.parseInt(pkRecord.getPerson2Score())>420){
          	pkRecord.setPerson2Correct("0");
			    }
			    
			    log.debug("判断后person1Score:"+pkRecord.getPerson1Score()+"-----判断后person2Score:"+pkRecord.getPerson2Score());

			    
			    //更新pk_list
			    questionSevice.updatePkRecord(pkRecord);
			    //清除共享数据
			    session.removeAttribute("pkId");
			    clearStaticSource(pkId);
			    String myScore="";
			    String myAnswerCorrect = "";
			    String rivalScore="";
			    String rivalAnswerCorrect = "";
			    String rivalId="";
			    if(myId.equals(pkRecord.getPersonId1())){
			    	myScore = pkRecord.getPerson1Score();
			    	myAnswerCorrect = pkRecord.getPerson1Correct();
			    	
			    	rivalScore = pkRecord.getPerson2Score();
			    	rivalAnswerCorrect = pkRecord.getPerson2Correct();
			    	rivalId = pkRecord.getPersonId2();
			    	
			    	
			    }else{
			    	myScore = pkRecord.getPerson2Score();
			    	myAnswerCorrect = pkRecord.getPerson2Correct();
			    	
			    	rivalScore = pkRecord.getPerson1Score();
			    	rivalAnswerCorrect = pkRecord.getPerson1Correct();
			    	rivalId = pkRecord.getPersonId1();
			    }
			   //对手信息
			    User rival =  questionSevice.getUserById(rivalId);
			    map.put("rivalNickName",rival.getNickName());
			    map.put("rivalHeadPicture",rival.getHeadPicture());
			    map.put("rivalScore", rivalScore);
			    map.put("rivalAnswerCorrect", rivalAnswerCorrect);
			    //自己信息
			    map.put("myScore", myScore);
			    map.put("myAnswerCorrect", myAnswerCorrect);
			    //奖励分数
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
			    
			    int myFianlScore=Integer.parseInt(myScore);
			    if(Integer.parseInt(myScore)>Integer.parseInt(rivalScore)){
			    	myFianlScore=Integer.parseInt(myScore)+Integer.parseInt(rivalScore);
			    }
			    if(Integer.parseInt(myScore)<Integer.parseInt(rivalScore)){
			    	myFianlScore=0;
			    }
			    GameRecord gameRecord =new GameRecord();
				gameRecord.setGameId(UUIDUtils.getUUID());
				gameRecord.setPersonId(myId);
				Date d=new Date();
				DateFormat fmt=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				gameRecord.setGameTime(fmt.format(d));
				gameRecord.setGameWeek(getWeekNumber()+"");
				gameRecord.setJifen(myFianlScore+"");
				gameRecord.setPkId(pkId);
				Map<String,String> paramMap=new HashMap<String,String>();
				paramMap.put("personId", myId);
				paramMap.put("gameWeek", getWeekNumber()+"");
				try {
					String maxOfWeek=questionSevice.getMaxOfWeek(paramMap);
					if(maxOfWeek==null){
						gameRecord.setIsWeekMax("1");	
					}else{
						if(myFianlScore>Integer.parseInt(maxOfWeek)){
							gameRecord.setIsWeekMax("1");			
							questionSevice.updatePreviousMax(paramMap);
						}else{
							gameRecord.setIsWeekMax("0");
						}
					}
					log.debug("插入的分数:"+gameRecord.getJifen());
					//添加有效标记
					gameRecord.setExtendField1("1");
					questionSevice.insertPkGameRecord(gameRecord);
				} catch (NumberFormatException e) {
					
				     return "failure";
				}
				    return "pkscore";
			}
			
			@RequestMapping(value = "/new/deletePkRecord", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
			@ResponseBody
			public String deletePkRecord(HttpServletRequest request,HttpSession session) 
			{
				String pkId=(String)session.getAttribute("pkId");
				questionSevice.deletePkRecoed(pkId);
		        return "success";
			}
			public static void clearStaticSource(String pkId){
				synchronized (questionIdMap.get(pkId)) {
					if (validateMap.get(pkId) != null) {
						pkMap.remove(pkId);
						pkQuestionMap.remove(pkId);
						questionIdMap.remove(pkId);
						validateMap.remove(pkId);
					} else {
						validateMap.put(pkId, "1");
					}
				}
			}
			@RequestMapping(value = "/new/share", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
			@ResponseBody
			public String share(ModelMap map,HttpSession session){
				String myId=((User)session.getAttribute("user")).getPersonId();
				questionSevice.updatePkTimesByShare(myId);
				return "success";
			}
	
	@RequestMapping(value = "/new/pkPermit", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String pkPermit(ModelMap map,HttpSession session){
		String myId=((User)session.getAttribute("user")).getPersonId();
		User user = questionSevice.getUserById(myId);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(Integer.parseInt(user.getPkTimes())>0){
			resultMap.put("pkPermit", true);
		}else{
			resultMap.put("pkPermit", false);
		}
	   return JSONUtil.toJson(resultMap);
	}
	}
