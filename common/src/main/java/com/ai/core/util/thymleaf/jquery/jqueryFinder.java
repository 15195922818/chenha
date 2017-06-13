package com.ai.core.util.thymleaf.jquery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.thymeleaf.dom.Element;

import com.ai.core.util.thymleaf.jquery.utils.Expression;

public class jqueryFinder {

	public static List<Element> find(String exp, Element e) {
		return find(new Expression(exp), e);
	}

	private static List<Element> find(Expression exp, Element e) {
		List<Element> result = Finder(translateExp(exp.getExp()), e,
				exp.isJustChild());
		if (result.size() > 0 && exp.getExpression() != null) {
			List<Element> nextResult = new ArrayList<Element>();
			for (Element el : result) {
				nextResult.addAll(find(exp.getExpression(), el));
			}
			return nextResult;
		}
		return result;
	}

	private static List<String> translateExp(String exp) {
		Pattern p = Pattern.compile("\\[(.*?)\\]");
		Matcher m = p.matcher(exp);
		List<String> el = new ArrayList<String>();
		while (m.find()) {
			el.add(m.group());
		}
		exp = exp.replaceAll("\\[(.*?)\\]", "");// .replaceAll(".", " .");
		if (exp.startsWith("\\.")) {
			exp = "[class^'" + exp.substring(1) + "']";
			el.add(0, exp);
		} else {
			String[] arr = exp.split("\\.");
			// System.out.println(arr);
			for (int i = 1; i < arr.length; i++) {
				arr[i] = "[class^'" + arr[i] + "']";
			}
			el.addAll(0, Arrays.asList(arr));
		}
		return el;
	}

	public static List<Element> Finder(List<String> el, Element parent,
			boolean justChild) {
		return enums(el, parent, justChild);
	}

	private static List<Element> enums(List<String> el, Element parent,
			boolean justChild) {
		List<Element> children = parent.getElementChildren();
		List<Element> found = new ArrayList<Element>();
		for (Element e : children) {
			boolean flag = true;
			for (String exp : el) {
				if (!calucate(e, exp)) {
					flag = false;
					break;
				}
			}
			if (flag) {
				found.add(e);
			}
		}
		if (!justChild) {
			for (Element e : children) {
				if (e.getElementChildren().size() > 0) {
					found.addAll(enums(el, e, justChild));
				}
			}
		}
		return found;
	}

	private static boolean calucate(Element e, String exp) {
		Pattern p = Pattern.compile("\\[(.*?)\\]");
		if (p.matcher(exp).find()) {
			// System.out.println("<"+exp+">---attr");
			return AttrCalucate(e, exp);
		} else {
			// System.out.println("<"+exp+">---tag");
			return TagCalucate(e, exp);
		}
	}

	private static boolean TagCalucate(Element e, String exp) {
		/*
		 * System.out.println( "[\r\n    TagName:"+e.getOriginalName()+"\r\n"
		 * +"    Exp:"+exp+"\r\n" +"    result"+
		 * e.getOriginalName().toLowerCase().equals(exp.toLowerCase())+ "\r\n]"
		 * );
		 */
		return e.getOriginalName().toLowerCase().equals(exp.toLowerCase());
	}

	private static boolean AttrCalucate(Element e, String exp) {
		Pattern equal = Pattern.compile("\\[(.*?)='(.*?)'\\]");
		Pattern unequal = Pattern.compile("\\[(.*?)!='(.*?)'\\]");
		Pattern include = Pattern.compile("\\[(.*?)\\^'(.*?)'\\]");
		Pattern uninclude = Pattern.compile("\\[(.*?)!\\^'(.*?)'\\]");
		if (unequal.matcher(exp).find()) {
			Matcher m = Pattern.compile("\\[(.*?)!=").matcher(exp);
			m.find();
			String attrName = m.group();
			attrName = attrName.substring(1, attrName.length() - 2);
			Matcher m2 = Pattern.compile("(!)='(.*?)'\\]").matcher(exp);
			m2.find();
			String attrValue = m2.group();
			attrValue = attrValue.substring(3, attrValue.length() - 2);
			// System.out.println(attrName+"!=__"+attrValue+"__");
			return !getAttr(e, attrName).equals(attrValue);
		}
		if (equal.matcher(exp).find()) {
			Matcher m = Pattern.compile("\\[(.*?)=").matcher(exp);
			m.find();
			String attrName = m.group();
			attrName = attrName.substring(1, attrName.length() - 1);
			Matcher m2 = Pattern.compile("='(.*?)'\\]").matcher(exp);
			m2.find();
			String attrValue = m2.group();
			attrValue = attrValue.substring(2, attrValue.length() - 2);
			// System.out.println(attrName+"=___"+attrValue+"__");
			return getAttr(e, attrName).equals(attrValue);
		}
		if (uninclude.matcher(exp).find()) {
			Matcher m = Pattern.compile("\\[(.*?)!\\^").matcher(exp);
			m.find();
			String attrName = m.group();// Pattern.compile("\\[(.*?)!\\^").matcher(exp).group();
			attrName = attrName.substring(1, attrName.length() - 2);
			Matcher m2 = Pattern.compile("!\\^'(.*?)'\\]").matcher(exp);
			m2.find();
			String attrValue = m2.group();// Pattern.compile("!\\^'(.*?)'\\]").matcher(exp).group();
			attrValue = attrValue.substring(3, attrValue.length() - 2);
			// System.out.println(attrName+"!^___"+attrValue+"__");
			return getAttr(e, attrName).indexOf(attrValue) == -1;
		}
		if (include.matcher(exp).find()) {
			Matcher m = Pattern.compile("\\[(.*?)\\^").matcher(exp);
			m.find();
			String attrName = m.group();// Pattern.compile("\\[(.*?)\\^").matcher(exp).group();
			attrName = attrName.substring(1, attrName.length() - 1);
			Matcher m2 = Pattern.compile("\\^'(.*?)'\\]").matcher(exp);
			m2.find();
			String attrValue = m2.group();// Pattern.compile("\\^'(.*?)'\\]").matcher(exp).group();
			attrValue = attrValue.substring(2, attrValue.length() - 2);
			// System.out.println(attrName+"^___"+attrValue+"__");
			return getAttr(e, attrName).indexOf(attrValue) > -1;
		}
		return false;
	}

	static String getAttr(Element e, String attrName) {
		return e.getAttributeValue(attrName) == null ? "" : e
				.getAttributeValue(attrName);
	}

}
