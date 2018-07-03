package com.github.intermon.messages;

import com.github.intermon.messages.Msg;
import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/*
 * Created by VSkurikhin at Sun Apr 15 13:20:45 MSK 2018.
 */

/**
 * This is contract for message (Msg) classes with default the helper
 * realization.
 *
 * A class is a superclass for implementing a message (Msg) interfaces.
 */
public abstract class Msg {
    public static final String CLASS_NAME_VARIABLE = "className";

    private final Address from;
    private final Address to;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final String className;

    protected Msg(Class<?> klass, Address from, Address to) {
        this.className = klass.getName();
        this.from = from;
        this.to = to;
    }

    public abstract String getId();

    public Address getFrom() {
        return from;
    }

    public Address getTo() {
        return to;
    }

    public static Msg get(String json) throws ClassNotFoundException, ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
        String className = (String) jsonObject.get(Msg.CLASS_NAME_VARIABLE);
        Class<?> msgClass = Class.forName(className);
        return (Msg) new Gson().fromJson(json, msgClass);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
