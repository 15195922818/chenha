package com.ai.core.util.utils;

import java.util.Random;

public class QuestionUtil {
	 private static Random random = new Random();  
	 private static char[] charSequence = "0123456789".toCharArray();  
    public static String getRandomCode(){
		String code="";
		for(int i=0;i<4;i++){
			code+=getRandomChar();
		}
		  return code;
	}
    
    private static String getRandomChar() {    
        int index = random.nextInt(charSequence.length);  
        return String.valueOf(charSequence[index]);  
    }  
    
    public static void main(String[] args) {
    	for(int i=0;i<100;i++){
    		System.out.println(random.nextInt(10));
    	}
		
	}
}  
