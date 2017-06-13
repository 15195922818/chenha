package com.ai.core.util.thymleaf.jquery.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expression {

	private String exp;
	private boolean justChild = false;
	private boolean isEnd = false;
	private Expression expression = null;

	public Expression() {
	}

	public Expression(String el) {
		this.constract(el, false);
	}

	public Expression(String el, boolean justChild) {
		this.constract(el, justChild);
	}

	public void constract(String el, boolean justChild) {
		el = el.trim();// .replaceAll("\\s+", " ");
		Pattern p = Pattern.compile("(\\s|>)");
		Matcher m = p.matcher(el);
		if (m.find()) {
			this.exp = el.substring(0, m.start());
			this.justChild = justChild;
			String nextExpression = el.substring(m.start() + 1);
			if (nextExpression.length() > 0) {
				this.expression = new Expression(nextExpression, m.group()
						.equals(">"));
			}
		} else {
			this.exp = el;
			this.justChild = justChild;
			this.isEnd = true;
		}
	}

	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public boolean isJustChild() {
		return justChild;
	}

	public void setJustChild(boolean justChild) {
		this.justChild = justChild;
	}

	public boolean isEnd() {
		return isEnd;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	@Override
	public String toString() {
		return "Expression [exp=" + exp + ", justChild=" + justChild
				+ ", isEnd=" + isEnd + ", \r\n expression=" + expression
				+ "\r\n]";
	}

	public static void main(String[] arg) {
		Expression exp = new Expression("head>script[description='']>body div>");
		System.out.println(exp);
	}
}
