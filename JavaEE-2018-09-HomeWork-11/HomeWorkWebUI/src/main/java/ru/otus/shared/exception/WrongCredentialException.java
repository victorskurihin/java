/*
 * WrongCredentialException.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.shared.exception;

public class WrongCredentialException extends Exception
{
    public WrongCredentialException()
    { /* None */ }

    public WrongCredentialException(String message)
    {
        super(message);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
