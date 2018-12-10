package ru.otus.utils;

import java.io.InputStream;

public class ResourceAsStream
{
    private String filename;
    public ResourceAsStream(String filename)
    {
        this.filename = filename;
    }

    public InputStream get()
    {
        System.err.println("filename = " + filename);
        InputStream result = this.getClass().getClassLoader().getResourceAsStream(filename);
        System.err.println("result = " + result);

        return result;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
