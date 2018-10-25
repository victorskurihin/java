package ru.otus.gwt.client;

import com.google.gwt.user.client.ui.*;
import ru.otus.gwt.client.service.ApplicationServiceAsync;

import static ru.otus.gwt.client.gin.ApplicationInjector.INSTANCE;

public class Login extends Index
{
    private static ApplicationServiceAsync service = INSTANCE.getService();
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

        VerticalPanel vPanel = new VerticalPanel();
        vPanel.add(deckPanel);

        rootPanel.add(vPanel);
    }

    public void onModuleLoad()
    {
        super.initHeaderAndTitle();
        initMainContainer();
    }
}
