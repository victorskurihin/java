package ru.otus.models;

import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.*;

public class SetOfQuestionsTest
{
    private SetOfQuestions setOfQuestions;

    @Before
    public void setUp() throws Exception
    {
        setOfQuestions = new SetOfQuestions();
    }

    @After
    public void tearDown() throws Exception
    {
        setOfQuestions = null;
    }
}