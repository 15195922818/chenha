package com.ai.web.entity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.ai.core.constant.Constant;
import com.ai.core.util.domain.entity.StringAndEqualsObject;
import com.ai.core.util.utils.CheckNull;
import com.ai.core.util.utils.HttpClientUtil;


/**
 * @author chenhognan
 * @ClassName: AccessToken
 * @Description: 根据appid，secret，grant_type获取access_token和expires_in
 */
public class RefreshToken extends StringAndEqualsObject {

	private static final long serialVersionUID = 1L;

	private String appid;
	
	private String grant_type;
	
	private String refresh_token;
	
	private String access_token;
	
	private String new_refresh_token;  //刷新后的
	 
	private String openid;
	
	private String scope;
	
	private String all_url;
	
	private static final String url = Constant.REFRESH_ACCESSTOKEN_URL;
	
	
	public String getAppid()
	{
		return appid;
	}


	public void setAppid(String appid)
	{
		this.appid = appid;
	}


	public String getGrant_type()
	{
		return grant_type;
	}


	public void setGrant_type(String grant_type)
	{
		this.grant_type = grant_type;
	}


	public String getRefresh_token()
	{
		return refresh_token;
	}


	public void setRefresh_token(String refresh_token)
	{
		this.refresh_token = refresh_token;
	}


	public String getAccess_token()
	{
		return access_token;
	}


	public void setAccess_token(String access_token)
	{
		this.access_token = access_token;
	}


	public String getNew_refresh_token()
	{
		return new_refresh_token;
	}


	public void setNew_refresh_token(String new_refresh_token)
	{
		this.new_refresh_token = new_refresh_token;
	}


	public String getOpenid()
	{
		return openid;
	}


	public void setOpenid(String openid)
	{
		this.openid = openid;
	}


	public String getScope()
	{
		return scope;
	}


	public void setScope(String scope)
	{
		this.scope = scope;
	}


	public String getAll_url()
	{
		return all_url;
	}


	public void setAll_url(String all_url)
	{
		this.all_url = all_url;
	}


	public static String getUrl()
	{
		return url;
	}


	@SuppressWarnings("serial")
	public class RefreshTokenG extends RefreshToken{
		/**
		 * 	
		 * @param grantType
		 * @param appId
		 * @param secret
		 * @throws ClientProtocolException
		 * @throws IOException
		 */
		public RefreshTokenG(String appId,String grant_type,String refresh_token) throws ClientProtocolException, IOException{
			setAppid(appId);
			setGrant_type(grant_type);
			setRefresh_token(refresh_token);
			this.getRefreshTokenG();
		}
		
		private void getRefreshTokenG() throws ClientProtocolException, IOException{
			all_url = url+"?appid="+super.getAppid()+"&grant_type="+super.getGrant_type()+"&refresh_token="+super.getRefresh_token();
	        setAll_url(all_url);
			String accessStr = HttpClientUtil.httpGet(all_url);
			if (accessStr != null) {
	            // 打印响应内容
	            Map<String,Object> map = StringToMap(accessStr);
	            String accessToken = map.get("access_token")==null?"":(String) map.get("access_token");
	            String refresh_token = map.get("refresh_token")==null?"":(String) map.get("refresh_token");
	            String openid = map.get("openid")==null?"":(String) map.get("openid");
	            String scope = map.get("scope")==null?"":(String) map.get("scope");
	            setAccess_token(accessToken);
	            setRefresh_token(refresh_token);
	            setOpenid(openid);
	            setScope(scope);
	        }
	        System.out.println("------------------------------------");  
		};
		
		private Map<String,Object> StringToMap(String str){
			if(str.indexOf("scope")>0){
				str = str.substring(0,str.indexOf("scope")-1);
			}
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
