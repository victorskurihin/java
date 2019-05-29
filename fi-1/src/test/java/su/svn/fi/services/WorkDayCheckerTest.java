package su.svn.fi.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class WorkDayCheckerTest
{
    WorkDayChecker workDayChecker;

    @BeforeEach
    void setUp()
    {
        workDayChecker = new WorkDayChecker();
    }

    LocalDate trueCase1 = LocalDate.of(2014, 12, 1);
    @Test
    void test_true_case1()
    {
        assertTrue(workDayChecker.isValid(trueCase1));
    }

    LocalDate trueCase2 = LocalDate.of(2014, 12, 2);
    @Test
    void test_true_case2()
    {
        assertTrue(workDayChecker.isValid(trueCase2));
    }

    LocalDate falseCase1 = LocalDate.of(2014, 12, 7);
    @Test
    void test_false_case1()
    {
        assertFalse(workDayChecker.isValid(falseCase1));
    }

    LocalDate falseCase2 = LocalDate.of(2019, 5, 30);
    @Test
    void test_false_case2()
    {
        assertFalse(workDayChecker.isValid(falseCase2));
    }
}