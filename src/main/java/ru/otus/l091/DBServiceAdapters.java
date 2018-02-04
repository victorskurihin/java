package ru.otus.l091;

import java.util.HashMap;
import java.util.Map;

public abstract class DBServiceAdapters extends DBServiceConnection {
    Map<String, Adapter> adapters = new HashMap<>();

    private final Adapter[] predefinedAdapters = {
        new DefaultAdapter()
    };

    DBServiceAdapters() {
        for (Adapter a : predefinedAdapters) {
            adapters.put(a.getAdapteeOfType(), a);
            a.setAdapters(adapters);
        }
    }
}
