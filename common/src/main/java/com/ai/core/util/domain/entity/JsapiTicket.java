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
public class JsapiTicket extends StringAndEqualsObject {

	private static final long serialVersionUID = 1L;

	private String accessToken;
	
	private String errcode;
	
	public String getErrcode()
	{
		return errcode;
	}

	public void setErrcode(String errcode)
	{
		this.errcode = errcode;
	}

	public String getErrmsg()
	{
		return errmsg;
	}

	public void setErrmsg(String errmsg)
	{
		this.errmsg = errmsg;
	}

	public String getTicket()
	{
		return ticket;
	}

	public void setTicket(String ticket)
	{
		this.ticket = ticket;
	}

	public String getExpiresIn()
	{
		return expiresIn;
	}

	public void setExpiresIn(String expiresIn)
	{
		this.expiresIn = expiresIn;
	}

	private String errmsg;
	
	private String ticket;
	
	private String expiresIn;
	
	private static final String url = Constant.JSAPITICKET_URL;
	
	public String getAccessToken()
	{
		return accessToken;
	}

	public void setAccessToken(String accessToken)
	{
		this.accessToken = accessToken;
	}

	@SuppressWarnings("serial")
	public class JsapiTicketG extends JsapiTicket{
		/**
		 * 
		 * @param grantType
		 * @param appId
		 * @param secret
		 * @throws ClientProtocolException
		 * @throws IOException
		 */
		public JsapiTicketG(String accessToken) throws ClientProtocolException, IOException{
			setAccessToken(accessToken);
			this.getJsapiTicketG();
		}
		
		private void getJsapiTicketG() throws ClientProtocolException, IOException{
			String getUrl = url+"?access_token="+super.getAccessToken()+"&&type=jsapi";
	        String accessStr = HttpClientUtil.httpGet(getUrl);
			if (accessStr != null) {
	            // 打印响应内容
	            Map<String,Object> map = StringToMap(accessStr);
	            String accessToken = map.get("access_token")==null?"":(String) map.get("access_token");
	            String errcode = map.get("errcode")==null?"":(String) map.get("errcode");
	            String errmsg = map.get("errmsg")==null?"":(String) map.get("errmsg");
	            String ticket = map.get("ticket")==null?"":(String) map.get("ticket");
	            String expiresIn = map.get("expires_in")==null?"":(String) map.get("expires_in");
	            setAccessToken(accessToken);
	            setErrcode(errcode);
	            setErrmsg(errmsg);
	            setTicket(ticket);
	            setExpiresIn(expiresIn);
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
