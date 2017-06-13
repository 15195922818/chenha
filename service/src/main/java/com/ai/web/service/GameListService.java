package com.ai.web.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.ai.web.entity.GameList;

public interface GameListService {
	
	public List<GameList> getThisWeekScore(HttpSession session);
	
	public List<GameList> getPreviousWeekScore(HttpSession session);
	
	public void insert(GameList record);
}
