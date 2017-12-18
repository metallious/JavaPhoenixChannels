package org.phoenixframework.channels;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ahmed on 12/18/17.
 */

public class LoggerBuilder {

    private String tag = "print";
    private Level level = Level.ALL;

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
