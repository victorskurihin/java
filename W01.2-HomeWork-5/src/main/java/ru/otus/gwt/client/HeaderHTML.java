package ru.otus.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

public interface HeaderHTML extends ClientBundle
{
    HeaderHTML INSTANCE = GWT.create(HeaderHTML.class);

    @Source("header.html")
    TextResource synchronous();
}
