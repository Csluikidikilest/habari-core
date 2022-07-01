package org.qazima.habari.core.library.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.qazima.habari.pluginsystem.extension.NodeExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationTest {

    @Test
    @DisplayName("Load configurations with no one")
    void loadEmpty() {
        String json = "{\"configurations\": []}";
        ObjectMapper mapper = new ObjectMapper();
        List<Configuration> configurations = new ArrayList<>();
        try {
            JsonNode node = mapper.readTree(json);
            Iterator<JsonNode> configurationsIterator = NodeExtension.getElements(node,"configurations");
            while (configurationsIterator.hasNext()) {
                JsonNode configurationNode = configurationsIterator.next();
                Configuration configuration = new Configuration();
                configuration.load(configurationNode);
                configurations.add(configuration);
            }
            assertEquals(0, configurations.size());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    @DisplayName("Load configurations with one but without attributes")
    void loadDefaultConfiguration() {
        String json = "{\"configurations\": [{}]}";
        ObjectMapper mapper = new ObjectMapper();
        List<Configuration> configurations = new ArrayList<>();
        Configuration item = null;
        try {
            JsonNode node = mapper.readTree(json);
            Iterator<JsonNode> configurationsIterator = NodeExtension.getElements(node,"configurations");
            while (configurationsIterator.hasNext()) {
                JsonNode configurationNode = configurationsIterator.next();
                Configuration configuration = new Configuration();
                configuration.load(configurationNode);
                configurations.add(configuration);
            }
            if(configurations.stream().findFirst().isPresent()) {
                item = configurations.stream().findFirst().get();
            }
            Configuration finalItem = item;
            assertAll(
                    () -> assertEquals(1, configurations.size()),
                    () -> assertTrue(finalItem.isGetAllowed()),
                    () -> assertFalse(finalItem.isDeleteAllowed()),
                    () -> assertFalse(finalItem.isPostAllowed()),
                    () -> assertFalse(finalItem.isPutAllowed()),
                    () -> assertEquals(0, finalItem.getDefaultPageSize()),
                    () -> assertEquals(0, finalItem.getAvailableConnections().size())
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}