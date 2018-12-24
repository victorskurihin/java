package ru.otus.homework.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class YamlProps {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
