package ru.otus.gwt.client.widget;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class ArchiveView extends Composite implements IsWidget
{
    interface MyUiBinder extends UiBinder<Widget, ArchiveView> { /* None */ }
    private static final MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    public ArchiveView() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public Widget asWidget()
    {
        return getWidget();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
