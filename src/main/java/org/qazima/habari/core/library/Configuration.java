package org.qazima.habari.core.library;

import com.fasterxml.jackson.databind.JsonNode;
import org.qazima.habari.pluginsystem.extension.NodeExtension;
import org.qazima.habari.pluginsystem.interfaces.IPlugin;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Configuration {
    private final List<AvailableConnection> availableConnections = new ArrayList<>();
    private final List<IPlugin> connections = new ArrayList<>();
    private final List<Server> servers = new ArrayList<>();
    private int defaultPageSize = 50;
    private boolean deleteAllowed = false;
    private boolean getAllowed = true;
    private boolean postAllowed = false;
    private boolean putAllowed = false;

    public void load(JsonNode node) {
        setDefaultPageSize(NodeExtension.get(node,"defaultPageSize",0));
        setDeleteAllowed(NodeExtension.get(node,"allowDelete",false));
        setGetAllowed(NodeExtension.get(node,"allowGet",false));
        setPostAllowed(NodeExtension.get(node,"allowPost",false));
        setPutAllowed(NodeExtension.get(node,"allowPut",false));
        Iterator<JsonNode> availableConnectionsIterator = NodeExtension.getElements(node,"availableConnections");
        while (availableConnectionsIterator.hasNext()) {
            JsonNode availableConnectionNode = availableConnectionsIterator.next();
            AvailableConnection availableConnection = new AvailableConnection();
            availableConnection.load(availableConnectionNode);
            getAvailableConnections().add(availableConnection);
        }
        Iterator<JsonNode> serversIterator = NodeExtension.getElements(node,"servers");
        while (serversIterator.hasNext()) {
            JsonNode serverNode = serversIterator.next();
            Server server = new Server();
            server.load(serverNode);
            getServers().add(server);
        }
    }

    public Class<IPlugin> LoadPlugin(AvailableConnection availableConnection) throws MalformedURLException, ClassNotFoundException {
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[] { new URL(availableConnection.getUrl())}, this.getClass().getClassLoader());
        return (Class<IPlugin>) Class.forName(availableConnection.getClassName(), true, urlClassLoader);
    }

    public List<AvailableConnection> getAvailableConnections() {
        return availableConnections;
    }

    public List<IPlugin> getConnections() {
        return connections;
    }

    public int getDefaultPageSize() {
        return defaultPageSize;
    }

    private void setDefaultPageSize(int defaultPageSize) {
        this.defaultPageSize = defaultPageSize;
    }

    public boolean isDeleteAllowed() {
        return deleteAllowed;
    }

    private void setDeleteAllowed(boolean deleteAllowed) {
        this.deleteAllowed = deleteAllowed;
    }

    public boolean isGetAllowed() {
        return getAllowed;
    }

    private void setGetAllowed(boolean getAllowed) {
        this.getAllowed = getAllowed;
    }

    public boolean isPostAllowed() {
        return postAllowed;
    }

    private void setPostAllowed(boolean postAllowed) {
        this.postAllowed = postAllowed;
    }

    public boolean isPutAllowed() {
        return putAllowed;
    }

    private void setPutAllowed(boolean putAllowed) {
        this.putAllowed = putAllowed;
    }

    public List<Server> getServers() {
        return servers;
    }
}
