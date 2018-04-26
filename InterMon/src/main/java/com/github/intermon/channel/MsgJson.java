package com.github.intermon.channel;

import com.github.intermon.messages.LoginMsg;
import com.github.intermon.messages.Msg;
import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MsgJson {

    /**
     * The method creates the JSON representation of Login Messages.
     *
     * @return the JSON representation of LoginMsg.
     */
    public static String createJsonLoginMsg() {
        return new Gson().toJson(new LoginMsg());
    }

    /**
     * The method ... TODO
     *
     * @return the JSON representation of LoginMsg.
     */
    public static String createJsonMsg(Msg msg) {
        return new Gson().toJson(msg);
    }

    /**
     * The method ... TODO
     *
     * @param json
     * @return
     * @throws ParseException
     * @throws ClassNotFoundException
     */
    public static Msg get(String json) throws ParseException, ClassNotFoundException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
        String className = (String) jsonObject.get(Msg.CLASS_NAME_VARIABLE);
        Class<?> msgClass = Class.forName(className);
        return (Msg) new Gson().fromJson(json, msgClass);
    }
}
