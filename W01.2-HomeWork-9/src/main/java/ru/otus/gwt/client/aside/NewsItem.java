package ru.otus.gwt.client.aside;

/*
 * Created by VSkurikhin at autumn 2018.
 */

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

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
