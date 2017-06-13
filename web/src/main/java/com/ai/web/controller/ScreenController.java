package com.ai.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.ai.core.util.utils.CacheUtils;
import com.ai.web.extVector;
import com.ai.web.entity.GameList;
import com.ai.web.entity.Question;
import com.ai.web.entity.asd;
import com.ai.web.service.GameListService;
import com.ai.web.service.QuestionSevice;
import com.ai.web.service.ScreenService;
import com.ai.web.service.impl.GameListServiceImpl;
import com.test.People;
import com.test.ThreadDemo04;

@Controller
public class ScreenController {
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	static int b = 0;
	
	static db db = new db();
	
	static tSychr t = new tSychr();
	
	static extVector list = (extVector) new extVector();
	
	static int i = 0;
	
	private Lock lock = new ReentrantLock();
	
	@Autowired
	private GameListService gameListService;
	
	@Autowired
	private QuestionSevice questionSevice;
	
	private static WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
	
	@Autowired
	private asd ad;
	
	static{
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		list.add("e");
	}
	
	@Autowired
	private ScreenService screenService;
	@RequestMapping(value = "/new/screenmanager")
	public String ScreenManager(ModelMap mv,HttpServletRequest request) throws Exception {
		//根据appid，secret，grantType获取access_token和expireIn (grantType可不填，默认为client_credential微信基础功能)
		/*String appId = Constant.SERVICE_APPID;
		String secret = Constant.SERVICE_SECRET;
		AccessTokenG atg =  new AccessToken().new AccessTokenG(appId,secret);
		//根据access_token获取jsapi_ticket
		JsapiTicket jt = new JsapiTicket().new JsapiTicketG(atg.getAccessToken());
		//jsapiTicket
		String jsapiTicket =  jt.getTicket();
		//时间戳
		long timestampL = new Date().getTime();
		String timestamp = String.valueOf("123456");
		//noncestr随机字符串
		String nonceStr = UUIDUtils.getUUID();
		//url
		String url = request.getRequestURL()==null?"":request.getRequestURL().toString();
		log.debug("--------------------------------------url--------------------------------");
		log.debug("url="+url);
		log.debug("-------------------------------------------------------------------------");
		//拼接
		String signatureStr = "jsapi_ticket="+jsapiTicket+"&noncestr="+nonceStr+"&timestamp="+timestamp+"&url="+url;
		//获取签名
		String signature = DigestUtils.shaHex(signatureStr);
		mv.put("appId", appId);
		mv.put("timestamp", timestamp);
		mv.put("noncestr", nonceStr);
		mv.put("signature", signature);
		
		mv.put("secret", secret);
		mv.put("accessToken", atg.getAccessToken());
		mv.put("ticket", jsapiTicket);
		mv.put("url", url);
		mv.put("string1", signatureStr);*/
		
		String jc = ad.getJedis("aaa");
		System.out.println(jc);
		
		double a = Math.random()*1000;
		long sleepTime =  Math.round(a);
		Thread.sleep(sleepTime);
		/*t.a();*/
		tSychr tt = new tSychr();
		System.out.println("b="+b);
		if(b==3 || b==2 ||b==4 || b==8 || b==9){
			
		}else{
			db = new db();
			System.out.println("db");
		}
		tt.aa(db);
		return "screenmanager";
	}
	
	@RequestMapping(value = "/new/screentest")
	public String screentest(ModelMap mv,HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		/*synchronized(this){
			//List<GameList> list = gameListService.getPreviousWeekScore(session);
			int a = screenService.selectNum();
			screenService.insertNum(a+1);
			int a1 = screenService.selectNum();
			screenService.insertNum(a1+1);
			int a2 = screenService.selectNum();
			screenService.insertNum(a2+1);
			int a3 = screenService.selectNum();
			screenService.insertNum(a3+1);
			int a4 = screenService.selectNum();
			screenService.insertNum(a4+1);
			int a5 = screenService.selectNum();
			screenService.insertNum(a5+1);
			int a6 = screenService.selectNum();
			screenService.insertNum(a6+1);
			int a7 = screenService.selectNum();
			screenService.insertNum(a7+1);
			int a8 = screenService.selectNum();
			screenService.insertNum(a8+1);
			int a9 = screenService.selectNum();
			screenService.insertNum(a9+1);
			int a10 = screenService.selectNum();
			screenService.insertNum(a10+1);
		}*/
		System.out.println(CacheUtils.CacheMap.get("abs"));
		for(int i=0;i<1;i++)
		{
			screenService.insertNum(2);
			Thread.sleep(10);
		}
		//System.out.println("1111111111111111111");	
		/*response.setHeader("Cache-Control","no-cache"); 
		response.setHeader("Pragma","no-cache"); 
		response.setDateHeader("Expires",0);*/
		
		return "screenmanager";
	}
	
	@RequestMapping(value = "/new/test1")
	public String test1(ModelMap mv,HttpServletRequest request) throws Exception 
	{
		long time = System.currentTimeMillis();
		List<GameList> list = new ArrayList<GameList>();
		String uuid;
		
		for(int i=0;i<19000;i++)
		{
			GameList gameList = new GameList();
			uuid=UUID.randomUUID().toString().replaceAll("-", "");
			gameList.setGameId(uuid.toString());
			gameList.setPersonId("1");
			gameList.setNickName("name");
			gameList.setJifen("100");
			gameList.setGameTime("20161201");
			gameList.setGameWeek("12");
			gameList.setIsWeekMax("1");
			list.add(gameList);
		}
		
		for(GameList game:list)
		{
			gameListService.insert(game);
		}
			/*for(int i =0 ;i<20;i++)
		{
			Info3 info3 = new Info3();
			info3.list = list;
			info3.index = i;
			testThreadController threadDemo01 = new testThreadController(info3);
			Thread thread = new Thread(threadDemo01);
			thread.start();
		}*/
		long time1 = System.currentTimeMillis();
		System.out.println("执行时间为:"+(time1-time));
		return "screenmanager";
	}
	
	@RequestMapping(value = "/new/test2")
	public String test2(ModelMap mv,HttpServletRequest request) throws Exception 
	{
		long time = System.currentTimeMillis();
		List<GameList> list = new ArrayList<GameList>();
		String uuid;
		
		for(int i=0;i<2000;i++)
		{
			GameList gameList = new GameList();
			uuid=UUID.randomUUID().toString().replaceAll("-", "");
			gameList.setGameId(uuid.toString());
			gameList.setPersonId("1");
			gameList.setNickName("name");
			gameList.setJifen("100");
			gameList.setGameTime("20161201");
			gameList.setGameWeek("12");
			gameList.setIsWeekMax("1");
			list.add(gameList);
		}
		
		/*for(GameList game:list)
			gameListService.insert(game);*/
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		
		DataSourceTransactionManager txManager = (DataSourceTransactionManager) wac.getBean("transactionManager");
		TransactionStatus status = txManager.getTransaction(def);
		txManager.setDefaultTimeout(10000000);
		ExecutorService service = Executors.newFixedThreadPool(20);

		for(int i =0 ;i<20;i++)
		{
			Info3 info3 = new Info3();
			info3.list = list;
			info3.index = i;
			Thread thread = new Thread(new testThreadController(info3, txManager, status));
			service.execute(thread);
		}
		long time1 = System.currentTimeMillis();
		System.out.println("执行时间为:"+(time1-time));
		return "screenmanager";
	}
	
	@RequestMapping(value = "/new/testDeadThread")
	public String testDeadThread(ModelMap mv,HttpServletRequest request) throws Exception 
	{
		long time = System.currentTimeMillis();
		ExecutorService service = Executors.newFixedThreadPool(20);

		Thread thread01 = new Thread(new ThreadDemo04(true));
		Thread thread02 = new Thread(new ThreadDemo04(false));
		service.execute(thread01);
		service.execute(thread02);
		long time1 = System.currentTimeMillis();
		System.out.println("执行时间为:"+(time1-time));
		return "screenmanager";
	}
	
	@RequestMapping(value = "/new/getThreadGroup")
	public String getThreadGroup(ModelMap mv,HttpServletRequest request) throws Exception 
	{
		ThreadGroup group = Thread.currentThread().getThreadGroup();
		int count =0 ;
		Thread[] threads = new Thread[group.activeCount()];
        while(group != null) {
            threads = new Thread[(int)(group.activeCount() * 1.2)];
            count = group.enumerate(threads, true);
            group = group.getParent();
        }
        for(int i = 0; i < count; i++) {
        	System.out.println("线程号为:"+threads[i].getId()+",线程名称为:"+threads[i].getName()+",线程状态为:"+threads[i].getState());
        }
		return "screenmanager";
	}
	
	@RequestMapping(value = "/test1")
	public String get(){
		Long time = System.currentTimeMillis();
		Question q = questionSevice.getQuestionById("12");
		Question q1 = questionSevice.getQuestionById("13");
		Question q2 = questionSevice.getQuestionById("14");
		Long time1 = System.currentTimeMillis();
		System.out.println("执行时间为:"+(time1-time));
		return "screenmanager";
	}
}


class db{}

class tSychr{
	
	public synchronized void  a(){
		
		ScreenController.b += 1;
		System.out.println(ScreenController.b);
		System.out.println("=====================");
		
	}
	
	public void  aa(db db){
		ScreenController.b += 1;
		
		synchronized(db){
			ScreenController.list.remove(4);
			ScreenController.list.addElement("1");
			System.out.println("=====================");
		}
		
	}
	
	@RequestMapping(value = "/test")
	public String test(ModelMap mv,HttpServletRequest request) throws Exception 
	{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("config/spring/applicationContext-mvc.xml");
		People p = (People)ctx.getBean("cutesource");
		System.out.println(p.getId());
		System.out.println(p.getName());
		System.out.println(p.getAge());
		return "screenmanager";
	}
	
}
