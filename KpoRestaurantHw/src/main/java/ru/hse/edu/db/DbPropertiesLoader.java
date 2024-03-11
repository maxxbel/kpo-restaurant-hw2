package ru.hse.edu.db;

import ru.hse.edu.consoleui.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DbPropertiesLoader {
    private static final String PROPERTIES_ERROR = "Не удалось загрузить настройки программы.";
    static {
        loadProperties();
    }
    private static Properties properties;

    private static void loadProperties() {
        properties = new Properties();
        try {
            InputStream stream = new FileInputStream("src/main/resources/application.properties");
            properties.load(stream);
        } catch (IOException e) {
            Logger.log(PROPERTIES_ERROR);
            Logger.log(e.getMessage());
        }
    }
    public static String get(String key) {
        return properties.getProperty(key);
    }
}
