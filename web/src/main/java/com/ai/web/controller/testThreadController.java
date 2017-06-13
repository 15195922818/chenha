package com.ai.web.controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.ai.web.entity.GameList;
import com.ai.web.service.GameListService;

class testThreadController extends Thread{
	
	private List<GameList> list;
	
	private int index;
	
	private DataSourceTransactionManager dataSourceTransactionManager;
	
	private TransactionStatus transactionStatus;
	
	private static WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
	
	static GameListService gameListService = (GameListService) wac.getBean("gameListServiceImpl");
	
	public testThreadController(Info3 info,DataSourceTransactionManager dataSourceTransactionManager,TransactionStatus transactionStatus){
		this.list = info.list;
		this.index = info.index;
		
	}
	
	public void run()
	{
		
		
		for(int i=(index)*50;i<((index+1)*50);i++){
			gameListService.insert(list.get(i));
		}
		
		//session.commit();
	}
}

class Info3{
	public List<GameList> list;
	
	public int index;
	
	public long time;
}