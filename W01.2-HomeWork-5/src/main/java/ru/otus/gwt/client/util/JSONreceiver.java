package ru.otus.gwt.client.util;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import ru.otus.gwt.client.aside.NewsItem;

public class JSONreceiver extends JavaScriptObject
{
    protected JSONreceiver() { /* None */ }

    public final native JsArray<NewsItem> getNewsArray() /*-{
        return this.NewsArray;
    }-*/;
}
