package ru.otus.gwt.client.service;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import com.google.gwt.user.client.rpc.AsyncCallback;
import ru.otus.gwt.shared.Emp;
import ru.otus.gwt.shared.Search;

import java.util.List;

public interface InsideServiceAsync
{
    void getEmpsList(AsyncCallback<List<Emp>> async);
    void addNewEmp(Emp emp, AsyncCallback<Void> voidAsyncCallback);
    void setEmpFirstName(long id, String value, AsyncCallback<Void> async);
    void setEmpSecondName(long id, String value, AsyncCallback<Void> async);
    void setEmpSurName(long id, String value, AsyncCallback<Void> async);
    void deleteEmp(long id, AsyncCallback<Void> async);
    void searchEmp(Search search, AsyncCallback<List<Emp>> async);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
