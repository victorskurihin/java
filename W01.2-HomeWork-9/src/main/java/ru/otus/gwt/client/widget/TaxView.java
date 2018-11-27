/*
 * Copyright (c) Victor N. Skurikhin 27.11.18 23:28.
 * TaxView.java
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

import javax.inject.Inject;

import static ru.otus.gwt.client.gin.ApplicationInjector.INSTANCE;

public class TaxView extends Composite implements IsWidget
{
    @UiTemplate("TaxView.ui.xml")
    public interface TaxViewUiBinder extends UiBinder<VerticalPanel, TaxView> {
    }

    @UiField
    TextBox incomeTextField;

    @UiField
    TextBox costsTextField;

    @UiField
    TextBox taxRateTextField;

    @UiField
    HorizontalPanel incomePanel;

    @UiField
    HorizontalPanel costsPanel;

    @UiField
    HorizontalPanel taxRatePanel;

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
        double income = Double.parseDouble(incomeTextField.getValue());
        double costs = Double.parseDouble(costsTextField.getValue());
        double taxRate = Double.parseDouble(taxRateTextField.getValue());
        service.getTax(income, costs, taxRate, new AsyncCallback<Double>()
        {
            @Override
            public void onFailure(Throwable caught)
            {
                Window.alert("Error" + caught);
            }

            @Override
            public void onSuccess(Double result)
            {
                Window.alert("Размер налога на прибыль за отчетный период: " + result);
            }
        });
    }

    private static TaxViewUiBinder ourUiBinder = INSTANCE.getTaxViewUiBinder();

    @Inject
    public TaxView(InsideServiceAsync service, Inside inside) {
        initWidget(ourUiBinder.createAndBindUi(this));
        this.service = service;
        this.inside = inside;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
