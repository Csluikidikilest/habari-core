package org.qazima.habari.core.library.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.qazima.habari.pluginsystem.extension.NodeExtension;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationTest {

    @Test
    @DisplayName("Load configurations without element")
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
        } catch (JsonProcessingException | MalformedURLException | ClassNotFoundException | InvocationTargetException |
                 NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Load configurations with one empty element")
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
                    () -> assertFalse(finalItem.isGetAllowed()),
                    () -> assertFalse(finalItem.isDeleteAllowed()),
                    () -> assertFalse(finalItem.isPostAllowed()),
                    () -> assertFalse(finalItem.isPutAllowed()),
                    () -> assertEquals(0, finalItem.getDefaultPageSize()),
                    () -> assertEquals(0, finalItem.getServers().size()),
                    () -> assertEquals(0, finalItem.getAvailableConnections().size()),
                    () -> assertEquals(0, finalItem.getConnections().size())
            );
        } catch (JsonProcessingException | MalformedURLException | ClassNotFoundException | InvocationTargetException |
                 NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Load configurations read only mode")
    void loadGetOnlyConfiguration() {
        String json = "{\"configurations\": [{\"allowGet\": true}]}";
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
                    () -> assertEquals(0, finalItem.getServers().size()),
                    () -> assertEquals(0, finalItem.getAvailableConnections().size()),
                    () -> assertEquals(0, finalItem.getConnections().size())
            );
        } catch (JsonProcessingException | MalformedURLException | ClassNotFoundException | InvocationTargetException |
                 NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Load configurations full access mode")
    void loadFullAccessConfiguration() {
        String json = "{\"configurations\": [{\"allowGet\": true, \"allowPost\": true, \"allowPut\": true, \"allowDelete\": true}]}";
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
                    () -> assertTrue(finalItem.isDeleteAllowed()),
                    () -> assertTrue(finalItem.isPostAllowed()),
                    () -> assertTrue(finalItem.isPutAllowed()),
                    () -> assertEquals(0, finalItem.getDefaultPageSize()),
                    () -> assertEquals(0, finalItem.getServers().size()),
                    () -> assertEquals(0, finalItem.getAvailableConnections().size()),
                    () -> assertEquals(0, finalItem.getConnections().size())
            );
        } catch (JsonProcessingException | MalformedURLException | ClassNotFoundException | InvocationTargetException |
                 NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Load configurations with one server")
    void loadOneServerConfiguration() {
        String json = "{\"configurations\": [{\"allowGet\": true, \"servers\": [{\"secured\": false,\"host\": \"127.0.0.1\",\"port\": 9000,\"configurationUri\": \"/config\",\"metadataUri\": \"/metadata\",\"uri\": \"/\"}]}]}";
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
                    () -> assertEquals(1, finalItem.getServers().size()),
                    () -> assertEquals(0, finalItem.getAvailableConnections().size()),
                    () -> assertEquals(0, finalItem.getConnections().size())
            );
        } catch (JsonProcessingException | MalformedURLException | ClassNotFoundException | InvocationTargetException |
                 NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}