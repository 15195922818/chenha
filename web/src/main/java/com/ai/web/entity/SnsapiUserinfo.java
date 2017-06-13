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
public class SnsapiUserinfo extends StringAndEqualsObject {
	
	private static final long serialVersionUID = 1L;

	private String access_token;
	
	private String openid;
	
	private String lang;
	
	private String nickname;
	
	private String sex;
	
	private String province;
	
	private String city;
	
	private String country;
	
	private String headimgurl;
	
	private String privilege;
	
	private String unionid;
	
	private String all_url;
	
	private static final String url = Constant.SNSAPIUSERINFO_URL;
	
	public String getAccess_token()
	{
		return access_token;
	}


	public void setAccess_token(String access_token)
	{
		this.access_token = access_token;
	}


	public String getOpenid()
	{
		return openid;
	}


	public void setOpenid(String openid)
	{
		this.openid = openid;
	}


	public String getLang()
	{
		return lang;
	}


	public void setLang(String lang)
	{
		this.lang = lang;
	}


	public String getNickname()
	{
		return nickname;
	}


	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}


	public String getSex()
	{
		return sex;
	}


	public void setSex(String sex)
	{
		this.sex = sex;
	}


	public String getProvince()
	{
		return province;
	}


	public void setProvince(String province)
	{
		this.province = province;
	}


	public String getCity()
	{
		return city;
	}


	public void setCity(String city)
	{
		this.city = city;
	}


	public String getCountry()
	{
		return country;
	}


	public void setCountry(String country)
	{
		this.country = country;
	}


	public String getHeadimgurl()
	{
		return headimgurl;
	}


	public void setHeadimgurl(String headimgurl)
	{
		this.headimgurl = headimgurl;
	}


	public String getPrivilege()
	{
		return privilege;
	}


	public void setPrivilege(String privilege)
	{
		this.privilege = privilege;
	}


	public String getUnionid()
	{
		return unionid;
	}


	public void setUnionid(String unionid)
	{
		this.unionid = unionid;
	}


	public String getAll_url()
	{
		return all_url;
	}


	public void setAll_url(String all_url)
	{
		this.all_url = all_url;
	}


	@SuppressWarnings("serial")
	public class SnsapiUserinfoG extends SnsapiUserinfo{
		
		private final Logger log = LoggerFactory.getLogger(LoginController.class);
		/**
		 * 
		 * @param grantType
		 * @param appId
		 * @param secret
		 * @throws ClientProtocolException
		 * @throws IOException
		 */
		public SnsapiUserinfoG(String access_token,String openid,String lang) throws ClientProtocolException, IOException{
			setAccess_token(access_token);
			setOpenid(openid);
			setLang(lang);
			this.getSnsapiUserinfo();
		}
		
		private void getSnsapiUserinfo() throws ClientProtocolException, IOException{
			all_url = url+"?access_token="+super.getAccess_token()+"&openid="+super.getOpenid()+"&lang="+super.getLang();
			log.debug("==============all_url=============");
			log.debug("all_url==="+all_url);
			log.debug("==============all_url=============");
			
			setAll_url(all_url);
	        String accessStr = HttpClientUtil.httpGet(all_url);
	        log.debug("accessStr==="+accessStr);
			if (accessStr != null) {
	            // 打印响应内容
	            Map<String,Object> map = StringToMap(accessStr);
	            String openid = map.get("openid")==null?"":(String) map.get("openid");
	            String nickname = map.get("nickname")==null?"":(String) map.get("nickname");
	            String sex = map.get("sex")==null?"":(String) map.get("sex");
	            String province = map.get("province")==null?"":(String) map.get("province");
	            String city = map.get("city")==null?"":(String) map.get("city");
	            String country = map.get("country")==null?"":(String) map.get("country");
	            String headimgurl = map.get("headimgurl")==null?"":(String) map.get("headimgurl");
	            String privilege = map.get("privilege")==null?"":(String) map.get("privilege");
	            String unionid = map.get("nickname")==null?"":(String) map.get("unionid");
	            setOpenid(openid);
	            setNickname(nickname);
	            setSex(sex);
	            setProvince(province);
	            setCity(city);
	            setCountry(country);
	            setHeadimgurl(headimgurl);
	            setPrivilege(privilege);
	            setUnionid(unionid);
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
