package com.ai.core.util.thymleaf.dialect.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Comment;
import org.thymeleaf.dom.Document;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;

import com.ai.core.util.thymleaf.dialect.ComicPlusDialectUtils;
import com.ai.core.util.thymleaf.jquery.jquery;

public class DomUtils {
	final static String IncludeAttr = "ai:used";

	protected static Logger logger = Logger.getLogger(DomUtils.class);

	/**
	 * [添加javascript依赖]
	 * 
	 * @param arguments
	 * @param depends
	 */
	public static void importJSDepends(String compnentName, Arguments arguments) {
		importBean(compnentName, arguments);
	}

	/**
	 * [添加单个javascript依赖,并且遍历添加该javascript的依赖]
	 * 
	 * @param depend
	 * @param head
	 */
	private static void importBean(String compnentName, Arguments arguments) {
		Element html = getHtml(arguments);
		importBean(compnentName, html);
	}

	@SuppressWarnings("unchecked")
	private static void importBean(String compnentName, Element html) {
		Element head = html.getFirstElementChild();
		List<String> includes = getIncludeBeans(head);
		if (includes.contains(compnentName)) {
			return;
		}
		Map<String, Object> beanMap = ComicPlusDialectUtils.config
				.get(compnentName);
		if (beanMap == null) {
			logger.debug("can not find Component " + compnentName);
			return;
		}
		logger.debug("importJSDepends : Component " + compnentName);
		if (beanMap.get("depends") != null) {
			List<String> depends = (List<String>) beanMap.get("depends");
			for (String depend : depends) {
				importBean(depend, html);
			}
		}
		List<JavaScriptUses> list = (List<JavaScriptUses>) beanMap.get("uses");
		List<Element> es = jquery.find("head>meta[" + IncludeAttr + "='true']",
				html);
		Element meta;
		if (es.size() == 0) {
			meta = new Element("meta");
			meta.setAttribute(IncludeAttr, "true");
			Element first = getHead(html).getFirstElementChild();
			getHead(html).insertBefore(first, meta);
		} else {
			meta = es.get(0);
		}
		if (list.size() > 0) {
			head.insertBefore(meta, new Comment("javascript component： "
					+ compnentName));
			for (JavaScriptUses use : list) {
				Node node = use.transToNode();
				head.insertBefore(meta, node);
			}
		}
		includes = getIncludeBeans(head);
		includes.add(compnentName);
		setIncludeBeans(includes, head);

	}

	private static void setIncludeBeans(List<String> includes, Element head) {
		StringBuilder strbui = new StringBuilder();
		for (String str : includes) {
			strbui.append(str).append(",");
		}
		head.setAttribute(IncludeAttr, strbui.substring(0, strbui.length() - 1));
	}

	public static List<String> getIncludeBeans(Element head) {
		String includes = jquery.getAttr(head, IncludeAttr);
		if (null == includes || includes.length() == 0) {
			return new ArrayList<String>();
		}
		// http://blog.csdn.net/thunderous/article/details/3693362
		// 在使用Arrays.asList()后调用add，remove这些method时出现java.lang.UnsupportedOperationException异常
		return new ArrayList(Arrays.asList(includes.split(",")));
	}

	/**
	 * [获取Head标签]
	 * 
	 * @param arguments
	 *            thymeleaf全局变量
	 * @return org.thymeleaf.dom.Element
	 */
	public static Element getHead(Arguments arguments) {
		Element html = getHtml(arguments);
		return html.getFirstElementChild();
	}

	/**
	 * [获取Head标签]
	 * 
	 * @param html
	 *            html节点
	 * @return org.thymeleaf.dom.Element
	 */
	public static Element getHead(Element html) {
		return html.getFirstElementChild();
	}

	/**
	 * [获取Html标签]
	 * 
	 * @param arguments
	 * @return org.thymeleaf.dom.Element
	 */
	public static Element getHtml(Arguments arguments) {
		Document d = getDocument(arguments);
		return d.getFirstElementChild();
	}

	/**
	 * [获取Document标签]
	 * 
	 * @param arguments
	 * @return org.thymeleaf.dom.Document;
	 */
	public static Document getDocument(Arguments arguments) {
		return arguments.getDocument();
	}

}
