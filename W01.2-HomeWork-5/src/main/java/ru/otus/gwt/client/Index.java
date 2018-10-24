package ru.otus.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.*;
import ru.otus.gwt.client.service.ApplicationServiceAsync;
import ru.otus.gwt.client.widget.*;

import static ru.otus.gwt.client.gin.ApplicationInjector.INSTANCE;

public class Index implements EntryPoint
{
    private static ApplicationServiceAsync service = INSTANCE.getService();

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

    public void menuAddClickHandler(DeckPanel deckPanel, String id, int i2)
    {
        for (int i = 0; i < i2; ++i) {
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

    protected RootPanel fillDeckPanel(DeckPanel deckPanel)
    {
        //noinspection GWTStyleCheck
        deckPanel.setStyleName("deckpanel");

        Label stubLabel = new Label("stub");

        deckPanel.add(new IndexView());
        deckPanel.add(new ContactsView());
        deckPanel.add(new BackformView());
        deckPanel.add(new ArchiveView());
        deckPanel.add(stubLabel);

        RootPanel rootPanel = RootPanel.get("main-container");

        if (null != rootPanel) {
            deckPanel.showWidget(0);
        } else {
            rootPanel = RootPanel.get("login-container");
            deckPanel.add(new LoginView(service));
            deckPanel.showWidget(5);
        }

        menuAddClickHandler(deckPanel, "navigation-menu-", 4);
        menuAddClickHandler(deckPanel, "aside-menu-", 4);

        return rootPanel;
    }

    // main-container
    private void initMainContainer()
    {
        final DeckPanel deckPanel = new DeckPanel();

        RootPanel rootPanel = fillDeckPanel(deckPanel);

        VerticalPanel vPanel = new VerticalPanel();
        vPanel.add(deckPanel);

        rootPanel.add(vPanel);
    }

    public void onModuleLoad()
    {
        initHeaderAndTitle();
        initMainContainer();
    }
}
