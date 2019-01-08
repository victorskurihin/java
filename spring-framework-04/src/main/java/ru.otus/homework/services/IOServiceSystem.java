package ru.otus.homework.services;

import java.io.InputStream;
import java.io.PrintStream;

public class IOServiceSystem implements IOService
{
    private InputStream in = System.in;

    private PrintStream out = System.out;

    public IOServiceSystem() { /* None */ }

    public IOServiceSystem(InputStream in, PrintStream out)
    {
        this.in = in;
        this.out = out;
    }

    @Override
    public InputStream getIn()
    {
        return in;
    }

    @Override
    public PrintStream getOut()
    {
        return out;
    }
}
