package com.ai.core.util.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http请求
 * @author chenhongan
 */
public class HttpClientUtil {

    /**
     * get方式获取数据
     * @return String
     */
    public static String httpGet(String url) {
    	String str = "";
    	CloseableHttpClient httpclient = HttpClients.createDefault();  
        // 创建httpget.    
		HttpGet httpost = new HttpGet(url);  
        // 执行get请求.    
        CloseableHttpResponse response = null;
		try
		{
			response = httpclient.execute(httpost);
		} 
		catch (IOException e)
		{
		}
        // 获取响应实体    
        HttpEntity entity = response.getEntity();  
        if (entity == null) {
        	return null;
        }
        try
		{
        	//编码方式需要设置utf-8
        	str = EntityUtils.toString(entity,"utf-8");
		}
        catch (ParseException | IOException e)
		{
		}
		return str;
    }

}
