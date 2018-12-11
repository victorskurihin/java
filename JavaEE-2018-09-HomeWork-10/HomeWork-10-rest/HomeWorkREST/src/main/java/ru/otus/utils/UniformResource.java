package ru.otus.utils;

import javax.servlet.http.HttpServletRequest;

public class UniformResource
{
    public static String getRequestURI(HttpServletRequest request)
    {
        return request.getRequestURI();
    }

    public static String getRequestURL(HttpServletRequest request)
    {
        return request.getRequestURL().toString();
    }

    public static String getBaseURL(HttpServletRequest request)
    {
        return getRequestURL(request).replaceFirst(getRequestURI(request),"") + request.getContextPath();
    }
}
