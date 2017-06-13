package com.ai.core.util.utils;


/**
 * @author chenhongan 封装java基本方法(效率提高)
 */
public class JUtils {

	/**
	 * 切割字符串
	 * 
	 * @return array
	 */
	public static String[] split(String str,String reg) {
		String array[] = new String[100000];
		if(str.length()>0){
			int i = 0;
			while(str.indexOf(reg)>0){
				int num = str.indexOf(reg);
				array[i] = str.substring(0,num);
				str = str.substring(num+1);
				i++;
			}
		}
		return array;
	}
}
