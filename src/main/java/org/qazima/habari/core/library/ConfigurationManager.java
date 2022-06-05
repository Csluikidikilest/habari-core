package org.qazima.habari.core.library;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.qazima.habari.pluginsystem.interfaces.IPlugin;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.util.*;

public class ConfigurationManager {
    private final Map<String, Class<IPlugin>> _availablePluginsConnections = new HashMap<>();
    private Parameter parameter = new Parameter();
    public Map<String, Class<IPlugin>> availablePluginsConnections() { return _availablePluginsConnections; }
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
