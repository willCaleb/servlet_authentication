package org.example.servlet;

import com.google.gson.Gson;
import com.google.gson.Strictness;
import org.example.pattern.Constants;
import org.example.utils.IgnoreFieldExclusionStrategy;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Modifier;

public abstract class AbstractServlet extends HttpServlet {

    public String getJsonString(HttpServletRequest request) throws IOException {
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            jsonBuilder.append(line);
        }
        String jsonRequest = jsonBuilder.toString();
        return jsonRequest;
    }

    public Gson getGjon() {
        return new com.google.gson.GsonBuilder()
                .disableHtmlEscaping()
                .excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT, Modifier.FINAL)
                .setExclusionStrategies(new IgnoreFieldExclusionStrategy())
                .setDateFormat(Constants.GSON_DATE_FORMAT)
                .setStrictness(Strictness.LENIENT)
                .create();
    }

    public void write(HttpServletResponse response, String value) {
        try {
            response.getWriter().write(value);
        }catch (Exception e) {
            throw new RuntimeException("Erro ao retornar dados");
        }
    }
}
