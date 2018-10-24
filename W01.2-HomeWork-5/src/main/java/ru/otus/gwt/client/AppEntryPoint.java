package ru.otus.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.HTML;

public abstract class AppEntryPoint implements EntryPoint
{
    abstract void showMainContainer();

    @SuppressWarnings("GWTStyleCheck")
    public void onModuleLoad()
    {
        HTML header = new HTML(HeaderHTML.INSTANCE.synchronous().getText());
        Element headerContainer = Document.get().getElementById("headerContainer");

        try {
            if (null != headerContainer) {
                headerContainer.appendChild(header.getElement());
            } else {
                // TODO Execption
            }

            showMainContainer();
        } catch (Exception e) { // TODO
            e.printStackTrace();
        }
    }
}
