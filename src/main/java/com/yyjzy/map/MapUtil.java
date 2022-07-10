package com.yyjzy.map;

import java.util.Map;

public class MapUtil {

    public static boolean isNotEmpty(Map<?, ?> map) {
        return null != map && !map.isEmpty();
    }
}
