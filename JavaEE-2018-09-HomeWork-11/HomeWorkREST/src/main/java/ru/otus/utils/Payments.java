package ru.otus.utils;

public class Payments
{
    public static double annuityPayment(double t, int kr, double st, int i)
    {
        return kr*st/(1 - 1/Math.pow(1.0 + st, t));
    }

    public static double differentiatedPayment(double t, int kr, double st, int i)
    {
        return kr/t + kr*(t - i + 1)*st/t;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
