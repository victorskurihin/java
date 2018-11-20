package ru.otus.gwt.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class BackformView extends Composite implements IsWidget
{
    interface MyUiBinder extends UiBinder<Widget, BackformView> { /* None */ }
    private static final MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    public BackformView() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public Widget asWidget()
    {
        return getWidget();
    }
}
