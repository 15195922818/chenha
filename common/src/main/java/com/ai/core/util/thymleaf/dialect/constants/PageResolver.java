package com.ai.core.util.thymleaf.dialect.constants;

import java.util.Map;

@SuppressWarnings("rawtypes")
public class PageResolver {

	private static ThreadLocal pageholder = new ThreadLocal();

	public static void setPage(Map<String, Object> page) {
		if (!page.equals(pageholder.get())) {
			pageholder.set(page);
		}
	}

	public static Map<String, Object> getPage() {
		return (Map<String, Object>) pageholder.get();
	}

	public static void clearContext() {
		pageholder.remove();
	}
}
