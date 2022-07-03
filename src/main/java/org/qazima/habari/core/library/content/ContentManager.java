package org.qazima.habari.core.library.content;

import org.qazima.habari.core.library.configuration.Configuration;
import org.qazima.habari.core.library.configuration.Server;

public class ContentManager {
    public ContentManager(Configuration configuration) {
        for (Server server :
                configuration.getServers()) {
            server.getListener().createContext(server.getUri(), new UriContext(configuration));
        }
    }
}
