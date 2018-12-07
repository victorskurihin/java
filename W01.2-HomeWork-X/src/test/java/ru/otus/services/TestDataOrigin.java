package ru.otus.services;

import static ru.otus.services.TestExpectedData.JSON_TEST;
import static ru.otus.services.TestExpectedData.XML_TEST;

public class TestDataOrigin implements DataOrigin
{
    private boolean ready = false;
    @Override
    public boolean isReady()
    {
        return ready;
    }

    @Override
    public void fetchData() {
        ready = true;
    }

    @Override
    public String getDataXML()
    {
        return XML_TEST;
    }

    @Override
    public String getDataJSON()
    {
        return JSON_TEST;
    }
}
