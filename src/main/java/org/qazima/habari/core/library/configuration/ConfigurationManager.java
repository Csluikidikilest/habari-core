package org.qazima.habari.core.library.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.qazima.habari.core.library.content.ContentManager;
import org.qazima.habari.core.library.logger.LoggerManager;
import org.qazima.habari.pluginsystem.extension.NodeExtension;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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

    public void loadConfiguration(String configurationFileName) {
        LoggerManager.getLogger().trace("Loading of ", configurationFileName, ": starting");
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(new File(configurationFileName));
            Iterator<JsonNode> configurationsIterator = NodeExtension.getElements(node, "configurations");
            while (configurationsIterator.hasNext()) {
                JsonNode configurationNode = configurationsIterator.next();
                Configuration configuration = new Configuration();
                configuration.load(configurationNode);
                configuration.configureServers();
                contentManagers.add(new ContentManager(configuration));
                getConfigurations().add(configuration);
            }
            LoggerManager.getLogger().trace("Loading of ", configurationFileName, ": ended");

        } catch (UnrecoverableKeyException | CertificateException | IOException | KeyStoreException |
                 NoSuchAlgorithmException | KeyManagementException | ClassNotFoundException |
                 InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            LoggerManager.getLogger().fatal(e);
        }
    }

    public void starts() {
        for (Configuration configuration : getConfigurations()) {
            try {
                configuration.startServers();
            } catch (IOException | UnrecoverableKeyException | CertificateException | KeyStoreException |
                     NoSuchAlgorithmException | KeyManagementException e) {
                LoggerManager.getLogger().fatal(e);
            }
        }
    }

    private static class InnerCM{
        private static final ConfigurationManager INSTANCE = new ConfigurationManager();
    }

    public static ConfigurationManager getInstance() {
        return InnerCM.INSTANCE;
    }
}
