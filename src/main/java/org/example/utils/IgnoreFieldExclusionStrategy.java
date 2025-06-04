package org.example.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class IgnoreFieldExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        return false;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}