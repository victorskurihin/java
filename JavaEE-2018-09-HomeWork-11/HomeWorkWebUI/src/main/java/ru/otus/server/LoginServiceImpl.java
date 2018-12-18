/*
 * LoginServiceImpl.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.otus.client.service.LoginService;
import ru.otus.shared.User;
import ru.otus.shared.exception.WrongCredentialException;
import ru.otus.shared.validation.ValidationRule;

import javax.servlet.ServletException;

public class LoginServiceImpl extends RemoteServiceServlet implements LoginService
{
    private static final Logger LOGGER = LogManager.getLogger(LoginServiceImpl.class.getName());

    // Implementation of sample interface method
    public String getMessage(String msg)
    {
        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
    }

    @Override
    public void authorize(User user) throws WrongCredentialException
    {
        if (ValidationRule.isValid(user)) {
            try {
                LOGGER.info("name: {}, password: {}", user.getLogin(), user.getPassword());
                getThreadLocalRequest().login(
                    user.getLogin(), user.getPassword()
                );
                LOGGER.info("Authentication done for user: {}", user.getLogin());
            }
            catch (ServletException e) {
                throw new WrongCredentialException("Некорректные логин/пароль");
            }
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
