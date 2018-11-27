/*
 * Copyright (c) Victor N. Skurikhin 27.11.18 22:58.
 * InsideService.java
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.gwt.client.service;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import ru.otus.gwt.shared.Emp;
import ru.otus.gwt.shared.Search;

import java.util.List;

@RemoteServiceRelativePath("InsideService")
public interface InsideService extends RemoteService
{
    List<Emp> getEmpsList();
    void addNewEmp(Emp emp);
    void setEmpFirstName(long id, String value);
    void setEmpSecondName(long id, String value);
    void setEmpSurName(long id, String value);
    void deleteEmp(long id);
    List<Emp> searchEmp(Search search);

    double getTax(double income, double costs, double taxRate);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
