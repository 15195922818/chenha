package com.ai.core.util.thymleaf.dialect.processor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.thymeleaf.Arguments;
import org.thymeleaf.Configuration;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.attr.AbstractAttrProcessor;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;

import com.ai.core.util.thymleaf.dialect.ComicPlusDialect;
import com.ai.core.util.thymleaf.dialect.utils.DomUtils;
import com.ai.core.util.thymleaf.jquery.jquery;

/**
 * @author zhoupeng
 * @date 2014/07
 */
public class MetaAttrProcessor extends AbstractAttrProcessor {

	private static final Logger logger = Logger
			.getLogger(MetaAttrProcessor.class);
	final static String DialectName = "user";
	final static String DialectTagName = "META";
	final static String DialectAttr = "metaBind";

	public MetaAttrProcessor() {
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
		if (jquery.getAttr(head, DialectAttr).length() == 0) {

			final HttpSession session = (HttpSession) explainExpression(
					arguments, "${#httpSession}");

			final HttpServletRequest request = (HttpServletRequest) explainExpression(
					arguments, "${#httpServletRequest}");

			element.setAttribute("pageName", request.getRequestURI());

			element.removeAttribute(attributeName);
		}
		return ProcessorResult.OK;
	}

	Object explainExpression(Arguments arguments, String exp) {

		final Configuration configuration = arguments.getConfiguration();

		final IStandardExpressionParser parser = StandardExpressions
				.getExpressionParser(configuration);

		final IStandardExpression expression = parser.parseExpression(
				configuration, arguments, exp);

		return expression.execute(configuration, arguments);
	}

	@Override
	public int getPrecedence() {
		return 104;
	}

}
