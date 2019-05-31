package su.svn.fi.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import su.svn.fi.models.Instrument;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class CalculationEngineOtherTest
{
    CalculationEngineOther engineOther;

    @BeforeEach
    void setUp()
    {
        engineOther = new CalculationEngineOther("INSTRUMENT4");
        engineOther.init();
    }

    @AfterEach
    void shutdown()
    {
        engineOther.shutdown();
    }

    @Test
    void test_parseDate_good_case1() throws InterruptedException
    {
        for (int i = 1; i < 31; ++i) {
            Instrument test = new Instrument(
                "INSTRUMENT3", i, LocalDate.of(2014, Month.NOVEMBER, i), i
            );
            engineOther.apply(test);
            Thread.sleep(100);
        }
        Thread.sleep(500);
        assertTrue(Math.abs(255.0 - engineOther.getResult()) < Double.MIN_VALUE);
    }
}