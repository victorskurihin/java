package ru.otus.outside.utils;

import mockit.Mock;
import mockit.MockUp;
import ru.otus.outside.exeptions.IORuntimeException;

import java.io.BufferedReader;
import java.io.IOException;

import static ru.otus.outside.utils.IOHelper.getBufferedReaderFromString;

public class IOHelperTestHelper
{
    public static void mockUp_IOHelper_getBufferedReader(String result)
    {
        new MockUp<IOHelper>() {
            @Mock
            public BufferedReader getBufferedReader(Class<?> clazz, String resource) {
                return getBufferedReaderFromString(result);
            }
        };
    }

    public static void mockUp_IOHelper_getBufferedReader_IORuntimeException()
    {
        new MockUp<IOHelper>() {
            @Mock
            public BufferedReader getBufferedReader(Class<?> clazz, String resource) {
                throw new IORuntimeException(new IOException());
            }
        };
    }
}