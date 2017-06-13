package com.ai.core.util.thymleaf.dialect.processor.tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Comment;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.element.AbstractElementProcessor;

import com.ai.core.util.thymleaf.dialect.ComicPlusDialectUtils;
import com.ai.core.util.thymleaf.dialect.constants.PageResolverUtils;
import com.ai.core.util.thymleaf.dialect.utils.DomUtils;
import com.ai.core.util.thymleaf.dialect.utils.JavaScriptUses;

public class InitResourceResolverProcessor extends AbstractElementProcessor {

	private static Logger logger = Logger
			.getLogger(InitResourceResolverProcessor.class);
	private final static String Tag_Name = "use";
	private final static int Precedence = 100001;

	public InitResourceResolverProcessor() {
		super(Tag_Name);
	}

	@Override
	public int getPrecedence() {
		return Precedence;
	}

	@Override
	protected ProcessorResult processElement(Arguments arguments,
			Element element) {
		resolveResources(arguments, element);

		element.getParent().removeChild(element);
		return ProcessorResult.OK;
	}

	@SuppressWarnings("unchecked")
	private void resolveResources(Arguments arguments, Element element) {
		List<String> resource = new ArrayList<String>();
		List<String> resources = (List<String>) PageResolverUtils
				.getResolver("ai:use");
		PageResolverUtils.setResolver("ai:use", null);

		for (String compnentName : resources) {
			logger.debug("begain-resolve-JSDepends : Component " + compnentName);
			resource.addAll(importResource(resource, compnentName));
			logger.debug("end-resolve-JSDepends : Component " + compnentName);
		}

		Element e = new Element("container");
		for (String compnentName : resource) {
			Map<String, Object> beanMap = ComicPlusDialectUtils.config
					.get(compnentName);
			List<JavaScriptUses> list = (List<JavaScriptUses>) beanMap
					.get("uses");

			e.addChild(new Comment("javascript component： " + compnentName));
			for (JavaScriptUses use : list) {
				e.addChild(use.transToNode());
			}
		}

		Element head = DomUtils.getHead(arguments);
		head.insertChild(0, e);
		head.extractChild(e);

	}

	private List<String> importResource(List<String> exists, String compnentName) {
		Map<String, Object> beanMap = ComicPlusDialectUtils.config
				.get(compnentName);
		if (beanMap == null) {
			logger.debug("can not find Component " + compnentName);
		}

		List<String> resources = new ArrayList<String>();

		if (exists.contains(compnentName)) {
			logger.debug("Component already exist : " + compnentName);
			return resources;
		}

		logger.debug("importJSDepends : Component " + compnentName);
		if (beanMap.get("depends") != null) {
			List<String> depends = (List<String>) beanMap.get("depends");
			for (String depend : depends) {
				logger.debug("importJSDepends : Component " + depend + " ( "
						+ compnentName + " depended )");
				resources.addAll(importResource(exists, depend));
			}
		}
		resources.add(compnentName);
		return resources;
	}

}
