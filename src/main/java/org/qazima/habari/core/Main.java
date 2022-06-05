package org.qazima.habari.core;

import org.qazima.habari.core.library.ConfigurationManager;

import java.io.IOException;

public class Main {
     public static void main(String[] args) {
        try {
            ConfigurationManager.getInstance().LoadConfiguration("config.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
/**
        // start http server
        SimpleHttpServer httpServer = new SimpleHttpServer();
        httpServer.Start(port);

        // start https server
        SimpleHttpsServer httpsServer = new SimpleHttpsServer();
        httpsServer.Start(port);

        System.out.println(System.getProperty("user.dir"));
        System.out.println(Main.class.getClassLoader().getResource("").getPath());
**/
    }
}