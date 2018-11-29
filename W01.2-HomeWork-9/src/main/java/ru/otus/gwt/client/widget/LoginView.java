/*
 * LoginView.java
 * This file was last modified at 29.11.18 10:59 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.gwt.client.widget;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import ru.otus.gwt.client.service.LoginServiceAsync;
import ru.otus.gwt.shared.User;
import ru.otus.gwt.shared.exception.WrongCredentialException;
import ru.otus.gwt.shared.validation.ValidationRule;

import javax.validation.ConstraintViolation;
import java.util.Set;
import javax.inject.Inject;

import static ru.otus.gwt.client.gin.ApplicationInjector.INSTANCE;

public class LoginView extends Composite implements IsWidget
{
    @UiTemplate("LoginView.ui.xml")
    public interface LoginViewUiBinder extends UiBinder<VerticalPanel, LoginView> {
    }

    @UiField
    TextBox loginTextField;

    @UiField
    PasswordTextBox passwordTextField;

    @UiField
    HorizontalPanel loginPanel;

    @UiField
    HorizontalPanel passwordPanel;

    private LoginServiceAsync service;
    private Image loginInvalidFieldImage, passwordInvalidFieldImage;

    @Override
    public Widget asWidget()
    {
        return getWidget();
    }

    @UiHandler("submit")
    void clickHandler(ClickEvent evt)
    {
        User user = new User(loginTextField.getValue(), passwordTextField.getValue());
        Set<ConstraintViolation<User>> errors = ValidationRule.getErrors(user);
        clearErrors();

        if (errors.isEmpty()) {
            service.authorize(user, new AsyncCallback<Void>() {
                @Override
                public void onFailure(Throwable caught) {
                    if (caught instanceof WrongCredentialException) {
                        Window.alert(caught.getLocalizedMessage());
                    }
                }

                @Override
                public void onSuccess(Void result) {
                    // UrlBuilder builder = Window.Location.createUrlBuilder().setParameter(queryParam, value);
                    Window.Location.replace("inside.jsp");
                    // Window.alert("Вход успешен!");
                }
            });
        }
        else {
            errors.stream().forEach(e -> {
                String propertyName = e.getPropertyPath().toString();

                if (propertyName.equals(User.LOGIN)) {
                    loginInvalidFieldImage = showError(loginTextField, loginPanel, e.getMessage());
                }
                else if (propertyName.equals(User.PASSWORD)) {
                    passwordInvalidFieldImage = showError(passwordTextField, passwordPanel, e.getMessage());
                }
            });
        }
    }

    private static LoginViewUiBinder ourUiBinder = INSTANCE.getLoginViewUiBinder();

    @Inject
    public LoginView(LoginServiceAsync service) {
        initWidget(ourUiBinder.createAndBindUi(this));
        this.service = service;
    }

    public Image showError(TextBox textBox, Panel panel, String error) {
        textBox.getElement().getStyle().setBorderColor("red");
        final Image fieldInvalidImage = new Image(INSTANCE.getImages().field_invalid());
        Style style = fieldInvalidImage.getElement().getStyle();
        style.setCursor(Style.Cursor.POINTER);
        style.setMargin(6, Style.Unit.PX);
        fieldInvalidImage.setTitle(error);
        panel.add(fieldInvalidImage);
        return fieldInvalidImage;
    }

    public void clearErrors(){
        loginTextField.getElement().getStyle().clearBorderColor();

        if (loginInvalidFieldImage != null){
            loginInvalidFieldImage.removeFromParent();
        }
        passwordTextField.getElement().getStyle().clearBorderColor();

        if (passwordInvalidFieldImage != null){
            passwordInvalidFieldImage.removeFromParent();
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
