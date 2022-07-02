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

    public ContentManager(Configuration configuration){
        this.configuration = configuration;
        for (Server server :
                this.configuration.getServers()) {
            server.getListener().createContext(server.getUri(), this);
        }
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
