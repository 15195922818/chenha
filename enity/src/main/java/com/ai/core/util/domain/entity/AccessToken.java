package com.ai.core.util.domain.entity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.ai.core.constant.Constant;
import com.ai.core.util.utils.CheckNull;
import com.ai.core.util.utils.HttpClientUtil;


/**
 * @author chenhognan
 * @ClassName: AccessToken
 * @Description: 根据appid，secret，grant_type获取access_token和expires_in
 */
public class AccessToken extends StringAndEqualsObject {

	private static final long serialVersionUID = 1L;

	private String appid;
	
	private String secret;
	
	private String grantType;
	
	private String accessToken;
	
	private String expiresIn;
	
	private static final String url = Constant.ACCESSTOKEN_URL;
	
	public String getAccessToken()
	{
		return accessToken;
	}

	public void setAccessToken(String accessToken)
	{
		this.accessToken = accessToken;
	}

	public String getExpiresIn()
	{
		return expiresIn;
	}

	public void setExpiresIn(String expiresIn)
	{
		this.expiresIn = expiresIn;
	}

	public String getAppid()
	{
		return appid;
	}

	public void setAppid(String appid)
	{
		this.appid = appid;
	}

	public String getSecret()
	{
		return secret;
	}

	public void setSecret(String secret)
	{
		this.secret = secret;
	}

	public String getGrantType()
	{
		return grantType;
	}

	public void setGrantType(String grantType)
	{
		this.grantType = grantType;
	}
	
	@SuppressWarnings("serial")
	public class AccessTokenG extends AccessToken{
		/**
		 * 
		 * @param grantType
		 * @param appId
		 * @param secret
		 * @throws ClientProtocolException
		 * @throws IOException
		 */
		public AccessTokenG(String appId,String secret,String grantType) throws ClientProtocolException, IOException{
			setAppid(appId);
			setSecret(secret);
			setGrantType(grantType);
			this.getAccessTokenG();
		}
		
		/**
		 * 
		 * @param appId
		 * @param secret
		 * @throws ClientProtocolException
		 * @throws IOException
		 */
		public AccessTokenG(String appId,String secret) throws ClientProtocolException, IOException{
			setAppid(appId);
			setSecret(secret);
			setGrantType("client_credential");
			this.getAccessTokenG();
		}
		
		private void getAccessTokenG() throws ClientProtocolException, IOException{
			String getUrl = url+"?grant_type="+super.getGrantType()+"&appid="+super.getAppid()+"&secret="+super.getSecret();
	        String accessStr = HttpClientUtil.httpGet(getUrl);
			if (accessStr != null) {
	            // 打印响应内容
	            Map<String,Object> map = StringToMap(accessStr);
	            String accessToken = map.get("access_token")==null?"":(String) map.get("access_token");
	            String expiresIn = map.get("expires_in")==null?"":(String) map.get("expires_in");
	            setAccessToken(accessToken);
	            setExpiresIn(expiresIn);
	            System.out.println(accessToken);
	        }
	        System.out.println("------------------------------------");  
		};
		
		private Map<String,Object> StringToMap(String str){
			str = str.substring(1,str.length()-1).replaceAll("\"", "");
			Map<String,Object> map = new HashMap<String,Object>();
			if(!CheckNull.isNull(str) && str.indexOf(",")>0){
				String array[] = str.split(",");
				for(String arr:array){
					map.put(arr.trim().substring(0,arr.indexOf(":")),arr.trim().substring(arr.indexOf(":")+1));
				}
			}
			return map;
		}
	}
}
