package com.ai.web.mapper;

import java.util.List;
import java.util.Map;

import com.ai.web.entity.weixinscreenData;

public interface WeixinScreenPicListMapper {

	public List<weixinscreenData> getAllscreenName();
	
	public void insertNum(Map map);
	
	public int selectNum();

}
