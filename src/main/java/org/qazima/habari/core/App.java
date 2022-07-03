package org.qazima.habari.core;

import org.qazima.habari.core.library.configuration.ConfigurationManager;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class App {
     public static void main(String[] args) {
        ConfigurationManager.getInstance().loadConfiguration(args[0]);
        ConfigurationManager.getInstance().starts();
    }
}