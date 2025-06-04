package org.example.utils;

import com.google.gson.Gson;
import com.google.gson.Strictness;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Modifier;

public class GsonBuilder {

    public static Gson getGjon() {
        return new com.google.gson.GsonBuilder()
                .disableHtmlEscaping()
                .excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT, Modifier.FINAL)
                .setExclusionStrategies(new IgnoreFieldExclusionStrategy())
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                .setStrictness(Strictness.LENIENT)
                .create();
    }

    private static String getJsonString(HttpServletRequest request) throws IOException {
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            jsonBuilder.append(line);
        }
        String jsonRequest = jsonBuilder.toString();
        return jsonRequest;
    }
}