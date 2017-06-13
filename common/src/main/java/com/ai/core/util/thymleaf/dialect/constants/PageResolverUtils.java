package com.ai.core.util.thymleaf.dialect.constants;

import java.util.HashMap;
import java.util.Map;

public class PageResolverUtils {

	public static Object getResolver(String resolverType) {
		Map<String, Object> map = PageResolver.getPage();
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		if (!map.keySet().contains(resolverType)) {
			map.put(resolverType, null);
		}
		return map.get(resolverType);

	}

	public static void setResolver(String resolverType, Object obj) {
		Map<String, Object> map = PageResolver.getPage();
		if (map == null) {
			// PageResolver.setPage(new HashMap<String, Object>());
			map = new HashMap<String, Object>();
		}
		map.put(resolverType, obj);
		PageResolver.setPage(map);
	}
}
