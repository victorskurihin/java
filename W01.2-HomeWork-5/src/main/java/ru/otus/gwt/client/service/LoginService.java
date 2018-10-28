package ru.otus.gwt.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import ru.otus.gwt.shared.User;
import ru.otus.gwt.shared.exception.WrongCredentialException;

@RemoteServiceRelativePath("LoginService")
public interface LoginService extends RemoteService {
    String getMessage(String msg);
    void authorize(User user) throws WrongCredentialException;
}
