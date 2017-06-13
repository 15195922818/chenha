package com.test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import com.ai.core.constant.Constant;
import com.ai.web.controller.QuestionController;

public class test4
{
	public static void main(String[] args) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException
	{
		Class<?> clazz = Class.forName(Constant.QuestionController);
		
        Field[] fields = clazz.getDeclaredFields();
        
        for(Field field : fields)

        {

        	System.out.print(field.getName() +"->");
        	
        	System.out.println(Modifier.toString(field.getModifiers()));
        	
        	String mdfy = String.valueOf(Modifier.toString(field.getModifiers()));
        	
        	if(mdfy.indexOf("private static")>=0 && field.getType().getName().toString().indexOf("Map")>=0){
        		
        		field.setAccessible(true);  //该方法表示取消java语言访问检查 
        		
        		Object pro = field.get("prop");
        		
        		((Map)pro).clear();
        		
        	}

        }
	}
}
