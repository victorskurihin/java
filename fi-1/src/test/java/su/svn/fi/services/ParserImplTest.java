package su.svn.fi.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import su.svn.fi.models.Instrument;

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

    String dateCase1 = "01-Jan-1996";
    @Test
    void test_parseDate_good_case1()
    {
        LocalDate expected = LocalDate.of(1996, 1, 1);
        LocalDate date = parser.parseDate(dateCase1);
        assertEquals(expected, date);
    }

    String dateCase2 = "21-Feb-1996";
    @Test
    void test_parseDate_good_case2()
    {
        LocalDate expected = LocalDate.of(1996, 2, 21);
        LocalDate date = parser.parseDate(dateCase2);
        assertEquals(expected, date);
    }

    String dateCase3 = "04-Mar-1996";
    @Test
    void test_parseDate_good_case3()
    {
        LocalDate expected = LocalDate.of(1996, 3, 4);
        LocalDate date = parser.parseDate(dateCase3);
        assertEquals(expected, date);
    }

    String dateCase4 = "01-Apr-1996";
    @Test
    void test_parseDate_good_case4()
    {
        LocalDate expected = LocalDate.of(1996, 4, 1);
        LocalDate date = parser.parseDate(dateCase4);
        assertEquals(expected, date);
    }

    String lineCase1 = "INSTRUMENT1,01-Jan-1996,2.4655";
    @Test
    void test_parse_good_case1()
    {
        Instrument expected = new Instrument(
            "INSTRUMENT1", 0, LocalDate.of(1996, 1, 1), 2.4655
            );
        Instrument instrument = parser.parse(lineCase1);
        assertEquals(expected, instrument);
    }

    String lineCase2 = "INSTRUMENT2,09-Jan-1996,9.342281879";
    @Test
    void test_parse_good_case2()
    {
        Instrument expected = new Instrument(
            "INSTRUMENT2", 0, LocalDate.of(1996, 1, 9), 9.342281879
        );
        Instrument instrument = parser.parse(lineCase2);
        assertEquals(expected, instrument);
    }

    String lineCase3 = "INSTRUMENT3,23-May-1997,115.405";
    @Test
    void test_parse_good_case3()
    {
        Instrument expected = new Instrument(
            "INSTRUMENT3", 0, LocalDate.of(1997, 5, 23), 115.405
        );
        Instrument instrument = parser.parse(lineCase3);
        assertEquals(expected, instrument);
    }
}