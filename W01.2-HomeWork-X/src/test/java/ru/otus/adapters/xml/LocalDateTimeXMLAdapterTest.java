package ru.otus.adapters.xml;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class LocalDateTimeXMLAdapterTest
{

    @Test
    public void unmarshal() throws Exception
    {
        LocalDateTimeXMLAdapter adapter = new LocalDateTimeXMLAdapter();
        LocalDateTime expected = LocalDateTime.now();
        String time = adapter.marshal(expected);
        LocalDateTime test = adapter.unmarshal(time);
        Assert.assertEquals(expected, test);

    }
}