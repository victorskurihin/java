package ru.otus.gwt.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.gwt.client.service.ApplicationService;
import ru.otus.gwt.shared.User;
import ru.otus.gwt.shared.exception.WrongCredentialException;
import ru.otus.gwt.shared.validation.ValidationRule;
import ru.otus.web.AuthenticationUtils;

import javax.servlet.ServletException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class ApplicationServiceImpl extends RemoteServiceServlet implements ApplicationService {

    private static final Logger LOGGER = LogManager.getLogger(ApplicationServiceImpl.class.getName());

    // Implementation of sample interface method
    public String getMessage(String msg) {
        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
    }

    @Override
    public void authorize(User user) throws WrongCredentialException {
        if (ValidationRule.isValid(user)){
            try {
                LOGGER.info("name: {}, password: {}", user.getLogin(), user.getPassword());
                getThreadLocalRequest().login(
                        user.getLogin(), user.getPassword()
                );
                LOGGER.info("Authentication done for user: {}", user.getLogin());
            } catch (ServletException e) {
                throw new WrongCredentialException("Некорректные логин/пароль");
            }
        }
    }
}