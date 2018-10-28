package ru.otus.gwt.client.news;

import com.google.gwt.core.client.JavaScriptObject;

public class NewsItem extends JavaScriptObject
{
    protected NewsItem() {
    }

    public final native String getId() /*-{
        return this.Id;
    }-*/;

    public final native String getText() /*-{
        return this.Text;
    }-*/;
}
