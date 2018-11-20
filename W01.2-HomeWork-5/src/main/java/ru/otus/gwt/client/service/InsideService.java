package ru.otus.gwt.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import ru.otus.gwt.shared.Emp;

import java.util.List;

@RemoteServiceRelativePath("InsideService")
public interface InsideService extends RemoteService {
    List<Emp> getEmpsList();
}
