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
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
