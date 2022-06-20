package org.qazima.habari.core.library;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.qazima.habari.pluginsystem.extension.NodeExtension;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ConfigurationManager {
    private final Parameter parameter = new Parameter();
    public List<Configuration> configurations() { return parameter.configurations(); }

    private ConfigurationManager() { }

    public void LoadConfiguration(String configurationFileName) throws IOException {
        JsonFactory factory = new JsonFactory();
        //parser.nextToken();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(new File(configurationFileName));
        Iterator<JsonNode> configurationsIterator = NodeExtension.getElements(node,"configurations");
        while (configurationsIterator.hasNext()) {
            JsonNode configurationNode = configurationsIterator.next();
            Configuration configuration = new Configuration();
            configuration.load(configurationNode);
            configurations().add(configuration);
        }
    }

    private static class InnerCM{
        private static final ConfigurationManager INSTANCE = new ConfigurationManager();
    }

    public static ConfigurationManager getInstance() {
        return InnerCM.INSTANCE;
    }
}