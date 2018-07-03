package com.github.intermon.app;

import com.github.intermon.messages.Msg;
import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.Random;

public class RandomUnsignedInt {
    public static int get() {
        Random random = new Random();
        return (int) (random.nextLong() & 0x7fffffffL);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
