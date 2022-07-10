package org.qazima.habari.core.library.content;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.http.HttpStatus;
import org.qazima.habari.core.library.configuration.Configuration;
import org.qazima.habari.pluginsystem.interfaces.IPlugin;
import org.qazima.habari.pluginsystem.library.Content;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.regex.Pattern;

public class ConfigurationContext implements HttpHandler {
    private final Configuration configuration;

    public ConfigurationContext(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestUri = exchange.getRequestURI().getPath();
        List<IPlugin> iPlugins = configuration.getConnections().stream().filter(item -> Pattern.compile(item.getConfiguration().getUri()).matcher(requestUri).matches()).toList();
        Content content = new Content();
        int contentResult = HttpStatus.SC_NOT_FOUND;
        for (IPlugin iPlugin :
                iPlugins) {
            contentResult = iPlugin.processConfigure(exchange, content);
        }
        byte[] response = content.getBody();
        exchange.sendResponseHeaders(contentResult, response.length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response);
        outputStream.close();
    }
}
