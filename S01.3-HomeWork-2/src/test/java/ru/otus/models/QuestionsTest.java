package ru.otus.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Class Questions")
class QuestionsTest
{
    Questions questions;


    @Test
    @DisplayName("is instantiated with new Questions()")
    void isInstantiatedWithNew() {
        new Questions();
    }

    @Nested
    @DisplayName("when new")
    class WhenNew
    {
        @BeforeEach
        void createNewQuestions() {
            questions = new Questions();
        }

        @Test
        @DisplayName("default values in Questions()")
        void defaults() {
            assertThat(questions).hasFieldOrPropertyWithValue("activeQuestion", 0);
            assertThat(questions).hasFieldOrPropertyWithValue("score", 0);
            assertThat(questions).hasFieldOrProperty("questions").isNotNull();
            assertTrue(questions.getQuestions().isEmpty());
        }

        @Test
        @DisplayName("Setter and getter for score")
        void testScore()
        {
            questions.setScore(13);
            assertThat(questions).hasFieldOrPropertyWithValue("score", 13);
            assertEquals(13, questions.getScore());
        }

        @Test
        @DisplayName("The method size")
        public void testSize()
        {
            assertEquals(0, questions.size());
            questions.setQuestions(Collections.singletonList(new Question()));
            assertEquals(1, questions.size());
        }

        private Question[] getTestDataQuestions(Questions testDataSet)
        {
            Question[] questions = new Question[]{ new Question(), new Question(), new Question()};
            questions[0].setQuestion("question0");
            questions[1].setQuestion("question1");
            questions[1].setAnswers(Collections.singletonList(new Answer()));
            testDataSet.setQuestions(Arrays.asList(questions));

            return questions;
        }

        @Test
        public void testIterate()
        {
            Question[] questionsArray = getTestDataQuestions(questions);

            int i = 0;
            Iterator<IQuestion> questionIterator = questions.iterator();
            while (questionIterator.hasNext()) {
                Question question = (Question) questionIterator.next();
                assertTrue(question == questionsArray[i++]);
                assertThat(questions).hasFieldOrPropertyWithValue("activeQuestion", i);
            }
            assertTrue(i > 0);
        }

        @Test
        @DisplayName("Equals for class Questions and hashCode")
        public void testEquals()
        {
            Questions expected = new Questions();
            assertEquals(expected.hashCode(), questions.hashCode());

            getTestDataQuestions(questions);
            getTestDataQuestions(expected);
            assertEquals(expected.hashCode(), questions.hashCode());
            assertTrue(questions.equals(expected));
            assertFalse(questions.equals(null));
            assertFalse(questions.equals(new Object()));
            assertEquals(expected.hashCode(), questions.hashCode());
        }

        @Test
        @DisplayName("The length of string from Questions::toString is great than zero")
        public void testToString()
        {
            assertTrue(questions.toString().length() > 0);
        }
    }
}