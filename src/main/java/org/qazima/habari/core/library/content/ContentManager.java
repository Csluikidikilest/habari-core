package org.qazima.habari.core.library.content;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.http.HttpStatus;
import org.qazima.habari.core.library.configuration.Configuration;
import org.qazima.habari.core.library.configuration.Server;
import org.qazima.habari.core.library.logger.LoggerManager;
import org.qazima.habari.pluginsystem.interfaces.IPlugin;
import org.qazima.habari.pluginsystem.library.Content;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

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
        String requestUri = exchange.getRequestURI().getPath();
        List<IPlugin> iPlugins = configuration.getConnections().stream().filter(item -> Pattern.compile(item.getConfiguration().getUri()).matcher(requestUri).matches()).toList();
        Content content = new Content();
        int contentResult = HttpStatus.SC_NOT_FOUND;
        for (IPlugin iPlugin :
                iPlugins) {
            contentResult = iPlugin.process(exchange, content);
        }
        byte[] response = content.getBody();
        exchange.sendResponseHeaders(contentResult, response.length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response);
        outputStream.close();
    }
}
