/*
 * News.java
 * This file was last modified at 2018.12.01 16:24 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.gwt.client.aside;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import ru.otus.gwt.client.util.JSONreceiver;

public class News
{
    private AsyncCallback callbackCBR = new AsyncCallback<JSONreceiver>()
    {
        public void onFailure(Throwable throwable)
        {
            Window.alert("Error: " + throwable.getMessage());
        }

        @Override
        public void onSuccess(JSONreceiver result)
        {
            JsArray<NewsItem> entries = result.getNewsArray();

            for (int i = 0; i < entries.length(); i++) {
                RootPanel id = RootPanel.get(entries.get(i).getId());
                id.add(new Label(entries.get(i).getText()));
            }
        }
    };

    public News(String url)
    {
        JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
        jsonp.setCallbackParam("mycallback");
        //noinspection unchecked
        jsonp.requestObject(url, callbackCBR);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
