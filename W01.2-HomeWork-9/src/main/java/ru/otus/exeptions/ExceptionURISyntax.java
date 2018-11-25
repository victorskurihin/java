package ru.otus.exeptions;

import java.net.URISyntaxException;

public class ExceptionURISyntax extends URISyntaxException
{
    public ExceptionURISyntax(URISyntaxException e)
    {
        super(e.getInput(), e.getReason(), e.getIndex());
    }
}
