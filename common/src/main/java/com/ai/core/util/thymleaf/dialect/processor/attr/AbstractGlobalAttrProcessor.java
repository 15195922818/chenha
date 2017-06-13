package com.ai.core.util.thymleaf.dialect.processor.attr;

import org.thymeleaf.Arguments;
import org.thymeleaf.processor.attr.AbstractAttrProcessor;

/**
 * @author zhoupeng
 * 
 */
public abstract class AbstractGlobalAttrProcessor extends AbstractAttrProcessor {

	protected AbstractGlobalAttrProcessor(String attributeName) {
		super(attributeName);
	}

	public void Finish(Arguments arguments) {
		System.err.println("-----------------------------------after");
		FinishProcess(arguments);
		ClearProcessor(arguments);
	}

	public abstract void FinishProcess(Arguments arguments);

	public abstract void ClearProcessor(Arguments arguments);
}
