/*
 * SearchView.java
 * This file was last modified at 2018.12.01 16:30 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.gwt.client.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import ru.otus.gwt.client.Inside;
import ru.otus.gwt.client.service.InsideServiceAsync;
import ru.otus.gwt.shared.Emp;
import ru.otus.gwt.shared.Search;

import javax.inject.Inject;

import java.util.List;

import static ru.otus.gwt.client.gin.ApplicationInjector.INSTANCE;

public class SearchView extends Composite implements IsWidget
{
    @UiTemplate("SearchView.ui.xml")
    public interface SearchViewUiBinder extends UiBinder<VerticalPanel, SearchView>
    { /* None */ }

    @UiField
    TextBox fioNameTextField;

    @UiField
    TextBox jobTextField;

    @UiField
    TextBox cityTextField;

    @UiField
    TextBox ageTextField;

    @UiField
    HorizontalPanel fioNamePanel;

    @UiField
    HorizontalPanel jobPanel;

    @UiField
    HorizontalPanel cityPanel;

    @UiField
    HorizontalPanel agePanel;

    private Inside inside;
    private InsideServiceAsync service;

    @Override
    public Widget asWidget()
    {
        return getWidget();
    }

    @UiHandler("submit")
    void clickHandler(ClickEvent evt)
    {
        Search search = new Search(
            fioNameTextField.getValue(), jobTextField.getValue(), cityTextField.getValue(), ageTextField.getValue()
        );
        service.searchEmp(search, new AsyncCallback<List<Emp>>()
        {
            @Override
            public void onFailure(Throwable caught)
            {
                Window.alert("Error" + caught);
            }

            @Override
            public void onSuccess(List<Emp> result)
            {
                inside.showSearchResult(result);
            }
        });
    }

    private static SearchViewUiBinder ourUiBinder = INSTANCE.getSearchViewUiBinder();

    @Inject
    public SearchView(InsideServiceAsync service, Inside inside)
    {
        initWidget(ourUiBinder.createAndBindUi(this));

        this.service = service;
        this.inside = inside;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
