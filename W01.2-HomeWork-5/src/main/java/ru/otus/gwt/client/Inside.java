package ru.otus.gwt.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import ru.otus.gwt.client.widget.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Inside extends AppEntryPoint
{
    static final Map<String, View> MAIN_CONTAINERS;
    static
    {
        Map<String, View> tempMap = new HashMap<>();
        // tempMap.put("indexContainer", new IndexView());

        MAIN_CONTAINERS = Collections.unmodifiableMap(tempMap);
    }

    @Override
    void showMainContainer()
    {
        for (Map.Entry<String, View> entry : MAIN_CONTAINERS.entrySet()) {
            Element container = Document.get().getElementById(entry.getKey());
            if (null != container) {
                container.appendChild(entry.getValue().getElement());
            }
        }
    }
}
