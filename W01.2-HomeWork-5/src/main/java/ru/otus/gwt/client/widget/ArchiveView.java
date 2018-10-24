package ru.otus.gwt.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.TableElement;
import com.google.gwt.uibinder.client.UiBinder;

public class ArchiveView implements View
{
    interface MyUiBinder extends UiBinder<TableElement, ArchiveView> { /* None */ }
    private static final MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    private TableElement root;

    public ArchiveView() {
        root = uiBinder.createAndBindUi(this);
    }

    public Element getElement() {
        return root;
    }
}
