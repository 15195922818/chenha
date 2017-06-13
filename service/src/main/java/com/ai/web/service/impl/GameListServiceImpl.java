package com.ai.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.ai.web.dao.GameListDao;
import com.ai.web.entity.GameList;
import com.ai.web.entity.User;
import com.ai.web.service.GameListService;

@Service
public class GameListServiceImpl implements GameListService{
	
	@Resource
	private GameListDao gameListDao;

	@Override
	public List<GameList> getThisWeekScore(HttpSession session) {
		List<GameList> thisWeekList1 = gameListDao.getThisWeekScore();
		if(thisWeekList1.size()<11){
			for(GameList list : thisWeekList1){
				String jifenStr = list.getJifen();
				int length = jifenStr.length();
				List<String> newStr = new ArrayList<String>();
				for(int i=0;i<length;i++){
					newStr.add(jifenStr.substring(i, i+1));
				}
				list.setJifenList(newStr);
			}
			return thisWeekList1;
		}
		List<GameList> thisWeekList = thisWeekList1.subList(0, 10);
		for(GameList list : thisWeekList){
			String jifenStr = list.getJifen();
//			jifenStr.substring(jifenStr.length()-1);
			int length = jifenStr.length();
			List<String> newStr = new ArrayList<String>();
			for(int i=0;i<length;i++){
				newStr.add(jifenStr.substring(i, i+1));
			}
			list.setJifenList(newStr);
		}
		
	  boolean isExist=false;
	  User user=(User)session.getAttribute("user");
	  GameList myRecord=null;
	  for(GameList record : thisWeekList1){
		  if((record.getPersonId()).equals(user.getPersonId())){
			  myRecord=record;
              break;
		  }
	  }
	  if(myRecord!=null){
		 if(Integer.parseInt(myRecord.getRanking())>10){
			 String jifenStr = myRecord.getJifen();
			 int length = jifenStr.length();
			 List<String> newStr = new ArrayList<String>();
			 for(int i=0;i<length;i++){
					newStr.add(jifenStr.substring(i, i+1));
				}
			 
			 String rankingStr=myRecord.getRanking();
			 int length1 = rankingStr.length();
			 List<String> newStr1 = new ArrayList<String>();
			 for(int i=0;i<length1;i++){
					newStr1.add(rankingStr.substring(i, i+1));
				}
			 myRecord.setJifenList(newStr);
			 myRecord.setRankingList(newStr1);
			 thisWeekList.add(myRecord);
		 }
		
	  }
		return thisWeekList;
	}
	
	public List<GameList> getPreviousWeekScore(HttpSession session) {
		List<GameList> previousWeekList1 = gameListDao.getPreviousWeekScore();
		if(previousWeekList1.size()<11){
			for(GameList list : previousWeekList1){
				String jifenStr = list.getJifen();
				int length = jifenStr.length();
				List<String> newStr = new ArrayList<String>();
				for(int i=0;i<length;i++){
					newStr.add(jifenStr.substring(i, i+1));
				}
				list.setJifenList(newStr);
			}
			return previousWeekList1;
		}
		List<GameList> previousWeekList = previousWeekList1.subList(0, 10);
		for(GameList list : previousWeekList){
			String jifenStr = list.getJifen();
//			jifenStr.substring(jifenStr.length()-1);
			int length = jifenStr.length();
			List<String> newStr = new ArrayList<String>();
			for(int i=0;i<length;i++){
				newStr.add(jifenStr.substring(i, i+1));
			}
			list.setJifenList(newStr);
		}
		
	  boolean isExist=false;
	  User user=(User)session.getAttribute("user");
	  GameList myRecord=null;
	  for(GameList record : previousWeekList1){
		  if((record.getPersonId()).equals(user.getPersonId())){
			  myRecord=record;
              break;
		  }
	  }
	  if(myRecord!=null){
		 if(Integer.parseInt(myRecord.getRanking())>10){
			 String jifenStr = myRecord.getJifen();
			 int length = jifenStr.length();
			 List<String> newStr = new ArrayList<String>();
			 for(int i=0;i<length;i++){
					newStr.add(jifenStr.substring(i, i+1));
				}
			 
			 String rankingStr=myRecord.getRanking();
			 int length1 = rankingStr.length();
			 List<String> newStr1 = new ArrayList<String>();
			 for(int i=0;i<length1;i++){
					newStr1.add(rankingStr.substring(i, i+1));
				}
			 myRecord.setJifenList(newStr);
			 myRecord.setRankingList(newStr1);
			 previousWeekList.add(myRecord);
		 }
		
	  }
		return previousWeekList;
	}

	public void insert(GameList record) {
		gameListDao.insert(record);
	}

}
