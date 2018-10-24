package ru.otus.gwt.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import ru.otus.gwt.client.service.ApplicationServiceAsync;
import ru.otus.gwt.client.widget.*;

import static ru.otus.gwt.client.gin.ApplicationInjector.INSTANCE;

public class Login extends AppEntryPoint
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

    @Override
    void showMainContainer()
    {
        Element container = Document.get().getElementById("loginContainer");

        if (null != container) {
            container.appendChild(new LoginView(service).getElement());
        }
    }
}
