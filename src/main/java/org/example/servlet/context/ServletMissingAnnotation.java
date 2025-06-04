package org.example.servlet.context;

import javax.servlet.http.HttpServlet;

@FunctionalInterface
public interface ServletMissingAnnotation {

    void validateHasAnnotationAndPath(HttpServlet httpServlet);

}
