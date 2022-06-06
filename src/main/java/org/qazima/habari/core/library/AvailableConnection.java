package org.qazima.habari.core.library;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.JsonNode;
import org.qazima.habari.pluginsystem.extension.NodeExtension;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class AvailableConnection {
    private String className;
    private String connectionType;
    private String url;

    public String getClassName() {
        return className;
    }

    private void setClassName(String className) {
        this.className = className;
    }

    public String getConnectionType() {
        return connectionType;
    }

    private void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public String getUrl() {
        return url;
    }

    private void setUrl(String url) {
        this.url = url;
    }

    public void load(JsonNode node) {
        setClassName(NodeExtension.get(node,"className", ""));
        setConnectionType(NodeExtension.get(node,"connectionType",""));
        setUrl(NodeExtension.get(node,"url",""));
    }
}
