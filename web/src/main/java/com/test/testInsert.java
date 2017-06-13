package com.test;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.ai.web.entity.GameList;
import com.mysql.jdbc.Connection;

public class testInsert {
	
	
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
		System.out.println("开始处理...");
		Connection connect = new testInsert().connect();
		long time = System.currentTimeMillis();
		try {
			connect.setAutoCommit(false);
			PreparedStatement ps = null; 
			String sql = "insert into game_list (game_id, person_id, jifen, game_time, game_week, is_week_max) values (?,?,?,?,?,?)"; 
			ps = connect.prepareStatement(sql);
			List<GameList> list = new ArrayList<GameList>();
			for(int i=0;i<100000;i++)
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
			for(GameList gameList:list){
				String uuid=UUID.randomUUID().toString().replaceAll("-", "");
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
				long time1 = System.currentTimeMillis();
				System.out.println("执行共耗时:" + (time1 - time));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

