package com.ai.core.util.thymleaf.dialect.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.attr.AbstractAttrProcessor;

import com.ai.core.util.thymleaf.dialect.constants.PageResolverUtils;
import com.ai.core.util.thymleaf.dialect.utils.DomUtils;

public class UseAttrProcessor extends AbstractAttrProcessor {
	protected static Logger logger = Logger.getLogger(UseAttrProcessor.class);
	static final String DialectName = "use";

	public UseAttrProcessor() {
		super(DialectName);
	}

	@Override
	protected ProcessorResult processAttribute(Arguments arguments,
			Element element, String attributeName) {
		String includes = element.getAttributeValue(attributeName);
		List<String> list = new ArrayList<String>();
		if (null == includes || includes.length() == 0) {
			list = new ArrayList<String>();
		} else {
			list = Arrays.asList(includes.split(","));
		}

		List<String> resources = (List<String>) PageResolverUtils
				.getResolver("ai:use");
		if (resources == null) {
			resources = new ArrayList<String>();
			Element e = new Element("c");
			e.addChild(new Element("ai:use"));
			DomUtils.getHtml(arguments).addChild(e);
			e.getParent().extractChild(e);
		}
		resources.addAll(list);
		PageResolverUtils.setResolver("ai:use", resources);

		element.removeAttribute(attributeName);
		return ProcessorResult.OK;
	}

	@Override
	public int getPrecedence() {
		return 99;
	}

}
