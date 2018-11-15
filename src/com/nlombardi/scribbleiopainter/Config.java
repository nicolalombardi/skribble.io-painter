package com.nlombardi.scribbleiopainter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config
{
    Properties configFile;
    public Config()
    {
        configFile = new java.util.Properties();
        try {
            configFile.load(new FileInputStream(new File("config.cfg")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getStringProperty(String key){
        return this.configFile.getProperty(key);
    }

    public int getIntProperty(String key){
        return Integer.parseInt(this.configFile.getProperty(key));
    }
}
