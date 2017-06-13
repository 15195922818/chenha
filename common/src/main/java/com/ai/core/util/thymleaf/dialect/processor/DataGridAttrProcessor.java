package com.ai.core.util.thymleaf.dialect.processor;

import org.apache.log4j.Logger;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.attr.AbstractAttrProcessor;

import com.ai.core.util.thymleaf.dialect.ComicPlusDialect;
import com.ai.core.util.thymleaf.dialect.utils.DomUtils;

public class DataGridAttrProcessor extends AbstractAttrProcessor {

	private static final Logger logger = Logger
			.getLogger(DataGridAttrProcessor.class);
	final static String DialectName = "datagrid";
	final static String DialectTagName = "TABLE";

	public DataGridAttrProcessor() {
		super(DialectName);
	}

	@Override
	protected ProcessorResult processAttribute(Arguments arguments,
			Element element, String attributeName) {
		if (!element.getOriginalName().toUpperCase().equals(DialectTagName)) {
			logger.debug("You can't put the " + ComicPlusDialect.DialectName
					+ ":" + DialectName + " attribute into the <"
					+ element.getOriginalName() + "> element - "
					+ "the decoration process will do nothing on tag <"
					+ element.getOriginalName() + ">  ");
			return ProcessorResult.OK;
		}
		Element head = DomUtils.getHead(arguments);
		/*
		 * jquery.find("head>title[des!='']", DomUtils.getHtml(arguments));
		 * jquery.find("head>title[des='']", DomUtils.getHtml(arguments));
		 * jquery.find("head>title[des!^'']", DomUtils.getHtml(arguments));
		 * jquery.find("head>title[des^'']", DomUtils.getHtml(arguments));
		 */
		DomUtils.importJSDepends(DialectName, arguments);

		String initData = element.getAttributeValue(attributeName);
		Element e_2 = new Element("script");
		Text cs = new Text("$(function(){window.Grid_"
				+ element.getAttributeValue("id") + "=AUI.DataGrid(" + initData
				+ ")});");
		e_2.addChild(cs);
		head.addChild(e_2);
		element.removeAttribute(attributeName);
		return ProcessorResult.OK;
	}

	@Override
	public int getPrecedence() {
		return 10;
	}

}
