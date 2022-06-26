package org.qazima.habari.core.library.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.qazima.habari.core.library.content.ContentManager;
import org.qazima.habari.pluginsystem.extension.NodeExtension;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.*;

public class ConfigurationManager {
    private final List<ContentManager> contentManagers = new ArrayList<>();
    private final Parameter parameter = new Parameter();
    public List<Configuration> getConfigurations() { return parameter.configurations(); }

    public List<ContentManager> getContentManagers() { return contentManagers; }

    private ConfigurationManager() { }

    public void loadConfiguration(String configurationFileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(new File(configurationFileName));
        Iterator<JsonNode> configurationsIterator = NodeExtension.getElements(node,"configurations");
        while (configurationsIterator.hasNext()) {
            JsonNode configurationNode = configurationsIterator.next();
            Configuration configuration = new Configuration();
            configuration.load(configurationNode);
            getConfigurations().add(configuration);
        }
    }

    public void starts() throws IOException, UnrecoverableKeyException, CertificateException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        for (Configuration configuration : getConfigurations()) {
            configuration.startServers();
        }
    }

    private static class InnerCM{
        private static final ConfigurationManager INSTANCE = new ConfigurationManager();
    }

    public static ConfigurationManager getInstance() {
        return InnerCM.INSTANCE;
    }
}
