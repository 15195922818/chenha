package com.ai.core.util.utils;

import java.util.*;

/**
 * Created by shaoxy on 2014/9/25.
 */
public abstract class CheckNull {
    /**
     * 检查字符串是否为空 包括空字符串,去空格
     *
     * @param str
     * @return true:空 false：非空
     */
    public static boolean isNullTrim(String str) {
        return (null == str || "".trim().equals(str));
    }

    /**
     * 检查字符串是否为空 包括空字符串
     *
     * @param str
     * @return true:空 false：非空
     */
    public static boolean isNull(String str) {
        return (null == str || "".equals(str.trim()));
    }

    /**
     * 检查set是否为空 包括Collection的size为0
     *
     * @param collection
     * @return true:空 false：非空
     */
    public static boolean isNull(Collection<?> collection) {
        return null == collection || collection.isEmpty();
    }

    /**
     * 检查数组是否为空
     *
     * @param objs
     * @return true:空 false：非空
     */
    public static boolean isNull(Object[] objs) {
        if (null == objs || 0 == objs.length) {
            return true;
        } else {
            for (Object obj : objs) {
                if (null != obj && !"".equals(obj.toString().trim())) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * 检查map是否为空 包括map的size为0
     *
     * @param map
     * @return
     */
    @SuppressWarnings("unchecked")
    public static boolean isNull(Map map) {
        return null == map || map.isEmpty();
    }

    /**
     * 检查object是否为空
     *
     * @param obj
     * @return true 空
     */
    public static boolean isNull(Object obj) {
        return null == obj;
    }
}
