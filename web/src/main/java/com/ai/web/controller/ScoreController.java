package com.ai.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import com.ai.web.service.GameListService;
import com.ai.web.service.QuestionSevice;
import com.test.testFileChannel;

@Controller
public class ScoreController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private GameListService gameListService;
	
	@Autowired
	private QuestionSevice questionSevice;
	
	@RequestMapping(value = "/new/getThisWeekList")
	private ModelAndView getThisWeekList(ModelMap modelMap,
	HttpSession session,HttpServletRequest request) throws ClientProtocolException, IOException
	{
		//	modelMap.addAttribute("scoreList", gameListService.getThisWeekScore(session));
		//HttpSession  sess = request.getSession();
		//sess.invalidate();
		testFileChannel tis = new testFileChannel();
		try {
			tis.copy();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView("rules", modelMap);
	}
	
	@RequestMapping(value = "/new/excelThisWeekList")
	private ModelAndView excelThisWeekList(ModelMap modelMap,
	HttpSession session,HttpServletRequest request) throws ClientProtocolException, IOException
	{
		HttpSession  sess = request.getSession();
		return new ModelAndView("", modelMap);
	}
	
	@RequestMapping(value = "/new/testInsertRedis")
	private ModelAndView testInsertRedis(ModelMap modelMap,
	HttpSession session,HttpServletRequest request) throws ClientProtocolException, IOException
	{
		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
		//Jedis Cluster will attempt to discover cluster nodes automatically
		//只需要配置集群中的一个结点，连接成功后，自动获取点集群中其他结点信息
		jedisClusterNodes.add(new HostAndPort("192.168.1.120", 7000));
		jedisClusterNodes.add(new HostAndPort("192.168.1.120", 7001));
		jedisClusterNodes.add(new HostAndPort("192.168.1.120", 7002));
		jedisClusterNodes.add(new HostAndPort("192.168.1.120", 7003));
		jedisClusterNodes.add(new HostAndPort("192.168.1.120", 7004));
		jedisClusterNodes.add(new HostAndPort("192.168.1.120", 7005));
		JedisCluster jc = new JedisCluster(jedisClusterNodes);
		jc.set("foo","1234");
		String value = jc.get("foo");
		System.out.println(value);
		return new ModelAndView("rules", modelMap);
	}
	
	@RequestMapping(value = "/new/testThread")
	private ModelAndView testThread(ModelMap modelMap,HttpSession session,HttpServletRequest request) throws ClientProtocolException, IOException
	{
		for(int i=0;i<8;i++){
			new Thread(new Runnable(){
				@Override
				public void run() {
					long time = System.currentTimeMillis();
					for(int i=0;i<125;i++){
						if(ping("http://www.baidu.com",0,5000)){
							System.out.println("已ping通过!");
						}
					}
					long time1 = System.currentTimeMillis();
					System.out.println(Thread.currentThread().getName()+"消耗时间为:"+(time1-time));
				}
			}).start();
		}
		return new ModelAndView("rules", modelMap);
	}
	
	public static boolean ping(String ipAddress, int pingTimes, int timeOut) {
		BufferedReader in = null;
		Runtime r = Runtime.getRuntime(); // 将要执行的ping命令,此命令是windows格式的命令
		String pingCommand = "ping " + ipAddress + " -n " + pingTimes + " -w "
				+ timeOut;
		try { // 执行命令并获取输出
			Process p = r.exec(pingCommand);
			if (p == null) {
				return false;
			}
			in = new BufferedReader(new InputStreamReader(p.getInputStream())); // 逐行检查输出,计算类似出现=23ms
																				// TTL=62字样的次数
			int connectedCount = 0;
			String line = null;
			while ((line = in.readLine()) != null) {
				connectedCount += getCheckResult(line);
			} // 如果出现类似=23ms TTL=62这样的字样,出现的次数=测试次数则返回真
			return connectedCount == pingTimes;
		} catch (Exception ex) {
			ex.printStackTrace(); // 出现异常则返回假
			return false;
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 若line含有=18ms TTL=16字样,说明已经ping通,返回1,否則返回0.
	private static int getCheckResult(String line) { // System.out.println("控制台输出的结果为:"+line);
		Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
			return 1;
		}
		return 0;
	}
}
