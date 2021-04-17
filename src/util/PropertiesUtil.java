package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();

    static {
        LoadProperties();
    }


    private PropertiesUtil(){
    }

    public static String get(String key){
        return PROPERTIES.getProperty(key);
    }

    private static void LoadProperties() {
        try (var resourceAsStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(resourceAsStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
