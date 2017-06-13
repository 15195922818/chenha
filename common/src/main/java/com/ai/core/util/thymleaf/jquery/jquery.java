package com.ai.core.util.thymleaf.jquery;

import java.util.List;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Document;
import org.thymeleaf.dom.Element;

public class jquery {
	@SuppressWarnings("unused")
	private final Document document;
	private final Element html;

	public jquery(Document d) {
		super();
		this.document = d;
		this.html = d.getFirstElementChild();
	}

	public jquery(Arguments arguments) {
		super();
		Document d = arguments.getDocument();
		this.document = d;
		this.html = d.getFirstElementChild();
	}

	/**
	 * @param el
	 *            jQuery 选择器
	 * @return List of org.thymeleaf.dom.Element
	 */
	public List<Element> find(String el) {
		return jqueryFinder.find(el, html);
	}

	/**
	 * @param el
	 *            jQuery 选择器
	 * @param e
	 *            在节点e下寻找
	 * @return List of org.thymeleaf.dom.Element
	 */
	public static List<Element> find(String el, Element e) {
		return jqueryFinder.find(el, e);
	}

	public static String getAttr(Element e, String attrName) {
		return jqueryFinder.getAttr(e, attrName);
	}
}
