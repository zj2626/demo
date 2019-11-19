package com.demo.common.service.util;

import com.google.common.collect.ImmutableMap;
import org.springframework.util.PropertyPlaceholderHelper;

/**
 * 占位符替换工具类
 */
public final class PlaceHolderUtils {

    /**
     * 解析占位符 只解析{}
     *
     * @param source 代替换的拥有占位符的字符串
     * @param map    占位符和替换值对应
     * @return
     */
    public static String resolverString(String source, ImmutableMap<String, String> map) {
        PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper("{", "}");
        return helper.replacePlaceholders(source, (a) -> map.getOrDefault(a, "{" + a + "}"));
    }

    /**
     * 适用于单个占位符的情况
     *
     * @param source
     * @param value
     * @return
     */
    public static String resolverString(String source, String value) {
        PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper("{", "}");
        return helper.replacePlaceholders(source, (a) -> value);
    }


}
