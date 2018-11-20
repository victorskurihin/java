package ru.otus.utils;

import org.junit.Test;
import ru.otus.web.AuthenticationUtils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

public class AuthenticationUtilsTest
{

    @Test
    public void encodeSHA256() throws UnsupportedEncodingException, NoSuchAlgorithmException
    {
        String pass = "funt";
        System.out.println(" AuthenticationUtils.encodeSHA256(pass) = " + AuthenticationUtils.encodeSHA256(pass));
    }
}