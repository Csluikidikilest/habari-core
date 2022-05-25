package org.qazima.habari.core.library;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsServer;
import org.qazima.habari.core.library.Configuration;

public class Server {
    private Configuration configuration;
    private int port;
    private HttpServer httpServer;
    private HttpsServer httpsServer;
    private static String protocol = "TLS";
}
