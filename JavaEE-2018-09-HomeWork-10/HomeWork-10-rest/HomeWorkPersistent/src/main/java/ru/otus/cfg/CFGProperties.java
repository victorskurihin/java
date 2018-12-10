package ru.otus.cfg;

import ru.otus.utils.ResourceAsStream;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Singleton
@Startup
public class CFGProperties
{
    private Properties properties;

    @PostConstruct
    public void init()
    {
        properties = new Properties();

//        try (InputStream inputStream = this.getClass()
//                                           .getClassLoader()
//                                           .getResourceAsStream("cfg.properties"))
        try (InputStream inputStream = (new ResourceAsStream("cfg.properties")).get()) {
            System.out.println("InputStream is: " + inputStream);
            // Loading the properties
            properties.load(inputStream);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // Printing the properties
        System.out.println("Read Properties." + properties);
    }

    /**
     * Searches for the property with the specified key in this property list.
     * If the key is not found in this property list, the default property list,
     * and its defaults, recursively, are then checked. The method returns
     * {@code null} if the property is not found.
     *
     * @param key
     * @return
     */
    public String getProperty(String key)
    {
        return null != properties ? properties.getProperty(key) : null;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
