/*
 * InsideServiceAsync.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ru.otus.shared.Emp;
import ru.otus.shared.Search;

import java.util.ArrayList;
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

    void diffPay(int t, int kr, int st, AsyncCallback<ArrayList<Double>> async);

    void annuPay(int t, int kr, int st, AsyncCallback<ArrayList<Double>> async);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
