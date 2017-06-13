package com.ai.web.entity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.core.constant.Constant;
import com.ai.core.util.domain.entity.StringAndEqualsObject;
import com.ai.core.util.utils.CheckNull;
import com.ai.core.util.utils.HttpClientUtil;
import com.ai.web.controller.LoginController;


/**
 * @author chenhognan
 * @ClassName: AccessToken
 * @Description: 根据appid，secret，grant_type获取access_token和expires_in
 */
public class OpenId extends StringAndEqualsObject {

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	private static final long serialVersionUID = 1L;

	private String appid;
	
	private String secret;
	
	private String code;
	
	private String grant_type;
	
	private String access_token;
	
	private String expires_in;
	
	private String refresh_token;
	
	private String openid;
	
	private String scope;
	
	private String unionid;
	
	private String all_url;
	
	private String errcode;
	
	private static final String url = Constant.OPENID_URL;
	
	public String getAll_url()
	{
		return all_url;
	}

	public void setAll_url(String all_url)
	{
		this.all_url = all_url;
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


	public String getCode()
	{
		return code;
	}


	public void setCode(String code)
	{
		this.code = code;
	}


	public String getGrant_type()
	{
		return grant_type;
	}


	public void setGrant_type(String grant_type)
	{
		this.grant_type = grant_type;
	}


	public String getAccess_token()
	{
		return access_token;
	}


	public void setAccess_token(String access_token)
	{
		this.access_token = access_token;
	}


	public String getExpires_in()
	{
		return expires_in;
	}


	public void setExpires_in(String expires_in)
	{
		this.expires_in = expires_in;
	}


	public String getRefresh_token()
	{
		return refresh_token;
	}


	public void setRefresh_token(String refresh_token)
	{
		this.refresh_token = refresh_token;
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


	public String getUnionid()
	{
		return unionid;
	}


	public void setUnionid(String unionid)
	{
		this.unionid = unionid;
	}


	public String getErrcode()
	{
		return errcode;
	}

	public void setErrcode(String errcode)
	{
		this.errcode = errcode;
	}


	@SuppressWarnings("serial")
	public class OpenIdG extends OpenId{
		/**
		 * 
		 * @param grantType
		 * @param appId
		 * @param secret
		 * @throws ClientProtocolException
		 * @throws IOException
		 */
		public OpenIdG(String appId,String secret,String code,String grantType) throws ClientProtocolException, IOException{
			setAppid(appId);
			setSecret(secret);
			setCode(code);
			setGrant_type(grantType);
			this.OpenIdG();
		}
		
		private void OpenIdG() throws ClientProtocolException, IOException{
			all_url = url+"?appid="+super.getAppid()+"&secret="+super.getSecret()+"&code="+super.getCode()+"&grant_type="+super.getGrant_type();
			
			log.debug("getopenIdByCodeUrl=====" + all_url);
			
			setAll_url(all_url);
	        String accessStr = HttpClientUtil.httpGet(all_url);
			if (accessStr != null) {
	            // 打印响应内容
	            Map<String,Object> map = StringToMap(accessStr);
	            String accessToken = map.get("access_token")==null?"":(String) map.get("access_token");
	            String expiresIn = map.get("expires_in")==null?"":(String) map.get("expires_in");
	            String refresh_token = map.get("refresh_token")==null?"":(String) map.get("refresh_token");
	            String openid = map.get("openid")==null?"":(String) map.get("openid");
	            String scope = map.get("scope")==null?"":(String) map.get("scope");
	            String unionid = map.get("unionid")==null?"":(String) map.get("unionid");
	            String errcode = map.get("errcode")==null?"":(String) map.get("errcode");
	            setAccess_token(accessToken);
	            setExpires_in(expiresIn);
	            setRefresh_token(refresh_token);
	            setOpenid(openid);
	            setScope(scope);
	            setUnionid(unionid);
	            setErrcode(errcode);
	            System.out.println(accessToken);
	        }
	        System.out.println("------------------------------------");  
		};
		
		private Map<String,Object> StringToMap(String str){
			str = str.substring(1,str.length()-1).replaceAll("\"", "");
			Map<String,Object> map = new HashMap<String,Object>();
			log.debug("openidstr========" + str);
			log.debug("str.indexOf(errcode)========" + str.indexOf("errcode"));
			if(!CheckNull.isNull(str) && str.indexOf(",")>0 && str.indexOf("errcode")<0){
				String array[] = str.split(",");
				for(String arr:array){
					map.put(arr.trim().substring(0,arr.indexOf(":")),arr.trim().substring(arr.indexOf(":")+1));
				}
			}
			return map;
		}
	}
}
