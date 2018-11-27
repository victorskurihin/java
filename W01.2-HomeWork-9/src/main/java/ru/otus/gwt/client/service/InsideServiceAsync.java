/*
 * Copyright (c) Victor N. Skurikhin 27.11.18 22:58.
 * InsideServiceAsync.java
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

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

    void getTax(double income, double costs, double taxRate, AsyncCallback<Double> async);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
