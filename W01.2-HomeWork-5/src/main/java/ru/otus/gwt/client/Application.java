package ru.otus.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.*;
import ru.otus.gwt.client.service.ApplicationServiceAsync;
import ru.otus.gwt.client.text.ApplicationConstants;
import ru.otus.gwt.client.widget.MainView;

import static ru.otus.gwt.client.gin.ApplicationInjector.INSTANCE;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class Application implements EntryPoint {

    private static ApplicationServiceAsync service = INSTANCE.getService();
    private static ApplicationConstants dictionary = INSTANCE.getConstants();

    public static final String LABEL_CLASS_NAME = "firstColumnWidth";
    public static final String INPUT_CLASS_NAME = "inputWidth";

    private TextBox loginTextBox;
    private PasswordTextBox passwordTextBox;

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        initHeaderAndTitle();
        initMainSlot();
    }

    public String getLogin() {
        return loginTextBox.getText();
    }

    public String getPassword() {
        return passwordTextBox.getText();
    }

    private void initHeaderAndTitle(){
        Document.get().getElementById("header").setInnerText(dictionary.form_header());
        Document.get().getElementById("title").setInnerText(dictionary.title());
    }


    private void initMainSlot(){
        RootPanel.get("slot").add(new MainView(service));
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
}
