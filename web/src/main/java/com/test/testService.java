package com.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.web.dao.GameListDao;
import com.ai.web.dao.QuestionDao;
import com.ai.web.entity.GameRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:config/spring/applicationContext.xml")
public class testService {
	
	@Autowired
	private ApplicationContext applicationContext; 
	
	@Test
	public void to(){
		QuestionDao questionDao = (QuestionDao) applicationContext.getBean("questionDao");
		GameRecord g = new GameRecord();
		g.setGameId("134567");
		g.setPersonId("23456");
		g.setPkId("112");
		g.setJifen("111");
		g.setGameTime("20170221");
		g.setGameWeek("7");
		g.setIsWeekMax("1");
		questionDao.insertGameRecord(g);
	}
	
}
