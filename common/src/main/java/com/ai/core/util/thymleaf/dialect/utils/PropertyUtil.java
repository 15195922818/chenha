package com.ai.core.util.thymleaf.dialect.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.core.io.Resource;
import org.springframework.web.context.WebApplicationContext;

import com.ai.core.util.thymleaf.dialect.ComicPlusDialectUtils;

/**
 * @author zhoupeng
 * 
 */
public class PropertyUtil extends ApplicationObjectSupport {

	protected static Logger logger = Logger.getLogger(PropertyUtil.class);

	private Resource location;
	private List<Resource> locations;

	public PropertyUtil() {
	}

	public PropertyUtil(Resource location) {
		super();
		this.location = location;
		this.locations.add(location);
		// this.init();
	}

	public PropertyUtil(List<Resource> locations) {
		super();
		this.locations = locations;
		// this.init();
	}

	@SuppressWarnings({ "restriction", "unchecked" })
	@PostConstruct
	public void init() {
		String ContextPath = ((WebApplicationContext) getApplicationContext())
				.getServletContext().getContextPath();
		SAXBuilder builder = new SAXBuilder();
		try {
			for (int i = 0; i < this.locations.size(); i++) {
				logger.debug("Init ai:use Component form url: "
						+ this.locations.get(i).getURI());
				// Document doc = builder.build(new
				// File(((Resource)this.locations.get(i)).getURI()));
				Document doc = builder.build(this.locations.get(i)
						.getInputStream());
				Element rootEl = doc.getRootElement();

				// 获得所有子元素
				List<Element> beans = rootEl.getChildren("bean");
				Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();

				for (Element bean : beans) {
					Map<String, Object> beanMap = new HashMap<String, Object>();
					List<JavaScriptUses> uses = new ArrayList<JavaScriptUses>();
					Element script = bean.getChild("scripts");
					if (script != null && script.getChildren("use").size() > 0) {
						List<Element> scripts = script.getChildren("use");
						if (scripts.size() > 0) {
							uses.addAll(getUses(scripts, "script", ContextPath));
						}
					}
					Element link = bean.getChild("links");
					if (link != null && link.getChildren("use").size() > 0) {
						List<Element> links = link.getChildren("use");
						if (links.size() > 0) {
							uses.addAll(getUses(links, "link", ContextPath));
						}
					}
					Element dependsTag = bean.getChild("depends");
					if (dependsTag != null
							&& dependsTag.getChildren("depend").size() > 0) {
						List<Element> dependences = dependsTag
								.getChildren("depend");
						List<String> dependency = new ArrayList<String>();
						for (Element e : dependences) {
							dependency.add(e.getAttributeValue("ref"));
						}
						beanMap.put("depends", dependency);
					}
					beanMap.put("uses", uses);

					map.put(bean.getAttributeValue("id"), beanMap);
				}
				ComicPlusDialectUtils.config.putAll(map);// = map;
			}
			logger.debug("________________________________________________________________________________________________________");
			logger.debug("________________________________________________________________________________________________________");
			logger.debug(ComicPlusDialectUtils.config);
			logger.debug("success in init ai:used Component .....");
			logger.debug("________________________________________________________________________________________________________");
			logger.debug("________________________________________________________________________________________________________");
		} catch (JDOMException e) {
			logger.debug("error in init ai:used Component ..... JDOM :" + e);
			e.printStackTrace();

		} catch (IOException e) {
			logger.debug("error in init ai:used Component ..... IOException :"
					+ e);
			e.printStackTrace();

		}
	}

	public static List<JavaScriptUses> getUses(List<Element> beans,
			String TagName) {
		List<JavaScriptUses> uses = new ArrayList<JavaScriptUses>();
		String type = "";
		if (TagName.toLowerCase().equals("script")) {
			type = "text/javascript";
		} else {
			type = "text/css";
		}
		for (Element use : beans) {
			JavaScriptUses ju = new JavaScriptUses();
			ju.setPrototype(TagName, type, use.getAttributeValue("ref"));
			ju.setDescription(use.getAttributeValue("descripe"));
			if (null != use.getAttributeValue("lt")
					&& use.getAttributeValue("lt").length() > 0) {
				logger.debug("lt:" + use.getAttributeValue("lt").toString());
				ju.setLt(Integer
						.valueOf(use.getAttributeValue("lt").toString()));
			}
			if (null != use.getAttributeValue("gt")
					&& use.getAttributeValue("gt").length() > 0) {
				logger.debug("gt:" + use.getAttributeValue("gt").toString());
				ju.setGt(Integer
						.valueOf(use.getAttributeValue("gt").toString()));
			}
			uses.add(ju);
		}
		return uses;
	}

	public static List<JavaScriptUses> getUses(List<Element> beans,
			String TagName, String ContextPath) {
		List<JavaScriptUses> uses = new ArrayList<JavaScriptUses>();
		String type = "";
		if (TagName.toLowerCase().equals("script")) {
			type = "text/javascript";
		} else {
			type = "text/css";
		}
		for (Element use : beans) {
			JavaScriptUses ju = new JavaScriptUses();
			ju.setPrototype(TagName, type,
					ContextPath + use.getAttributeValue("ref"));
			ju.setDescription(use.getAttributeValue("descripe"));
			if (null != use.getAttributeValue("lt")
					&& use.getAttributeValue("lt").length() > 0) {
				logger.debug("lt:" + use.getAttributeValue("lt").toString());
				ju.setLt(Integer
						.valueOf(use.getAttributeValue("lt").toString()));
			}
			if (null != use.getAttributeValue("gt")
					&& use.getAttributeValue("gt").length() > 0) {
				logger.debug("gt:" + use.getAttributeValue("gt").toString());
				ju.setGt(Integer
						.valueOf(use.getAttributeValue("gt").toString()));
			}
			uses.add(ju);
		}
		return uses;
	}

	public Resource getLocation() {
		return location;
	}

	public void setLocation(Resource location) throws IOException {
		this.location = location;
		this.init();
	}

	public List<Resource> getLocations() {
		return locations;
	}

	public void setLocations(List<Resource> locations) {
		this.locations = locations;
	}

}
