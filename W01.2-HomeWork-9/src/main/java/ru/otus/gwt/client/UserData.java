package ru.otus.gwt.client;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import ru.otus.gwt.shared.User;

@JsType(isNative=true, namespace= JsPackage.GLOBAL, name="Object")
public class UserData {

    public String login;
    public String password;

    public UserData(){}

    @JsOverlay
    public static UserData as(User user){
        UserData data = new UserData();
        data.login = user.getLogin();
        data.password = user.getPassword();
        return data;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
