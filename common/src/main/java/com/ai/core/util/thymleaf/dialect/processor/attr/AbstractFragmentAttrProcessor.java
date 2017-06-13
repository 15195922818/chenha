package com.ai.core.util.thymleaf.dialect.processor.attr;

import java.util.List;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Attribute;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.fragment.WholeFragmentSpec;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.attr.AbstractAttrProcessor;
import org.thymeleaf.standard.fragment.StandardFragment;
import org.thymeleaf.standard.fragment.StandardFragmentProcessor;

import com.ai.core.util.thymleaf.dialect.ComicPlusDialect;

/**
 * @author zhoupeng
 * @date 2014-11-14
 * @email zhoupeng@asiainfo.com
 * @useage
 */
public abstract class AbstractFragmentAttrProcessor extends
		AbstractAttrProcessor {

	public AbstractFragmentAttrProcessor(String attributeName) {
		super(attributeName);
	}

	public static final int ATTR_PRECEDENCE = 100;
	public static final String FRAGMENT_ATTR_NAME = "layout";
	public static final String Contain_Body_Name = ComicPlusDialect.DialectName
			+ ":body";
	public static final String LOCATION_PREFIX = "fragment/";

	@Override
	public abstract int getPrecedence();

	@Override
	public final ProcessorResult processAttribute(final Arguments arguments,
			final Element element, final String attributeName) {

		final String attributeValue = element.getAttributeValue(attributeName);

		// 是否包保留最外层元素
		final boolean containFragmentWrap = !removeFragmentWrap(arguments,
				element, attributeName, attributeValue);

		final List<Node> fragmentNodes = computeFragment(arguments, element,
				attributeName, attributeValue);

		if (fragmentNodes == null) {
			throw new TemplateProcessingException("Cannot correctly process \""
					+ attributeName + "\" attribute. "
					+ "Fragment specification \"" + attributeValue
					+ "\" matched null.");
		} else if (containFragmentWrap) {
			final String FragmentAttributeName = getTargetAttributeName(
					arguments, element, attributeName, attributeValue);
			for (final Node fragmentNode : fragmentNodes) {
				((Element) fragmentNode).removeAttribute(FragmentAttributeName);
			}
		}

		List<Node> resultNodes = fragmentNodes;

		boolean FragmentEditable = FragmentEditable(arguments, element,
				attributeName, attributeValue);

		if (FragmentEditable) {
			resultNodes = translateFragment(fragmentNodes, arguments, element,
					attributeName, attributeValue);
		}

		// 是否移除当前元素
		final boolean removeElementWrap = removeElementWrap(arguments, element,
				attributeName, attributeValue);

		element.clearChildren();
		element.removeAttribute(attributeName);

		if (removeElementWrap) {
			element.setChildren(resultNodes);
			element.getParent().extractChild(element);
		} else {
			for (final Node fragmentNode : resultNodes) {
				element.addChild(fragmentNode);
			}
		}

		return ProcessorResult.OK;

	}

	/**
	 * [调用thymeleaf全局匹配fragment]
	 * 
	 * @param arguments
	 * @param element
	 * @param attributeName
	 * @param fragmentSpec
	 * @param containFragmentWrap
	 * @return
	 */
	protected final List<Node> computeFragment(final Arguments arguments,
			final Element element, final String attributeName,
			final String attributeValue) {

		final String dialectPrefix = Attribute
				.getPrefixFromAttributeName(attributeName);

		// fragment匹配的表达式
		final String fragmentSignatureAttributeName = getTargetAttributeName(
				arguments, element, attributeName, attributeValue);

		final String TargetAttributeName = getFragmentSpec(arguments, element,
				attributeName, attributeValue);
		final StandardFragment fragment = StandardFragmentProcessor
				.computeStandardFragmentSpec(arguments.getConfiguration(),
						arguments, TargetAttributeName, dialectPrefix,
						fragmentSignatureAttributeName);

		final List<Node> extractedNodes = fragment.extractFragment(
				arguments.getConfiguration(), arguments,
				arguments.getTemplateRepository());

		// 是否保存fragment的父节点
		final boolean saveFragmentWrap = !removeFragmentWrap(arguments,
				element, attributeName, attributeValue);

		// If fragment is a whole document (no selection inside), we should
		// never remove its parent node/s
		// Besides, we know that
		// StandardFragmentProcessor.computeStandardFragmentSpec only creates
		// two types of
		// IFragmentSpec objects: WholeFragmentSpec and DOMSelectorFragmentSpec.
		final boolean isWholeDocument = (fragment.getFragmentSpec() instanceof WholeFragmentSpec);

		if (extractedNodes == null || saveFragmentWrap || isWholeDocument) {
			return extractedNodes;
		}

		// Host node is NOT to be removed, therefore what should be removed is
		// the top-level elements of the returned
		// nodes.

		final Element containerElement = new Element("container");

		for (final Node extractedNode : extractedNodes) {
			// This is done in this indirect way in order to preserver internal
			// structures like e.g. local variables.
			containerElement.addChild(extractedNode);
			containerElement.extractChild(extractedNode);
		}

		final List<Node> extractedChildren = containerElement.getChildren();
		containerElement.clearChildren();

		return extractedChildren;

	}

	/**
	 * [ 获取要匹配的fragement的属性名称 ]
	 * 
	 * @param arguments
	 * @param element
	 * @param attributeName
	 * @param attributeValue
	 * @return
	 */
	private String getTargetAttributeName(Arguments arguments, Element element,
			String attributeName, String attributeValue) {
		return FRAGMENT_ATTR_NAME;
	}

	/**
	 * [ 获取要匹配的fragement的属性值/位置 /解析表达式 ]
	 * 
	 * @param arguments
	 * @param element
	 * @param attributeName
	 * @param attributeValue
	 * @param containFragmentWrap
	 * @return
	 */
	private String getFragmentSpec(final Arguments arguments,
			final Element element, final String attributeName,
			final String attributeValue) {
		String spec = attributeValue;
		if (attributeValue.indexOf(LOCATION_PREFIX) == -1) {
			spec = LOCATION_PREFIX + attributeValue;
		}
		if (spec.indexOf(">") > -1) {
			return spec.replace(">", " :: ");
		} else {
			return spec;
		}
	}

	/**
	 * [ 是否去除当前元素 ]
	 * 
	 * @param arguments
	 * @param element
	 * @param attributeName
	 * @param attributeValue
	 * @return
	 */
	protected abstract boolean removeElementWrap(Arguments arguments,
			Element element, String attributeName, String attributeValue);

	/**
	 * [是否去除匹配到fragement的最外层元素]
	 * 
	 * @param arguments
	 * @param element
	 * @param attributeName
	 * @param attributeValue
	 * @return
	 */
	protected boolean removeFragmentWrap(Arguments arguments, Element element,
			String attributeName, String attributeValue) {
		return true;
	};

	/**
	 * [是否编辑fragment]
	 * 
	 * @param arguments
	 * @param element
	 * @param attributeName
	 * @param attributeValue
	 * @return
	 */
	protected abstract boolean FragmentEditable(Arguments arguments,
			Element element, String attributeName, String attributeValue);

	/**
	 * [处理返回的fragment并返回]
	 * 
	 * @param fragmentNodes
	 * @param arguments
	 * @param element
	 * @param attributeName
	 * @param attributeValue
	 * @return
	 */
	protected abstract List<Node> translateFragment(List<Node> fragmentNodes,
			Arguments arguments, Element element, String attributeName,
			String attributeValue);

}
