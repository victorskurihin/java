package ru.otus.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.models.Answer;
import ru.otus.models.Question;
import ru.otus.models.SetOfQuestions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class ConsoleExamTest
{

    private final static String str1 = "";
    private final static String str2 = "\n";

    @Before
    public void setUp() throws Exception
    {
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test(expected = NullPointerException.class)
    public void test0()
    {
        ConsoleExam exam = new ConsoleExam(null, null);

        exam.run();
    }

    public static InputStream getInputStream(String s)
    {
        return new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
    }

    @Test
    public void test1()
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ConsoleExam exam = new ConsoleExam(getInputStream(str1), baos);

        exam.setSetOfQuestions(new SetOfQuestions());
        exam.run();
    }

//    @Test
//    public void test2()
//    {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        // ConsoleExam exam = new ConsoleExam(getInputStream(str2), baos);
//        ConsoleExam exam = new ConsoleExam(System.in, System.out);
//
//        SetOfQuestions setOfQuestions = new SetOfQuestions();
//        Question question = new Question();
//        question.addAnswer(new Answer());
//        question.setQuestion("test");
//        setOfQuestions.setQuestions(Collections.singletonList(question));
//        exam.setSetOfQuestions(setOfQuestions);
//        exam.run();
//    }
}