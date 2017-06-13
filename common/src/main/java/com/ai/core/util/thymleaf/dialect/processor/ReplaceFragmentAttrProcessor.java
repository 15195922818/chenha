/**
 * 
 */
package com.ai.core.util.thymleaf.dialect.processor;

import java.util.ArrayList;
import java.util.List;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;

import com.ai.core.util.thymleaf.dialect.processor.attr.AbstractFragmentAttrProcessor;

/**
 * @author zhoupeng
 * @date 2014-11-15
 * @email zhoupeng@asiainfo.com
 * @useage
 */
public class ReplaceFragmentAttrProcessor extends AbstractFragmentAttrProcessor {

	public static final int ATTR_PRECEDENCE = 101;
	public static final String ATTR_NAME = "replace";
	private final boolean removeElementWrap = true;
	private final boolean FragmentEditable = false;

	public ReplaceFragmentAttrProcessor() {
		super(ATTR_NAME);
	}

	@Override
	public int getPrecedence() {
		return ATTR_PRECEDENCE;
	}

	@Override
	protected boolean removeElementWrap(Arguments arguments, Element element,
			String attributeName, String attributeValue) {
		return removeElementWrap;
	}

	@Override
	protected boolean FragmentEditable(Arguments arguments, Element element,
			String attributeName, String attributeValue) {
		return FragmentEditable;
	}

	@Override
	protected List<Node> translateFragment(List<Node> fragmentNodes,
			Arguments arguments, Element element, String attributeName,
			String attributeValue) {
		Element wrap = new Element("div");
		List<Node> children = element.getChildren();

		for (Node fragmentNode : fragmentNodes) {
			wrap.addChild(fragmentNode);
		}

		return setTbody(wrap, children);
	}

	List<Node> setTbody(Element wrap, List<Node> children) {
		List<Element> list = new ArrayList<Element>();
		getTbody(wrap, list);
		if (list.size() > 0) {
			Element body = list.get(0);
			for (Node node : children) {
				node = node.cloneNode(null, false);
				body.addChild(node);
			}
			body.getParent().extractChild(body);
		}

		List<Node> result = wrap.getChildren();
		wrap.clearChildren();
		return result;
	}

	private void getTbody(Element wrap, List<Element> list) {
		for (Element e : wrap.getElementChildren()) {
			if (e.getOriginalName().equals(Contain_Body_Name)) {
				list.add(e);
				break;
			}
			if (e.getElementChildren().size() > 0)
				getTbody(e, list);
		}
	}
}
