package ru.otus.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.*;
import ru.otus.gwt.client.news.News;
import ru.otus.gwt.client.service.LoginServiceAsync;
import ru.otus.gwt.client.valutes.CBRValutes;

import static ru.otus.gwt.client.gin.ApplicationInjector.INSTANCE;

public class Login extends Index
{
    private static LoginServiceAsync service = INSTANCE.getService();
    private TextBox loginTextBox;
    private PasswordTextBox passwordTextBox;

    public String getLogin() {
        return loginTextBox.getText();
    }

    public String getPassword() {
        return passwordTextBox.getText();
    }

    private void initMainContainer()
    {
        final DeckPanel deckPanel = new DeckPanel();

        RootPanel rootPanel = fillDeckPanel(deckPanel);
        Label stubLabel = new Label("OK!!!");
        deckPanel.add(stubLabel);
        deckPanel.showWidget(5);

        VerticalPanel vPanel = new VerticalPanel();
        vPanel.add(deckPanel);

        menuAddClickHandler(deckPanel, "navigation-menu-", 5);
        menuAddClickHandler(deckPanel, "aside-menu-", 4);

        rootPanel.add(vPanel);
    }

    public void onModuleLoad()
    {
        super.initHeaderAndTitle();
        initMainContainer();
        new News(GWT.getHostPageBaseURL() + "/rbcnews");
        new CBRValutes(GWT.getHostPageBaseURL() + "/cbrforex");
    }
}
