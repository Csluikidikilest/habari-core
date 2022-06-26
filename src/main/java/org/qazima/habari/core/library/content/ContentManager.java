package org.qazima.habari.core.library.content;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.http.HttpStatus;
import org.qazima.habari.core.library.configuration.Configuration;
import org.qazima.habari.core.library.configuration.Server;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ContentManager implements HttpHandler {
    private Configuration configuration;
    private Server server;

    public ContentManager(Configuration configuration, Server server){
        this.configuration = configuration;
        this.server = server;
    }

    public void createContent(String path) {
        this.server.getListener().createContext(path, this);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "Response";
        exchange.sendResponseHeaders(HttpStatus.SC_OK, response.length());
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response.getBytes(StandardCharsets.UTF_8));
        outputStream.close();
    }
}
