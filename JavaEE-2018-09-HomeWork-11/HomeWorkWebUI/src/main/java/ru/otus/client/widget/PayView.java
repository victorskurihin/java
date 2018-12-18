/*
 * PayView.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.client.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import ru.otus.client.Inside;
import ru.otus.client.service.InsideServiceAsync;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import static ru.otus.client.gin.ApplicationInjector.INSTANCE;

public class PayView extends Composite implements IsWidget
{
    @UiTemplate("PayView.ui.xml")
    public interface PayViewUiBinder extends UiBinder<VerticalPanel, PayView>
    { /* None */ }

    @UiField
    TextBox countOfPeriodTextField;

    @UiField
    TextBox sumOfCreditTextField;

    @UiField
    TextBox pctTextField;

    @UiField
    HorizontalPanel countOfPeriodPanel;

    @UiField
    HorizontalPanel sumOfCreditPanel;

    @UiField
    HorizontalPanel pctPanel;

    private Inside inside;
    private InsideServiceAsync service;

    @Override
    public Widget asWidget()
    {
        return getWidget();
    }

    @UiHandler("submit1")
    void clickHandler(ClickEvent evt)
    {
        int count = Integer.parseInt(countOfPeriodTextField.getValue());
        int sum = Integer.parseInt(sumOfCreditTextField.getValue());
        int pct = Integer.parseInt(pctTextField.getValue());

        service.diffPay(count, sum, pct, new AsyncCallback<ArrayList<String>>()
        {
            @Override
            public void onFailure(Throwable caught)
            {
                Window.alert("Error" + caught);
            }

            @Override
            public void onSuccess(ArrayList<String> result)
            {
                Window.alert("расчёта платежей: " + result);
            }
        });
    }

    @UiHandler("submit2")
    void clickHandlerMaxSalary(ClickEvent evt)
    {
        int count = Integer.parseInt(countOfPeriodTextField.getValue());
        int sum = Integer.parseInt(sumOfCreditTextField.getValue());
        int pct = Integer.parseInt(pctTextField.getValue());

        service.annuPay(count, sum, pct, new AsyncCallback<ArrayList<String>>()
        {
            @Override
            public void onFailure(Throwable caught)
            {
                Window.alert("Error" + caught);
            }

            @Override
            public void onSuccess(ArrayList<String> result)
            {
                Window.alert("расчёта платежей: " + result);
            }
        });
    }

    private static PayViewUiBinder ourUiBinder = INSTANCE.getPayViewUiBinder();

    @Inject
    public PayView(InsideServiceAsync service, Inside inside)
    {
        initWidget(ourUiBinder.createAndBindUi(this));

        this.service = service;
        this.inside = inside;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
