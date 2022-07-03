package org.qazima.habari.core.library.logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.qazima.habari.core.App;

public class LoggerManager {
    private static final Logger logger = LogManager.getLogger(App.class);

    public static Logger getLogger() {return logger;}
}
