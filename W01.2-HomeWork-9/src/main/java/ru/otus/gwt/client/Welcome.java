/*
 * Copyright (c) Victor N. Skurikhin 27.11.18 23:06.
 * Welcome.java
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.*;
import ru.otus.gwt.client.aside.News;
import ru.otus.gwt.client.service.LoginServiceAsync;
import ru.otus.gwt.client.aside.CBRValutes;
import ru.otus.gwt.client.widget.*;

import static ru.otus.gwt.client.gin.ApplicationInjector.INSTANCE;
import static ru.otus.gwt.shared.Constants.REQUEST_FOREX_RATES;
import static ru.otus.gwt.shared.Constants.REQUEST_NEWS;

public class Welcome implements EntryPoint
{
    private static LoginServiceAsync service = INSTANCE.getLoginService();
    private int deckIndexIndexView;
    private int deckIndexContactsView;
    private int deckIndexBackformView;
    private int deckIndexArchiveView;
    private int deckIndexLoginView;

    public int getDeckIndexIndexView()
    {
        return deckIndexIndexView;
    }

    public int getDeckIndexContactsView()
    {
        return deckIndexContactsView;
    }

    public int getDeckIndexBackformView()
    {
        return deckIndexBackformView;
    }

    public int getDeckIndexArchiveView()
    {
        return deckIndexArchiveView;
    }

    public int getDeckIndexLoginView()
    {
        return deckIndexLoginView;
    }

    protected void initHeaderAndTitle()
    {
        Document.get()
                .getElementById("header-1")
                .setInnerText("Рога и копыта");
        Document.get()
                .getElementById("header-2")
                .setInnerText("Заведение, занимается заготовкой «когтей и хвостов» и «горчицы и щёлока»");
        Document.get()
                .getElementById("title")
                .setInnerText("Home Work 9 of JavaEE 2018-09 | a work showcasing the feature Freemarker");
    }

    public void menuAddClickHandler(DeckPanel deckPanel, String id, int n)
    {
        for (int i = 0; i < n; ++i) {
            Anchor anchor = Anchor.wrap(
                    Document.get().getElementById(id + i).cast()
            );
            if (null != anchor) {
                int finalI = i;
                anchor.addClickHandler(event -> deckPanel.showWidget(finalI));
            } else {
                throw new NullPointerException(); // TODO custom Exception
            }
        }
    }

    @SuppressWarnings("GwtToHtmlReferences")
    protected RootPanel fillDeckPanel(DeckPanel deckPanel)
    {
        //noinspection GWTStyleCheck
        deckPanel.setStyleName("deckpanel");
        deckPanel.getElement().setId("main-deck-panel");

        deckPanel.add(new IndexView());
        deckIndexIndexView = deckPanel.getWidgetCount() - 1;

        deckPanel.add(new ContactsView());
        deckIndexContactsView = deckPanel.getWidgetCount() - 1;

        deckPanel.add(new BackformView());
        deckIndexBackformView = deckPanel.getWidgetCount() - 1;

        deckPanel.add(new ArchiveView());
        deckIndexArchiveView = deckPanel.getWidgetCount() - 1;

        RootPanel rootPanel = RootPanel.get("main-container");

        if (null != rootPanel) {
            deckPanel.showWidget(getDeckIndexIndexView());
        } else {
            rootPanel = RootPanel.get("login-container");
            if (null != rootPanel) {
                deckPanel.add(new LoginView(service));
                deckIndexLoginView = deckPanel.getWidgetCount() - 1;
                deckPanel.showWidget(getDeckIndexLoginView());
            } else {
                rootPanel = RootPanel.get("inside-container");
            }
        }

        return rootPanel;
    }

    // main-container
    private void initMainContainer()
    {
        final DeckPanel deckPanel = new DeckPanel();

        RootPanel rootPanel = fillDeckPanel(deckPanel);

        VerticalPanel vPanel = new VerticalPanel();
        vPanel.add(deckPanel);

        menuAddClickHandler(deckPanel, "navigation-menu-", 4);
        menuAddClickHandler(deckPanel, "aside-menu-", 4);

        rootPanel.add(vPanel);
    }

    public void onModuleLoad()
    {
        initHeaderAndTitle();
        initMainContainer();
        new News(GWT.getHostPageBaseURL() + "/" + REQUEST_NEWS);
        new CBRValutes(GWT.getHostPageBaseURL() + "/" + REQUEST_FOREX_RATES);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
