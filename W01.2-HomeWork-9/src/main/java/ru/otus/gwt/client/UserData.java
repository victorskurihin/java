/*
 * UserData.java
 * This file was last modified at 2018.12.01 16:31 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.gwt.client;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import ru.otus.gwt.shared.User;

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class UserData
{
    public String login;
    public String password;

    public UserData() { /* None */ }

    @JsOverlay
    public static UserData as(User user)
    {
        UserData data = new UserData();
        data.login = user.getLogin();
        data.password = user.getPassword();

        return data;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
