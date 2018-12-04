package ru.otus.models;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;

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

    @Test
    public void testQuestions()
    {
        setOfQuestions.setQuestions(new LinkedList<>());
        Assert.assertEquals(new LinkedList<>(), setOfQuestions.getQuestions());
    }

    @Test
    public void testScore()
    {
        setOfQuestions.setScore(13);
        Assert.assertEquals(13, setOfQuestions.getScore());
        setOfQuestions.addScore(4);
        Assert.assertEquals(17, setOfQuestions.getScore());
    }

    @Test
    public void testSize()
    {
        Assert.assertEquals(0, setOfQuestions.size());
        setOfQuestions.setQuestions(Collections.singletonList(new Question()));
        Assert.assertEquals(1, setOfQuestions.size());
    }
}