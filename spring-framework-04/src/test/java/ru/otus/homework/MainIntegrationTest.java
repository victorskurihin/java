package ru.otus.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.homework.models.AnswerImpl;
import ru.otus.homework.models.QuestionImpl;
import ru.otus.homework.models.Questions;
import ru.otus.homework.services.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@DisplayName("Main Integration Test")
class MainIntegrationTest
{
    @Autowired
    private MessagesService msg;

    @MockBean
    Main main;

    @Test
    @DisplayName("MessagesService: msg Bean")
    void testMessagesService()
    {
        assertEquals("Hello world.", msg.get("hello_world"));
    }

    @Autowired
    private Questions questions;

    @Test
    @DisplayName("Questions: questions Bean")
    void testQuestions()
    {
        assertEquals(0, questions.size());
    }

    @Autowired
    QuestionsReader reader;

    @Test
    @DisplayName("QuestionsReader: reader Bean")
    void testQuestionsReader()
    {
        assertThat(reader).hasFieldOrPropertyWithValue("questions", questions);
    }

    @Autowired
    QuizExecutor tester;

    @Test
    @DisplayName("QuizExecutor: tester Bean")
    void testQuizExecutor()
    {
        assertThat(tester).hasFieldOrPropertyWithValue("questions", questions);
    }

    @Autowired
    AnswerFactory answerFactory;

    @Test
    @DisplayName("AnswerFactory: answerFactory BeanFactory")
    void testAnswerFactory()
    {
        assertEquals(new AnswerImpl(), answerFactory.getObject());
    }

    @Autowired
    QuestionFactory questionFactory;

    @Test
    @DisplayName("QuestionFactory: questionFactory BeanFactory")
    void testQuestionFactory()
    {
        assertEquals(new QuestionImpl(), questionFactory.getObject());
    }
}