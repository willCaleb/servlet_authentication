package org.example.servlet.context;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.example.filter.AuthFilter;
import org.example.servlet.AbstractServlet;
import org.example.servlet.CreateUserServlet;
import org.example.utils.Utils;
import org.reflections.Reflections;

import javax.servlet.DispatcherType;
import javax.servlet.annotation.WebServlet;
import java.util.Arrays;
import java.util.EnumSet;

public class ServletContextResolver extends ServletContextHandler {

    public ServletContextResolver() {
        super(ServletContextHandler.SESSIONS);
    }

    public ServletContextResolver addAllServlet() {

        Reflections reflections = new Reflections(CreateUserServlet.class.getPackage().getName());

        setContextPath("/");

        reflections.getSubTypesOf(AbstractServlet.class)
                .stream()
                .filter(servlet -> servlet.isAnnotationPresent(WebServlet.class))
                .forEach(servlet -> {
                    String path = Arrays.stream(servlet.getAnnotation(WebServlet.class).urlPatterns()).findFirst().orElse(null);
                    if (Utils.isNotEmpty(path)) {

                        addServlet(servlet, path);

                    }
                });
        addFilter(AuthFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));

        return this;
    }

}
