package com.test;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

public class testPZ {
	Set<Resource> set;
	public Set<Resource> method(){
		System.out.println(111);
		return set;
	}
	
	public void setSet(Set<Resource> set){
		this.set=set;
	}
	
	@PostConstruct
	public void in(){
		Iterator<Resource> it = set.iterator();
		SAXBuilder builder = new SAXBuilder();
		while(it.hasNext())
		{
			try {
				Document doc = builder.build(it.next().getInputStream());
				Element ele = doc.getRootElement();

				List<Element> beans = ele.getChildren("xml-body");
				for(Element bean:beans)
				{
					Element element = bean.getChild("a");
					System.out.println(element.getValue());
				}
			} catch (JDOMException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
