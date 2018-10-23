package ru.otus.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.*;
import ru.otus.gwt.widget.IndexView;

public class Index implements EntryPoint
{
    @SuppressWarnings("GWTStyleCheck")
    public void onModuleLoad()
    {
        DockPanel headerPanel = new DockPanel();
        headerPanel.setStyleName("tg body");
        headerPanel.setSpacing(0);
        headerPanel.setHorizontalAlignment(DockPanel.ALIGN_CENTER);

        HTML header = new HTML(HeaderHTML.INSTANCE.synchronous().getText());
        headerPanel.add(header, DockPanel.CENTER);

        VerticalPanel vPanel = new VerticalPanel();
        vPanel.setSpacing(0);
        vPanel.setStyleName("tg body");
        vPanel.add(headerPanel);

        // Add the widgets to the root panel.
        RootPanel.get("headerContainer").add(vPanel);

        Element indexContainer = Document.get().getElementById("indexContainer");
        if (null != indexContainer) {
            indexContainer.appendChild(new IndexView().getElement());
        }
    }
}
