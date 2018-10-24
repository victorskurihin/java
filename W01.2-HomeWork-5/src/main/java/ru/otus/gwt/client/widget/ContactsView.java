package ru.otus.gwt.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.TableElement;
import com.google.gwt.uibinder.client.UiBinder;

public class ContactsView implements View
{
    interface MyUiBinder extends UiBinder<TableElement, ContactsView> { /* None */ }
    private static final MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    private TableElement root;

    public ContactsView() {
        root = uiBinder.createAndBindUi(this);
    }

    public Element getElement() {
        return root;
    }
}
