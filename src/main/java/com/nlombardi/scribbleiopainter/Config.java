package com.nlombardi.scribbleiopainter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

class Config {
    private Properties configFile;

    Config() {
        configFile = new java.util.Properties();
        try {
            configFile.load(new FileInputStream(new File("config.cfg")));
        } catch (IOException e) {
            System.err.println("Couldn't find 'config.cfg', create it and restart the app");
            System.exit(0);
        }
    }

    String getStringProperty(String key) {
        return this.configFile.getProperty(key);
    }

    int getIntProperty(String key) {
        return Integer.parseInt(this.configFile.getProperty(key));
    }
}
