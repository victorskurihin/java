package ru.otus.utils;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class AuthenticationTest
{

    @Test
    public void encodeSHA256() throws UnsupportedEncodingException, NoSuchAlgorithmException
    {
        String pass = "funt";
        System.out.println(" Authentication.encodeSHA256(pass) = " + Authentication.encodeSHA256(pass));
        Long l = 1L;
    }
}