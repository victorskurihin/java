package ru.otus.homework.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.otus.homework.models.AnswerImpl;
import ru.otus.homework.models.QuestionImpl;
import ru.otus.homework.models.Questions;
import ru.otus.homework.models.QuestionsImpl;
import ru.otus.outside.exeptions.EmptyResourceRuntimeException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.outside.utils.IOHelperTestHelper.mockUp_Scanner_nextInt;
import static ru.otus.outside.utils.IOHelperTestHelper.mockUp_Scanner_nextInt_InputMismatchException;
import static ru.otus.outside.utils.IOHelperTestHelper.mockUp_Scanner_nextInt_NoSuchElementException;

@DisplayName("Class ConsoleQuizExecutor")
class ConsoleQuizExecutorTest
{
    public static final String TEST = "test";

    private final InputStream in = System.in;

    ConsoleQuizExecutor executor;

    @Test
    @DisplayName("is instantiated with new ConsoleQuizExecutor()")
    void isInstantiatedWithNew() {
        new ConsoleQuizExecutor(in, null, null, null);
    }

    @Nested
    @DisplayName("when new")
    class WhenNew
    {
        private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        private PrintStream ps;
        private Questions questions;
        private MessagesServiceImpl msg;

        @BeforeEach
        void createNewQuestion()
        {
            try {
                ps = new PrintStream(baos, true, "UTF-8");
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            questions = new QuestionsImpl();
            msg = new MessagesServiceImpl("en_US", MessagesServiceImplTest.createTestMessageSource());
            executor = new ConsoleQuizExecutor(in, ps, questions, msg);
        }

        @Test
        @DisplayName("default values in ConsoleQuizExecutor()")
        void defaults()
        {
            assertThat(executor).hasFieldOrProperty("scanner").isNotNull();
            assertThat(executor).hasFieldOrPropertyWithValue("out", ps);
            assertThat(executor).hasFieldOrPropertyWithValue("questions", questions);
            assertThat(executor).hasFieldOrPropertyWithValue("msg", msg);
        }

        @Test
        @DisplayName("Setter and getter for questions")
        void testSetGetQuestions()
        {
            QuestionsImpl expected = new QuestionsImpl();
            executor.setQuestions(new QuestionsImpl());
            assertEquals(expected, executor.getQuestions());
        }

        @Test
        @DisplayName("Setter and getter for questions")
        void testGetScore()
        {
            executor.setQuestions(new QuestionsImpl());
            assertEquals(0, executor.getScore());
        }

        @Test
        @DisplayName("Show question")
        void testShowQuestion()
        {
            executor.showQuestion(TEST);
            String test = new String(baos.toByteArray(), StandardCharsets.UTF_8);
            assertTrue(test.length() > TEST.length());
        }

        @Test
        @DisplayName("Show answers")
        public void testShowAnswers()
        {
            AnswerImpl answerImpl = new AnswerImpl();
            answerImpl.setAnswer(TEST);
            executor.showAnswers(Collections.singletonList(answerImpl));
            String test = new String(baos.toByteArray(), StandardCharsets.UTF_8);
            assertTrue(test.length() > TEST.length());
        }
    }

    @Nested
    @DisplayName("when readAnswer")
    class WhenReadAnswer
    {
        private PrintStream ps;
        private MessagesServiceImpl msg;
        private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        @BeforeEach
        void createNewQuestion()
        {
            try {
                ps = new PrintStream(baos, true, "UTF-8");
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            msg = new MessagesServiceImpl("en_US", MessagesServiceImplTest.createTestMessageSource());
            executor = new ConsoleQuizExecutor(in, ps, null, msg);
        }

        @Test
        @DisplayName("run1")
        public void testRun1()
        {
            mockUp_Scanner_nextInt(2);
            AnswerImpl answerImpl = new AnswerImpl();
            answerImpl.setAnswer(TEST);
            assertEquals(-1, executor.readAnswer(Collections.singletonList(answerImpl)));
        }

        @Test
        @DisplayName("run throw InputMismatchException")
        public void testRunInputMismatchException()
        {
            mockUp_Scanner_nextInt_InputMismatchException();
            assertEquals(-2, executor.readAnswer(null));
        }

        @Test
        @DisplayName("run throw NoSuchElementException")
        public void testRunInputNoSuchElementException()
        {
            mockUp_Scanner_nextInt_NoSuchElementException();
            assertEquals(-3, executor.readAnswer(null));
        }
    }

    @Nested
    @DisplayName("when run")
    class WhenRun
    {
        private final String INPUT = "First" + ConsoleQuizExecutor.NL
                                             + "SurName" + ConsoleQuizExecutor.NL
                                             + "1" + ConsoleQuizExecutor.NL;
        private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        private PrintStream ps;
        private Questions questions;
        private MessagesServiceImpl msg;

        private void provideInput(String data) {
            System.setIn(new ByteArrayInputStream(data.getBytes()));
        }

        @BeforeEach
        void createNewQuestion()
        {
            try {
                ps = new PrintStream(baos, true, "UTF-8");
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            questions = new QuestionsImpl();
            msg = new MessagesServiceImpl("en_US", MessagesServiceImplTest.createTestMessageSource());
            provideInput(INPUT);
            executor = new ConsoleQuizExecutor(System.in, ps, questions, msg);
        }

        @Test
        @DisplayName("run throw EmptyResourceRuntimeException")
        public void testRunEmptyResourceRuntimeException()
        {
            QuestionsImpl questionsImpl = new QuestionsImpl();
            executor.setQuestions(questionsImpl);
            assertThrows(EmptyResourceRuntimeException.class, () -> executor.run());
            System.setIn(in);
        }

        @Test
        @DisplayName("run")
        public void testRun()
        {
            AnswerImpl answerImpl = new AnswerImpl();
            answerImpl.setAnswer("test");
            answerImpl.setScore(13);
            QuestionImpl questionImpl = new QuestionImpl();
            questionImpl.setQuestion("test");
            questionImpl.setAnswers(Collections.singletonList(answerImpl));
            QuestionsImpl questionsImpl = new QuestionsImpl();
            questionsImpl.addQuestion(questionImpl);
            executor.setQuestions(questionsImpl);
            executor.run();
            System.setIn(in);
            assertEquals(13, executor.getScore());
        }
    }
}