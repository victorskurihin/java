/*
 * ArchiveView.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class ArchiveView extends Composite implements IsWidget
{
    interface MyUiBinder extends UiBinder<Widget, ArchiveView>
    { /* None */ }

    private static final MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    public ArchiveView()
    {
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
