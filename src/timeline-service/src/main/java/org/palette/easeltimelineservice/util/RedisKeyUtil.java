package org.palette.easeltimelineservice.util;

import java.util.List;

public class RedisKeyUtil {

    private RedisKeyUtil() {
    }

    public static String constructKey(String prefix, Long id) {
        return prefix + ":" + id;
    }

    public static String constructKey(String prefix, String metric, Long id) {
        return prefix + ":" + metric + ":" + id;
    }

    public static List<String> constructKeys(final String prefix, final List<Long> ids) {
        return ids.stream()
                .map(paintId -> constructKey(prefix, paintId))
                .toList();
    }
}
