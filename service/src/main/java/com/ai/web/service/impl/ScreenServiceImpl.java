package com.ai.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.web.dao.ScreenDao;
import com.ai.web.entity.weixinscreenData;
import com.ai.web.service.ScreenService;

@Service
public class ScreenServiceImpl implements ScreenService {

	@Autowired
	private ScreenDao screenDao;

	@Override
	public List<weixinscreenData> getAllscreenName() {
		List<weixinscreenData> WeixinscreenData = screenDao.getAllscreenName();
		return WeixinscreenData;
	}
	
	public void insertNum(int num)
	{
		screenDao.insertNum(num);
	}
	
	public int selectNum()
	{
		return screenDao.selectNum();
	}
}
