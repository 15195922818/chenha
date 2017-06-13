package com.ai.core.util.thymleaf.dialect.processor;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.attr.AbstractAttrProcessor;

/**
 * @author zhoupeng
 * 
 */
public class TypeAttrProcessor extends AbstractAttrProcessor {

	private final static String ATTR_NAME = "type";
	private final int Precedence = 90;

	protected TypeAttrProcessor() {
		super(ATTR_NAME);
	}

	@Override
	public int getPrecedence() {
		return Precedence;
	}

	@Override
	protected ProcessorResult processAttribute(Arguments arguments,
			Element element, String attributeName) {

		element.removeAttribute(attributeName);
		return ProcessorResult.OK;
	}

}
