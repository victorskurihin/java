package ru.otus.exeptions;

public class ExceptionThrowable extends Throwable
{
    public ExceptionThrowable(Throwable e)
    {
        super(e);
        e.printStackTrace();
    }
}
