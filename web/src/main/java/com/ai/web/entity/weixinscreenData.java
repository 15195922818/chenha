package com.ai.web.entity;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.ai.core.util.domain.entity.StringAndEqualsObject;

/**
 * Copyright (C) aisainfo
 * 
 * @className:ActiveData
 * @description:
 * @version:v1.0.0
 * @author:chenhongan
 * @date: 2015-3-22 9:08
 */
public class weixinscreenData extends StringAndEqualsObject implements Cloneable {
	/**
	 * @Fields serialVersionUID : 自动生成默认序列化ID
	 */
	private static final long serialVersionUID = 1L;

	public String personId;
	
	public String getPersonId()
	{
		return personId;
	}

	public void setPersonId(String personId)
	{
		this.personId = personId;
	}

	public String getNickName()
	{
		return nickName;
	}

	public void setNickName(String nickName)
	{
		this.nickName = nickName;
	}

	public String getHeadPicture()
	{
		return headPicture;
	}

	public void setHeadPicture(String headPicture)
	{
		this.headPicture = headPicture;
	}

	public String nickName;
	
	public String headPicture;
	
}
