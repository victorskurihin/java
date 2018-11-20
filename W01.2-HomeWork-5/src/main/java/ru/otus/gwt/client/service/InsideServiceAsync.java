package ru.otus.gwt.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ru.otus.gwt.shared.Emp;

import java.util.List;

public interface InsideServiceAsync
{
    void getEmpsList(AsyncCallback<List<Emp>> async);
}
