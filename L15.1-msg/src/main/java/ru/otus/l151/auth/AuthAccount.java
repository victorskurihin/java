package ru.otus.l151.auth;

/*
 * Created by VSkurikhin at winter 2018.
 */

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import ru.otus.l151.dataset.UserDataSet;
import ru.otus.l151.db.DBService;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides access to a centralized registry of the user's online
 * accounts.  The user enters credentials  (username and password) once  per
 * account,  granting   applications   access   to  online  resources   with
 * "one-click" approval.
 */
public class AuthAccount {
    public static final String ADMIN_NAME = "admin_username";
    private static final String ADMIN_PASSWORD = "admin_password";
    private Map<String, String> adminsUserPassword = new HashMap<>();
    private Map<String, String> usersPassword = new HashMap<>();

    private static Map<String, String> loadAdmins(DBService dbService, String fileName)
        throws Exception {

        URL url = Resources.getResource(fileName);
        List<String> lines = Resources.readLines(url, Charsets.UTF_8);
        int index = 1;
        Map<String, String> result = new HashMap<>();

        for (String line : lines) {
            String[] variablePair = line.split("=", 2 );
            if (2 == variablePair.length) {
                String variableName = variablePair[0].trim();
                String variableValue = variablePair[1].trim();

                result.put(variableName, variableValue);
                if (variableName.equals(AuthAccount.ADMIN_NAME)) {
                    UserDataSet adminUser = new UserDataSet(
                        index++, variableValue, null
                    );
                    dbService.save(adminUser);
                }
            }
        }

        return result;
    }

    /**
     * The constructor initializes the list of admin accounts.
     *
     * @param admins the list of admin accounts with passwords.
     */
    public AuthAccount(Map<String, String> admins) {
        if (admins.containsKey(ADMIN_NAME)) {
            String adminName = admins.get(ADMIN_NAME);
            System.out.println("adminName = " + adminName);
            if (admins.containsKey(ADMIN_PASSWORD)) {
                String password = admins.get(ADMIN_PASSWORD);
                System.out.println("password = " + password);
                adminsUserPassword.put(adminName, password);
            }
        }
    }

    public AuthAccount(DBService dbService, String fileName) throws Exception {
        this(loadAdmins(dbService, fileName));
    }
    /**
     * The method is procedure for users authentication.
     *
     * @param username the name of user
     * @param password the password of user
     * @return true if authentication is success
     */
    public boolean auth(String username, String password) {
        if (usersPassword.containsKey(username)) {
            return usersPassword.get(username).equals(password);
        }
        return adminsUserPassword.containsKey(username)
            && adminsUserPassword.get(username).equals(password);
    }

    /**
     * The method is procedure for detecting admin accounts.
     *
     * @param username the name of user
     * @return true if account is administrator
     */
    public boolean isAdministrator(String username) {
        return adminsUserPassword.containsKey(username);
    }

    /**
     * The method adds the user to authentication map.
     *
     * @param user the name of user
     * @param password the password of user
     */
    public void put(String user, String password) {
        usersPassword.put(user, password);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
