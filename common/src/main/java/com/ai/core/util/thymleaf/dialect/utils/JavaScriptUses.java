package com.ai.core.util.thymleaf.dialect.utils;

import org.thymeleaf.dom.Comment;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;

/**
 * @author zhoupeng
 * 
 */
public class JavaScriptUses {

	private String href;
	private String src;
	private String type;
	private String description;
	private String TagName;
	private Integer lt;
	private Integer gt;

	public JavaScriptUses() {
	}

	public JavaScriptUses(String ref, String src, String type,
			String description, String tagName) {
		super();
		this.href = ref;
		this.src = src;
		this.type = type;
		this.description = description;
		TagName = tagName;
	}

	public JavaScriptUses(String href, String src, String type,
			String description, String tagName, Integer lt, Integer gt) {
		super();
		this.href = href;
		this.src = src;
		this.type = type;
		this.description = description;
		TagName = tagName;
		this.lt = lt;
		this.gt = gt;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public Integer getLt() {
		return lt;
	}

	public void setLt(Integer lt) {
		this.lt = lt;
	}

	public Integer getGt() {
		return gt;
	}

	public void setGt(Integer gt) {
		this.gt = gt;
	}

	public String getRef() {
		return href;
	}

	public void setRef(String ref) {
		this.href = ref;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTagName() {
		return TagName;
	}

	public void setTagName(String tagName) {
		TagName = tagName;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public void setPrototype(String TagName, String type, String ref) {
		this.TagName = TagName;
		this.type = type;
		if (TagName.toLowerCase().equals("script")) {
			this.src = ref;
		} else {
			this.href = ref;
		}
	}

	public void setProperties(Element e) {
		// e.setAttribute("description", this.description);
		e.setAttribute("type", this.type);
		if (this.TagName.toLowerCase().equals("script")) {
			e.setAttribute("src", this.src);
		} else {
			e.setAttribute("href", this.href);
			e.setAttribute("rel", "stylesheet");
		}
	}

	@Override
	public String toString() {
		return "JavaScriptUses [href=" + href + ", src=" + src + ", type="
				+ type + ", description=" + description + ", TagName="
				+ TagName + ", lt=" + lt + ", gt=" + gt + "]";
	}

	public Node transToNode() {
		if (this.lt == null && this.gt == null) {
			Element e = new Element(this.TagName);
			setProperties(e);
			return e;
		} else {
			String htm = this.changeToStr();
			Integer version;
			String condition;
			if (this.lt != null) {
				condition = "lt";
				version = this.lt;
			} else {
				condition = "gt";
				version = this.gt;
			}
			Comment c = new Comment("[if " + condition + " IE " + version
					+ "]> " + htm + " <![endif]");
			return c;
		}
	}

	private String changeToStr() {
		if (this.TagName.toLowerCase().equals("script")) {
			return new StringBuilder().append("<script src='").append(this.src)
					.append("' ></script>").toString();
		} else {
			return new StringBuilder().append("<link rel='stylesheet' href='")
					.append(this.href).append("' ></link>").toString();
		}
	}

}
