package org.example.error;

import org.eclipse.jetty.server.handler.ErrorHandler;
import org.example.utils.GsonBuilder;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;


public class CustomErrorHandler extends ErrorHandler {

    @Override
    protected void handleErrorPage(HttpServletRequest request, Writer writer, int code, String message) throws IOException {

        Throwable exception = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        String detailedMessage = (exception != null && exception.getMessage() != null)
                ? exception.getMessage()
                : message;

        writer.write(GsonBuilder.getGjon().toJson(Map.of(
                "message", detailedMessage,
                "status", code
        )));
    }

}