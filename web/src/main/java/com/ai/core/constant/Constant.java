package com.ai.core.constant;

public class Constant
{
	/**
	 * 成功!
	 */
	public final static String SUCCESS="SUCCESS";
	
	/**
	 * appId!
	 */
	public final static String APPID = "wx8a14e1ce687fe13c";
	
	/**
	 * SERVICE_APPID
	 */
	public final static String SERVICE_APPID = "wxb7691b7d246ea6dd";
	
	/**
	 * SERVICE_SECRET
	 */
	public final static String SERVICE_SECRET = "b480c00df8a1fff7d148f5094e409786";
	
	/**
	 * 测试appId!
	 */
	public final static String TEST_APPID = "wx32b89e49e10d0914";
	
	/**
	 * SECRET!
	 */
	public final static String SECRET = "0ff038f17000d83d8624d32c9d0e2304";
	
	/**
	 * 测试SECRET!
	 */
	public final static String TEST_SECRET = "1d28e6f4efb2dfb7337bf372aec39909";
	
	/**
	 * ACCESSTOKEN_URL!
	 */
	public final static String ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
	
	/**
	 * GRANT_TYPE!
	 */
	public final static String GRANT_TYPE = "client_credential";
	
	/**
	 * GRANT_TYPE!
	 */
	public final static String JSAPITICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
	
	/**
	 * CODE_URL!
	 */
	public final static String CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";
	
	/**
	 * CALL_URL!授权之后的回调url
	 */
	public final static String CALL_URL = "http://examgame.51safety.com.cn/wxcs/new/homePage";
	
	/**
	 * TEST_CALL_URL本地测试
	 */
	public final static String TEST_CALL_URL = "http://localhost:8080/wxcs/new/homePage";
	
	/**
	 * CODE
	 */
	public final static String CODE = "code";
	
	/**
	 * SNSAPI_USERINFO应用授权作用域，snsapi_base
	 */
	public final static String SNSAPI_USERINFO = "snsapi_userinfo";
	
	/**
	 * STATE重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
	 */
	public final static String STATE = "state";
	
	/**
	 * #wechat_redirect无论直接打开还是做页面302重定向时候，必须带此参数
	 */
	public final static String WECHAT_REDIRECT = "#wechat_redirect";
	
	/**
	 * ISAUTH
	 */
	public final static String ISAUTH = "已经授权";
	
	/**
	 * OPENID_URL获取openId等的url
	 */
	public final static String OPENID_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
	
	/**
	 * GRANT_TYPE
	 */
	public final static String OPENID_GRANT_TYPE = "authorization_code";
	
	/**
	 * REFRESH_ACCESSTOKEN_URL
	 */
	public final static String REFRESH_ACCESSTOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
	
	/**
	 * REFRESHTOKEN_GRANT_TYPE
	 */
	public final static String REFRESHTOKEN_GRANT_TYPE = "refresh_token";
	
	/**
	 * SNSAPIUSERINFO_URL
	 */
	public final static String SNSAPIUSERINFO_URL = "https://api.weixin.qq.com/sns/userinfo";
	
	/**
	 * SNSAPIUSERINFO_LANG(zh_CN 简体，zh_TW 繁体，en 英语)
	 */
	public final static String SNSAPIUSERINFO_LANG = "zh_CN";
	
	/**
	 * 获取ACCESS_TOKEN的周期(单位:分钟)
	 */
	public final static long ACCESS_TOKEN_INTERVAL = 60;
	
	
	
	public final static String QuestionController = "com.ai.web.controller.QuestionController";
	
}
	