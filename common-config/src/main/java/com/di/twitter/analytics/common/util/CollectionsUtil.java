package com.di.twitter.analytics.common.util;

import java.util.ArrayList;
import java.util.List;

public final class CollectionsUtil {

    private CollectionsUtil() {}

    public static <T> List<T> getListFromIterable(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }
}
