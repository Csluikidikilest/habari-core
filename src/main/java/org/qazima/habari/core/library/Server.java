package org.qazima.habari.core.library;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsServer;
import org.qazima.habari.pluginsystem.extension.NodeExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Server {
    private final List<String> certificatesUrls = new ArrayList<>();
    private String configurationUri = "/config";
    private String host = "127.0.0.1";
    @JsonIgnoreProperties
    private HttpServer httpServer;
    @JsonIgnoreProperties
    private HttpsServer httpsServer;
    private String keyManagerProtocol = "";
    private String keyPassword = "";
    private String metadataUri = "/meta";
    private int port = 4792;
    private boolean secured = false;
    private String sslProtocol = "";
    private String storePassword = "";
    private String storeProtocol = "";
    private String trustManagerProtocol = "";
    private String uri = "/";

    public List<String> getCertificatesUrls() {
        return certificatesUrls;
    }

    public String getConfigurationUri() {
        return configurationUri;
    }

    private void setConfigurationUri(String configurationUri) {
        this.configurationUri = configurationUri;
    }

    public String getHost() {
        return host;
    }

    private void setHost(String host) {
        this.host = host;
    }

    public HttpServer getHttpServer() {
        return httpServer;
    }

    public HttpsServer getHttpsServer() {
        return httpsServer;
    }

    public String getKeyManagerProtocol() {
        return keyManagerProtocol;
    }

    public void setKeyManagerProtocol(String keyManagerProtocol) {
        this.keyManagerProtocol = keyManagerProtocol;
    }

    public String getKeyPassword() {
        return keyPassword;
    }

    public void setKeyPassword(String keyPassword) {
        this.keyPassword = keyPassword;
    }

    public String getMetadataUri() {
        return metadataUri;
    }

    private void setMetadataUri(String metadataUri) {
        this.metadataUri = metadataUri;
    }

    public int getPort() {
        return port;
    }

    private void setPort(int port) {
        this.port = port;
    }

    public boolean isSecured() {
        return secured;
    }

    private void setSecured(boolean secured) {
        this.secured = secured;
    }

    public String getSslProtocol() {
        return sslProtocol;
    }

    private void setSslProtocol(String sslProtocol) {
        this.sslProtocol = sslProtocol;
    }

    public String getStorePassword() {
        return storePassword;
    }

    private void setStorePassword(String storePassword) {
        this.storePassword = storePassword;
    }

    public String getStoreProtocol() {
        return storeProtocol;
    }

    private void setStoreProtocol(String storeProtocol) {
        this.storeProtocol = storeProtocol;
    }

    public String getTrustManagerProtocol() {
        return trustManagerProtocol;
    }

    private void setTrustManagerProtocol(String trustManagerProtocol) {
        this.trustManagerProtocol = trustManagerProtocol;
    }

    public String getUri() {
        return uri;
    }

    private void setUri(String uri) {
        this.uri = uri;
    }

    public void load(JsonNode node) {
        Iterator<JsonNode> certificatesUrlsIterator = NodeExtension.getElements(node, "certificatesUrls");
        while (certificatesUrlsIterator.hasNext()) {
            JsonNode certificatesUrlNode = certificatesUrlsIterator.next();
            getCertificatesUrls().add(certificatesUrlNode.asText());
        }
        setConfigurationUri(NodeExtension.get(node,"configurationUri", "/configuration"));
        setKeyManagerProtocol(NodeExtension.get(node,"keyManagerProtocol",""));
        setKeyPassword(NodeExtension.get(node,"keyPassword",""));
        setMetadataUri(NodeExtension.get(node,"metadataUri","/metadata"));
        setHost(NodeExtension.get(node,"host","127.0.0.1"));
        setPort(NodeExtension.get(node,"port",9000));
        setSecured(NodeExtension.get(node,"secured",false));
        setSslProtocol(NodeExtension.get(node,"sslProtocol",""));
        setStorePassword(NodeExtension.get(node,"storePassword",""));
        setStoreProtocol(NodeExtension.get(node,"storeProtocol",""));
        setTrustManagerProtocol(NodeExtension.get(node,"trustManagerProtocol",""));
        setUri(NodeExtension.get(node,"uri","/"));
    }
}
