package com.ai.web.entity;

import com.ai.core.util.domain.entity.StringAndEqualsObject;


/**
 * @author chenhognan
 * @ClassName: MxConfig
 * @Description: appid，timestamp，nonceStr,signature
 */
public class MxConfig extends StringAndEqualsObject {

	private static final long serialVersionUID = 1L;
	
	private String appId;
	
	private String timestamp;
	
	private String nonceStr;
	
	private String signature;
	
	private MxConfig(){}
	
	public String getAppId()
	{
		return appId;
	}

	private void setAppId(String appId)
	{
		this.appId = appId;
	}

	public String getTimestamp()
	{
		return timestamp;
	}

	private void setTimestamp(String timestamp)
	{
		this.timestamp = timestamp;
	}

	public String getNonceStr()
	{
		return nonceStr;
	}

	private void setNonceStr(String nonceStr)
	{
		this.nonceStr = nonceStr;
	}

	public String getSignature()
	{
		return signature;
	}

	private void setSignature(String signature)
	{
		this.signature = signature;
	}
	
	public static class Create{
		
		private MxConfig mxConfig;
		
		public Create(){
			mxConfig = new MxConfig();
		}
		
		public Create setAppId(String appId){
			mxConfig.setAppId(appId);
			return this;
		}
		
		public Create setTimestamp(String timestamp){
			mxConfig.setTimestamp(timestamp);
			return this;
		}
		
		public Create setNonceStr(String nonceStr){
			mxConfig.setNonceStr(nonceStr);
			return this;
		}
		public Create setSignature(String signature){
			mxConfig.setSignature(signature);
			return this;
		}
		
		public MxConfig create(){
			return mxConfig;
		}
	}
	
}
