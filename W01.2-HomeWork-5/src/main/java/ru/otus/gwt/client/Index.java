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

public class Index implements EntryPoint
{
    private static LoginServiceAsync service = INSTANCE.getLoginService();

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
                .setInnerText("Home Work 5 of JavaEE 2018-09 | a work showcasing the feature of GWT");
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

        deckPanel.add(new IndexView());
        deckPanel.add(new ContactsView());
        deckPanel.add(new BackformView());
        deckPanel.add(new ArchiveView());

        RootPanel rootPanel = RootPanel.get("main-container");

        if (null != rootPanel) {
            deckPanel.showWidget(0);
        } else {
            rootPanel = RootPanel.get("login-container");
            if (null != rootPanel) {
                deckPanel.add(new LoginView(service));
                deckPanel.showWidget(4);
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
        new News(GWT.getHostPageBaseURL() + "/rbcnews");
        new CBRValutes(GWT.getHostPageBaseURL() + "/cbrforex");
    }
}
