package org.example.servlet.context;

import org.example.annotation.NoAuth;
import org.example.enums.EnumException;
import org.example.servlet.AbstractServlet;
import org.example.servlet.CreateUserServlet;
import org.example.utils.ListUtils;
import org.example.utils.Utils;
import org.reflections.Reflections;

import javax.servlet.annotation.WebServlet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NoAuthServletContextResolver {

    public static List<String> getNoAuthPaths() {
        List<String> noAuthPaths = new ArrayList<>();

        Reflections reflections = new Reflections(CreateUserServlet.class.getPackage().getName());

        validateAllHaveAnnotationAndPath(reflections);

        reflections.getSubTypesOf(AbstractServlet.class)
                .stream()
                .filter(servlet -> servlet.isAnnotationPresent(NoAuth.class))
                .filter(servlet -> servlet.isAnnotationPresent(WebServlet.class))
                .forEach(servlet -> {
                    String path = Arrays.stream(servlet.getAnnotation(WebServlet.class).urlPatterns()).findFirst().orElse(null);
                    if (Utils.isNotEmpty(path)) {
                        noAuthPaths.add(path);
                    }
                });
        return noAuthPaths;
    }

    private static void validateAllHaveAnnotationAndPath(Reflections reflections) {
        reflections.getSubTypesOf(AbstractServlet.class)
                .forEach(servlet -> {
                    if (!servlet.isAnnotationPresent(WebServlet.class)) {
                        throw new RuntimeException(EnumException.WEB_SERVLET_MISSING_ANNOTATION.getMessage());
                    }
                    if (ListUtils.isEmpty(servlet.getAnnotation(WebServlet.class).urlPatterns())) {
                        throw new RuntimeException(EnumException.WEB_SERVLET_MISSING_PATH.getMessage());
                    }
                });
    }


}
