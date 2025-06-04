package org.example.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ListUtils {

    public static boolean isEmpty(Collection<?> collection) {
        return collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isEmpty(Object[] collectionAsArray) {
        return isEmpty(Arrays.asList(collectionAsArray));
    }

    public static boolean isNotEmpty(Object[] collectionAsArrays) {
        return isNotEmpty(Arrays.asList(collectionAsArrays));
    }
}
