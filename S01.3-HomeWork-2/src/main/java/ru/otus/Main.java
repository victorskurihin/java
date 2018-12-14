package ru.otus;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main
{
    public static void main(String[] args)
    {
        ( new Application( new ClassPathXmlApplicationContext( new String[] {
            "META-INF/spring/App-context-AutoScan.xml"
        }))).run();
    }
}