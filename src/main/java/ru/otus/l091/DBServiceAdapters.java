package ru.otus.l091;

import java.util.HashMap;
import java.util.Map;

public abstract class DBServiceAdapters extends DBServiceConnection {
    Map<String, Adapter> adapters = new HashMap<>();

    private final Adapter[] predefinedAdapters = {
        new DefaultAdapter(getConnection())
    };

    DBServiceAdapters() {
        for (Adapter a : predefinedAdapters) {
            adapters.put(a.getAdapteeOfType(), a);
            a.setAdapters(adapters);
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
