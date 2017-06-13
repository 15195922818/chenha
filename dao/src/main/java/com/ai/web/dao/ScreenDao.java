package com.ai.web.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ai.web.entity.weixinscreenData;
import com.ai.web.mapper.WeixinScreenPicListMapper;

@Repository
public class ScreenDao {

	@Autowired
	private WeixinScreenPicListMapper weixinScreenPicListMapper;

	public List<weixinscreenData> getAllscreenName() {
		return weixinScreenPicListMapper.getAllscreenName();
	}
	
	public void insertNum(int num){
		Map map = new HashMap();
		map.put("num", num);
		weixinScreenPicListMapper.insertNum(map);
	}
	
	public int selectNum()
	{
		return weixinScreenPicListMapper.selectNum();
	}
}
