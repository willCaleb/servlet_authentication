package org.example.server;

import org.eclipse.jetty.server.Server;
import org.example.error.CustomErrorHandler;
import org.example.servlet.context.ServletContextResolver;

public class ServerStarter {

    public static void init() {
        try {
            Server server = new Server(7272);

            server.setHandler(new ServletContextResolver());

            server.setErrorHandler(new CustomErrorHandler());

            server.start();

            server.join();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
