package su.svn.fi.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ParserImplTest
{
    ParserImpl parser;

    @BeforeEach
    void setUp()
    {
        parser = new ParserImpl();
    }

    String case1 = "01-Jan-1996";
    @Test
    void test_parseDate_good_case1()
    {
        LocalDate expected = LocalDate.of(1996, 1, 1);
        LocalDate date = parser.parseDate(case1);
        assertEquals(expected, date);
    }

    String case2 = "21-Feb-1996";
    @Test
    void test_parseDate_good_case2()
    {
        LocalDate expected = LocalDate.of(1996, 2, 21);
        LocalDate date = parser.parseDate(case2);
        assertEquals(expected, date);
    }

    String case3 = "04-Mar-1996";
    @Test
    void test_parseDate_good_case3()
    {
        LocalDate expected = LocalDate.of(1996, 3, 4);
        LocalDate date = parser.parseDate(case3);
        assertEquals(expected, date);
    }

    String case4 = "01-Apr-1996";
    @Test
    void test_parseDate_good_case4()
    {
        LocalDate expected = LocalDate.of(1996, 4, 1);
        LocalDate date = parser.parseDate(case4);
        assertEquals(expected, date);
    }
}