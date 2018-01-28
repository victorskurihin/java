package ru.otus.l081;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import java.lang.reflect.Field;

public class SimpleObjectOutputJsonTest extends SimpleObjectOutputJson {
    SimpleObjectOutputJson oojs;

    @Before
    public void setUp() throws Exception {
        oojs = new SimpleObjectOutputJson();
    }

    @After
    public void tearDown() throws Exception {
        oojs = null;
    }

}
