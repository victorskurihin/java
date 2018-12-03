/*
 * JSONreceiver.java
 * This file was last modified at 2018.12.01 16:26 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.gwt.client.util;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import ru.otus.gwt.client.aside.NewsItem;

public class JSONreceiver extends JavaScriptObject
{
    protected JSONreceiver()
    { /* None */ }

    public final native JsArray<NewsItem> getNewsArray() /*-{
        return this.NewsArray;
    }-*/;
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
