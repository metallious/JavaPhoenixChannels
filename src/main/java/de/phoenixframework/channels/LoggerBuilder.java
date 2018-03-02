package de.phoenixframework.channels;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ahmed on 12/18/17.
 */

public class LoggerBuilder {

    private static final Level DEFAULT_LOGGING_LEVEL = Level.ALL;

    private String tag = "print";
    private Level level = DEFAULT_LOGGING_LEVEL;

    public LoggerBuilder setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public LoggerBuilder setLevel(Level level) {
        this.level = level;
        return this;
    }

    public Logger build() {
        Logger logger = Logger.getLogger(tag);
        logger.setLevel(level);
        return logger;
    }

}
