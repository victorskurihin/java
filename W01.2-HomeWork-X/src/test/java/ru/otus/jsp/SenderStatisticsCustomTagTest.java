package ru.otus.jsp;

import org.junit.Test;

import javax.servlet.jsp.JspException;
import java.io.IOException;

import static org.junit.Assert.*;

public class SenderStatisticsCustomTagTest
{
    @Test
    public void test() throws IOException, JspException
    {
        SenderStatisticsCustomTag tag = new SenderStatisticsCustomTag();
        tag.setPageName("test");
    }
}