package ru.otus.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Class Question")
class QuestionTest
{
    Question question;

    @Test
    @DisplayName("is instantiated with new Question()")
    void isInstantiatedWithNew() {
        new Question();
    }

    @Nested
    @DisplayName("when new")
    class WhenNew
    {
        @BeforeEach
        void createNewQuestion() {
            question = new Question();
        }

        @Test
        @DisplayName("default values in Answer()")
        void defaults() {
            assertThat(question).hasFieldOrPropertyWithValue("question", null);
            assertThat(question).hasFieldOrProperty("answers").isNotNull();
            assertTrue(question.getAnswers().isEmpty());
        }

        @Test
        @DisplayName("Setter and getter for question")
        void testAnswer()
        {
            question.setQuestion("test");
            assertThat(question).hasFieldOrPropertyWithValue("question", "test");
            assertEquals("test", question.getQuestion());
        }

        @Test
        @DisplayName("Setter and getter for answers")
        void testAnswers()
        {
            Answer answer = new Answer();
            List<IAnswer> answers = new ArrayList<>(Collections.singletonList(answer));
            question.setAnswers(answers);
            assertThat(question).hasFieldOrPropertyWithValue("answers", answers);
            assertEquals(1, question.getAnswers().size());
            assertTrue(question.getAnswers().contains(answer));
        }

        @Test
        @DisplayName("Equals for class Question and hashCode")
        void testEquals()
        {
            Question expected = new Question();
            assertEquals(expected.hashCode(), question.hashCode());
            expected.addAnswer(new Answer());
            question.addAnswer(new Answer());
            expected.setQuestion("test");
            question.setQuestion("test");
            assertTrue(question.equals(expected));
            assertFalse(question.equals(null));
            assertFalse(question.equals(new Object()));
            assertEquals(expected.hashCode(), question.hashCode());
        }

        @Test
        @DisplayName("The length of string from Question::toString is great than zero")
        void testToString()
        {
            assertTrue(question.toString().length() > 0);
        }
    }
}