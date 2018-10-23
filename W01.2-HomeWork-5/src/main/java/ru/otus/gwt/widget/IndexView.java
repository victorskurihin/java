package ru.otus.gwt.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.TableElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;

public class IndexView
{
    interface MyUiBinder extends UiBinder<TableElement, IndexView> { /* None */ }
    private static final MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    private TableElement root;

    public IndexView() {
        root = uiBinder.createAndBindUi(this);
    }

    public Element getElement() {
        return root;
    }
}
