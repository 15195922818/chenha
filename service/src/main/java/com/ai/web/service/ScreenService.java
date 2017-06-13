package com.ai.web.service;

import java.util.List;

import com.ai.web.entity.weixinscreenData;

public interface ScreenService {

	public List<weixinscreenData> getAllscreenName();
	
	public void insertNum(int num);
	
	public int selectNum();
}
