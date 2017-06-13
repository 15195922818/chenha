package com.test;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ai.web.entity.GameList;
import com.mysql.jdbc.Connection;

public class testThreadInsert {
	public Connection connect(){
		Connection connect = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connect=(Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/wxks?useUnicode=true&characterEncoding=UTF-8","wxks","wxks");//SYSTEM@//localhost:1521/ORCL为数据库链接地址，Scott用户名，tiger为密码//
		}catch(Exception e){
			e.printStackTrace();
		}
		return connect;		
	}
	
	public static void main(String[] args) {
		List<GameList> list = new ArrayList<GameList>();
		for(int i=0;i<1000000;i++)
		{
			GameList gameList = new GameList();
			String uuid=UUID.randomUUID().toString().replaceAll("-", "");
			gameList.setGameId(uuid.toString());
			gameList.setPersonId("1");
			gameList.setJifen("100");
			gameList.setGameTime("20161201");
			gameList.setGameWeek("12");
			gameList.setIsWeekMax("1");
			list.add(gameList);
		}
		ExecutorService service = Executors.newCachedThreadPool();
		for(int i=0;i<10;i++)
		{
			Info01 info01 = new Info01();
			info01.list = list;
			info01.index = i;
			ThreadDemo05 threadDemo05 = new ThreadDemo05(info01);
			service.execute(threadDemo05);
		}
	}
}

class Info01{
	List<GameList> list;
	
	int index;
}


class ThreadDemo05 extends Thread{
	List<GameList> list;
	
	int index;
	
	public ThreadDemo05(Info01 info01){
		this.list = info01.list;
		this.index = info01.index;
	}
	
	public void run(){
		Connection connect = new testInsert().connect();
		try {
			connect.setAutoCommit(false);
			PreparedStatement ps = null; 
			String sql = "insert into game_list (game_id, person_id, jifen, game_time, game_week, is_week_max) values (?,?,?,?,?,?)"; 
			ps = connect.prepareStatement(sql);
			for(int i=(index)*100000;i<((index+1)*100000);i++){
				GameList gameList = list.get(i);
				ps.setString(1, gameList.getGameId());
				ps.setString(2, gameList.getPersonId());
				ps.setString(3, gameList.getJifen());
				ps.setString(4, gameList.getGameTime());
				ps.setString(5, gameList.getGameWeek());
				ps.setString(6, gameList.getIsWeekMax());
				int j = ps.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				connect.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}