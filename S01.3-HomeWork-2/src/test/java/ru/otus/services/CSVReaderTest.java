package ru.otus.services;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.exeptions.ExceptionIO;
import ru.otus.models.*;

import java.util.function.Supplier;

public class CSVReaderTest
{
    CSVReader reader;

    @Before
    public void setUp() throws Exception
    {
        reader = new CSVReader("quests.csv");
    }

    @After
    public void tearDown() throws Exception
    {
        reader = null;
    }

    @Test
    public void testSetOfQuestions() {
        Questions expected = new Questions();
        reader.setQuestions(new Questions());
        Assert.assertEquals(expected, reader.getQuestions());
    }

    @Test(expected = NullPointerException.class)
    public void testNullPointerException() throws ExceptionIO
    {
        reader.read(null, null);
    }

    @Test(expected = ExceptionIO.class)
    public void test() throws ExceptionIO
    {
        Supplier<IAnswer> getAnswer = () -> new Answer();
        Supplier<IQuestion> getQuestion = () -> new Question();
        CSVReader test = new CSVReader();
        test.read(getQuestion, getAnswer);
    }
}