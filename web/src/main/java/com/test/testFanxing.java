package com.test;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class testFanxing {
	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		/*Method method = MyClass.class.getMethod("setStringList", List.class,Set.class);
		Type[] genericParameterTypes = method.getGenericParameterTypes();
		for(Type genericParameterType : genericParameterTypes){
		    if(genericParameterType instanceof ParameterizedType){
		        ParameterizedType aType = (ParameterizedType) genericParameterType;
		        Type[] parameterArgTypes = aType.getActualTypeArguments();
		        for(Type parameterArgType : parameterArgTypes){
		            Class parameterArgClass = (Class) parameterArgType;
		            System.out.println("parameterArgClass = " + parameterArgClass);
		        }
		    }
		}*/
		String o = new aaaa<String>().get();
		System.out.println(o);
	}
}

class MyClass {
	  protected List<String> stringList = new ArrayList<String>();
	  public void setStringList(List<Object> list,Set<Integer> set){
	    
	  }
}
	     //你可以像这样来访问其方法参数的参数化类型：

class aaaa<T>{
	public <T> T get(){
		Object num="123";
		T t = (T)num;
		return t;
	}
}
