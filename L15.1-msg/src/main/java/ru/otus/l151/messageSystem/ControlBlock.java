package ru.otus.l151.messageSystem;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ControlBlock {
    private Map<String,String> map = new ConcurrentHashMap<>();

    public enum Keys {
        AUTH("AUTH"), CHAT("CHAT"), FORM("FORM"), LOGIN("LOGIN"),
        PASSWORD("PASSWORD"), SESSION("SESSION"), SINGUP("SINGUP");

        private final String text;

        Keys(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public String put(String key, String value) {
        return map.put(key, value);
    }

    public String get(String key) {
        return map.get(key);
    }

    public String getOrDefault(String key) {
        return map.getOrDefault(key, null);
    }

    public String getOrDefault(String key, String defaultValue) {
        return map.getOrDefault(key, defaultValue);
    }

    public static String keyS(Keys keys) {
        return keys.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ControlBlock that = (ControlBlock) o;
        return Objects.equals(map, that.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
