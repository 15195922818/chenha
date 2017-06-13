package com.ai.core.util.thymleaf.dialect;

import java.util.HashMap;
import java.util.Map;

import org.thymeleaf.dom.Attribute;
import org.thymeleaf.dom.Element;
import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.standard.processor.attr.StandardWithAttrProcessor;

public class ComicPlusDialectUtils {

	public static final String LINE_SEPARATOR = System
			.getProperty("line.separator");
	public static final String HTML_ELEMENT_HTML = "html";
	public static final String HTML_ELEMENT_HEAD = "head";
	public static final String HTML_ELEMENT_TITLE = "title";
	public static final String HTML_ELEMENT_BODY = "body";
	public static Map<String, Map<String, Object>> config = new HashMap<String, Map<String, Object>>();

	/**
	 * Default hidden constructor as this class is only ever meant to be used
	 * statically.
	 */
	private ComicPlusDialectUtils() {
	}

	/**
	 * Returns whether the attribute contains the given prefix and name parts.
	 * Checks both the prefixed and data- versions of a Thymeleaf attribute
	 * processor.
	 * 
	 * @param attribute
	 * @param prefix
	 * @param name
	 * @return <tt>true</tt> if the attribute name consists of the prefix and
	 *         name parts.
	 */
	public static boolean equalsAttributeName(Attribute attribute,
			String prefix, String name) {

		String attributename = attribute.getOriginalName();
		return attributename.equals(prefix + ":" + name)
				|| attributename.equals("data-" + prefix + "-" + name);
	}

	/**
	 * Returns the value of the attribute. Checks both the prefixed and data-
	 * versions of a Thymeleaf attribute processor.
	 * 
	 * @param element
	 * @param prefix
	 * @param name
	 * @return The value of the attribute, in either form, or <tt>null</tt> if
	 *         the value doesn't exist in either form.
	 */
	public static String getAttributeValue(Element element, String prefix,
			String name) {

		String value = element.getAttributeValue(prefix + ":" + name);
		if (value == null) {
			value = element.getAttributeValue("data-" + prefix + "-" + name);
		}
		return value;
	}

	/**
	 * Returns whether or not the attribute on the element exists. Checks both
	 * the prefixed and data- versions of a Thymeleaf attribute processor.
	 * 
	 * @param element
	 * @param prefix
	 * @param name
	 * @return <tt>true</tt> if the attribute exists on the element, in either
	 *         form.
	 */
	public static boolean hasAttribute(Element element, String prefix,
			String name) {

		return element.hasAttribute(prefix + ":" + name)
				|| element.hasAttribute("data-" + prefix + "-" + name);
	}

	/**
	 * Replace the attributes of the target element with those of the source
	 * element. A merge is done on the <tt>th:with</tt> attribute, while all
	 * other attributes are simply overidden.
	 * 
	 * @param targetelement
	 * @param sourceelement
	 */
	public static void pullAttributes(Element targetelement,
			Element sourceelement) {

		pullAttributes(targetelement, sourceelement, false);
	}

	/**
	 * The same as {@link #pullAttributes(Element, Element)}, but with the
	 * option to specify whether to copy everything over, or only attributes
	 * that already exist in <tt>sourceelement</tt>.
	 * 
	 * @param targetelement
	 * @param sourceelement
	 * @param mergeonly
	 *            <tt>true</tt> to pull only attributes that exist in
	 *            <tt>targetelement</tt>. <tt>th:with</tt> values will continue
	 *            to be brought in regardless.
	 */
	public static void pullAttributes(Element targetelement,
			Element sourceelement, boolean mergeonly) {

		if (sourceelement == null || targetelement == null) {
			return;
		}

		for (Attribute sourceattribute : sourceelement.getAttributeMap()
				.values()) {

			// Exclude the copying of fragment attributes
			if (equalsAttributeName(sourceattribute,
					ComicPlusDialect.DialectName,
					ComicPlusDialect.PROCESSOR_NAME_FRAGMENT)) {
				continue;
			}

			// Merge th:with attributes to retain local variable declarations
			if (equalsAttributeName(sourceattribute, StandardDialect.PREFIX,
					StandardWithAttrProcessor.ATTR_NAME)) {
				String mergedwithvalue = sourceattribute.getValue();
				String targetwithvalue = getAttributeValue(targetelement,
						StandardDialect.PREFIX,
						StandardWithAttrProcessor.ATTR_NAME);
				if (targetwithvalue != null) {
					mergedwithvalue += "," + targetwithvalue;
				}
				targetelement.setAttribute(StandardDialect.PREFIX + ":"
						+ StandardWithAttrProcessor.ATTR_NAME, mergedwithvalue);
				continue;
			}

			// Copy only attributes that already exist in the target element, or
			// copy any attributes
			if ((mergeonly && targetelement.hasAttribute(sourceattribute
					.getNormalizedName())) || !mergeonly) {
				targetelement.setAttribute(sourceattribute.getOriginalName(),
						sourceattribute.getValue());
			}
		}
	}

	/**
	 * Replace the content of the target element, with the content of the source
	 * element.
	 * 
	 * @param targetelement
	 * @param sourceelement
	 */
	public static void pullContent(Element targetelement, Element sourceelement) {

		// Clone target element without processing information to make Thymeleaf
		// reprocesses it
		// targetelement.clearChildren();
		targetelement.addChild(sourceelement.cloneNode(null, false));
		// targetelement.getParent().extractChild(targetelement);
	}

	public static void addToHtmlHead(Element targetelement,
			Element sourceelement) {
		// Element html = targetelement.ge
	}

	/**
	 * Removes an attribute from an element. Removes both the prefixed and data-
	 * variations of the attribute.
	 * 
	 * @param element
	 * @param prefix
	 * @param name
	 */
	public static void removeAttribute(Element element, String prefix,
			String name) {

		element.removeAttribute(prefix + ":" + name);
		element.removeAttribute("data-" + prefix + "-" + name);
	}
}
