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
public class Auth extends StringAndEqualsObject {

	private static final long serialVersionUID = 1L;

	private String appid;
	
	private String redirect_uri;
	
	private String response_type;
	
	private String scope;
	
	private String state;
	
	private String wechat_redirect;
	
	private String call_url;
	
	private String accessStr;
	
	private String get_url;  //需要的全授权url
	
	private final String url = Constant.CODE_URL;
	
	public String getGet_url()
	{
		return get_url;
	}

	public void setGet_url(String get_url)
	{
		this.get_url = get_url;
	}

	public String getAppid()
	{
		return appid;
	}


	public void setAppid(String appid)
	{
		this.appid = appid;
	}


	public String getRedirect_uri()
	{
		return redirect_uri;
	}


	public void setRedirect_uri(String redirect_uri)
	{
		this.redirect_uri = redirect_uri;
	}


	public String getResponse_type()
	{
		return response_type;
	}


	public void setResponse_type(String response_type)
	{
		this.response_type = response_type;
	}


	public String getScope()
	{
		return scope;
	}


	public void setScope(String scope)
	{
		this.scope = scope;
	}


	public String getState()
	{
		return state;
	}


	public void setState(String state)
	{
		this.state = state;
	}


	public String getWechat_redirect()
	{
		return wechat_redirect;
	}


	public void setWechat_redirect(String wechat_redirect)
	{
		this.wechat_redirect = wechat_redirect;
	}
	
	public String getCall_url()
	{
		return call_url;
	}


	public void setCall_url(String call_url)
	{
		this.call_url = call_url;
	}

	@SuppressWarnings("serial")
	public class AuthG extends Auth{
		/**
		 * 
		 * @param grantType
		 * @param appId
		 * @param secret
		 * @throws ClientProtocolException
		 * @throws IOException
		 */
		public AuthG(String appId,String redirect_uri,String response_type,String scope,String state,String wechat_redirect,String call_url) throws ClientProtocolException, IOException{
			setAppid(appId);
			setRedirect_uri(redirect_uri);
			setResponse_type(response_type);
			setScope(scope);
			setState(state);
			setWechat_redirect(wechat_redirect);
			setCall_url(call_url);
			this.getAuthG();
		}
		
		private void getAuthG() throws ClientProtocolException, IOException{
			String getUrl = url+"?appid="+super.getAppid()+"&redirect_uri="+super.getRedirect_uri()+"&response_type="+super.getResponse_type()+"&scope="+super.getScope()+"&state="+super.getState()+"#wechat_redirect";
	        setGet_url(getUrl);
		};
	}
}
