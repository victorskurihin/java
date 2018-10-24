package ru.otus.gwt.client;

//import com.google.gwt.dom.client.Document;
//import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.*;
import ru.otus.gwt.client.widget.LoginView;
import ru.otus.gwt.client.service.ApplicationServiceAsync;
//import ru.otus.gwt.client.widget.*;
//
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
        Label stubLabel = new Label("ok");

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
