package com.ai.web.entity;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;

import com.ai.core.constant.Constant;
import com.ai.core.util.domain.entity.StringAndEqualsObject;
import com.ai.core.util.utils.CheckNull;
import com.ai.core.util.utils.UUIDUtils;
import com.ai.web.entity.AccessToken.AccessTokenG;


/**
 * @author chenhognan
 * @ClassName: AccessToken
 * @Description: 根据appid，secret，grant_type获取access_token和expires_in
 */
public class Signature extends StringAndEqualsObject {
	
	private static final Logger log = LoggerFactory.getLogger(Signature.class);
	
	private static long oldTime;
	
	//缓存acessTaken
	private static String acessTaken;
	
	//缓存oldTicket
	private static String oldTicket;

	private static final long serialVersionUID = 1L;

	private String appid;
	
	private String ticket;
	
	private String timestamp;
	
	private String nonceStr;
	
	private String signature;
	
	public String getAccessToken(){
		return acessTaken;
	}
	
	public String getAppid()
	{
		return appid;
	}


	public void setAppid(String appid)
	{
		this.appid = appid;
	}

	public String getTicket()
	{
		return ticket;
	}


	public void setTicket(String ticket)
	{
		this.ticket = ticket;
	}


	public String getTimestamp()
	{
		return timestamp;
	}


	public void setTimestamp(String timestamp)
	{
		this.timestamp = timestamp;
	}


	public String getNonceStr()
	{
		return nonceStr;
	}


	public void setNonceStr(String nonceStr)
	{
		this.nonceStr = nonceStr;
	}


	public String getUrl()
	{
		return url;
	}


	public void setUrl(String url)
	{
		this.url = url;
	}


	public String getSignature()
	{
		return signature;
	}


	public void setSignature(String signature)
	{
		this.signature = signature;
	}


	private String url;
	
	
	@SuppressWarnings("serial")
	public class SignatureG extends Signature{
		
		/**
		 * 
		 * @param grantType
		 * @param appId
		 * @param secret
		 * @throws ClientProtocolException
		 * @throws IOException
		 */
		public SignatureG(HttpServletRequest request,ModelMap map) throws ClientProtocolException, IOException{
			this.getP(request);
			this.getSignatureG();
			this.setMap(map);
		}
		
		public void getP(HttpServletRequest request) throws ClientProtocolException, IOException{
			//根据appid，secret，grantType获取access_token和expireIn (grantType可不填，默认为client_credential微信基础功能)
			String appId = Constant.SERVICE_APPID;
			String access_token = "";
			//缓存access_token
			long time = 0;
			long timeSpace = 0;
			if(oldTime == 0){
				oldTime = System.currentTimeMillis();
				AccessTokenG atg =  new AccessToken().new AccessTokenG(appId,Constant.SERVICE_SECRET);
				acessTaken = atg.getAccessToken();
				JsapiTicket jt = new JsapiTicket().new JsapiTicketG(acessTaken);    //根据access_token获取jsapi_ticket
				oldTicket = jt.getTicket();
				log.debug("acessTaken==="+acessTaken);
			}
			else
			{
				time = System.currentTimeMillis();
				timeSpace = time - oldTime;
				log.debug("timeSpace==="+timeSpace);
			}
			//访问间隔时间
			long minute = timeSpace/1000/60;
			log.debug("minute==="+minute);
			log.debug("minute>=Constant.ACCESS_TOKEN_INTERVAL===" + (minute>=Constant.ACCESS_TOKEN_INTERVAL));
			if(minute>=Constant.ACCESS_TOKEN_INTERVAL){
				AccessTokenG atg =  new AccessToken().new AccessTokenG(appId,Constant.SERVICE_SECRET);
				acessTaken = atg.getAccessToken();
				JsapiTicket jt = new JsapiTicket().new JsapiTicketG(acessTaken);    //根据access_token获取jsapi_ticket
				oldTicket = jt.getTicket();
				oldTime = System.currentTimeMillis();
			}
			log.debug("access_token===="+   acessTaken);
			setAppid(appId);
			setTicket(oldTicket);
			log.debug("Ticket==="+oldTicket);
			setNonceStr(UUIDUtils.getUUID());
			setTimestamp(String.valueOf(Long.toString(new Date().getTime()/1000)));
			String queryString = request.getQueryString();
			String url = request.getRequestURL()==null?"":request.getRequestURL().toString();
			setUrl(CheckNull.isNull(queryString)?url:url+"?"+queryString);
			log.debug("queryString==="+request.getQueryString());
		}
		
		private void getSignatureG() throws ClientProtocolException, IOException{
			String signatureStr = "jsapi_ticket="+super.getTicket()+"&noncestr="+super.getNonceStr()+"&timestamp="+super.getTimestamp()+"&url="+super.getUrl();
			String signature = DigestUtils.shaHex(signatureStr);
			setSignature(signature);
			log.debug("--------------------------------------url--------------------------------");
			log.debug("jsapi_ticket="+getTicket());
			log.debug("url="+getUrl());
			log.debug("nonceStr="+getNonceStr());
			log.debug("timestamp="+getTimestamp());
			log.debug("signature="+getSignature());
			log.debug("-------------------------------------------------------------------------");
		};
		
		private void setMap(ModelMap map){
			map.put("appId", this.getAppid());
			map.put("timestamp", this.getTimestamp());
			map.put("noncestr", this.getNonceStr());
			map.put("signature", this.getSignature());
		}
	}
}
