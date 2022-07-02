package org.qazima.habari.core.library.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.net.httpserver.*;
import org.apache.http.HttpStatus;
import org.qazima.habari.pluginsystem.extension.NodeExtension;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Server {
    private final List<String> certificatesUrls = new ArrayList<>();
    private String configurationUri = "/configuration";
    private String host = "127.0.0.1";
    private HttpServer httpServer;
    private HttpsServer httpsServer;
    private String keyFile = "";
    private String keyManagerProtocol = "";
    private String keyPassword = "";
    private String metadataUri = "/metadata";
    private int port = 9000;
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

    public HttpServer getListener() {
        return isSecured() ? httpsServer : httpServer;
    }

    public String getKeyFile() {
        return keyFile;
    }

    public void setKeyFile(String keyFile) {
        this.keyFile = keyFile;
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

    public void configureListener() throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
        if(isSecured()) {
            FileInputStream fileInputStream = new FileInputStream(getKeyFile());
            KeyStore keyStore = KeyStore.getInstance(getStoreProtocol());
            keyStore.load(fileInputStream, getStorePassword().toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(getKeyManagerProtocol());
            keyManagerFactory.init(keyStore, getKeyPassword().toCharArray());
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(getTrustManagerProtocol());
            trustManagerFactory.init(keyStore);
            httpsServer = HttpsServer.create(new InetSocketAddress(getHost(), getPort()), 0);
            SSLContext sslContext = SSLContext.getInstance(getSslProtocol());
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
            httpsServer.setHttpsConfigurator(new HttpsConfigurator(sslContext) {
                public void configure(HttpsParameters httpsParameters) {
                    try {
                        SSLContext context = SSLContext.getDefault();
                        SSLEngine engine = context.createSSLEngine();
                        httpsParameters.setNeedClientAuth(false);
                        httpsParameters.setCipherSuites(engine.getEnabledCipherSuites());
                        httpsParameters.setProtocols(engine.getEnabledProtocols());
                        SSLParameters parameters = context.getDefaultSSLParameters();
                        httpsParameters.setSSLParameters(parameters);
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } else {
            httpServer = HttpServer.create(new InetSocketAddress(getHost(), getPort()), 0);
        }
    }

    public void load(JsonNode node) {
        Iterator<JsonNode> certificatesUrlsIterator = NodeExtension.getElements(node, "certificatesUrls");
        while (certificatesUrlsIterator.hasNext()) {
            JsonNode certificatesUrlNode = certificatesUrlsIterator.next();
            getCertificatesUrls().add(certificatesUrlNode.asText());
        }
        setConfigurationUri(NodeExtension.get(node,"configurationUri", "/configuration"));
        setKeyFile(NodeExtension.get(node, "keyFile", ""));
        setKeyManagerProtocol(NodeExtension.get(node,"keyManagerProtocol",""));
        setKeyPassword(NodeExtension.get(node,"keyPassword",""));
        setMetadataUri(NodeExtension.get(node,"metadataUri","/metadata"));
        setHost(NodeExtension.get(node,"host","127.0.0.1"));
        setPort(NodeExtension.get(node,"port",8080));
        setSecured(NodeExtension.get(node,"secured",false));
        setSslProtocol(NodeExtension.get(node,"sslProtocol",""));
        setStorePassword(NodeExtension.get(node,"storePassword",""));
        setStoreProtocol(NodeExtension.get(node,"storeProtocol",""));
        setTrustManagerProtocol(NodeExtension.get(node,"trustManagerProtocol",""));
        setUri(NodeExtension.get(node,"uri","/"));
    }

    public void startListener() {
        getListener().setExecutor(null);
        getListener().start();
    }
}
