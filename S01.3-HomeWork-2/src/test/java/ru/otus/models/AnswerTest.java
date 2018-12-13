package ru.otus.models;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Class Answer")
class AnswerTest
{
    Answer answer;

    @Test
    @DisplayName("is instantiated with new Answer()")
    void isInstantiatedWithNew() {
        new Answer();
    }

    @Nested
    @DisplayName("when new")
    class WhenNew
    {
        @BeforeEach
        void createNewAnswer() {
            answer = new Answer();
        }

        @Test
        @DisplayName("default values in Answer()")
        void defaults() {
            assertThat(answer).hasFieldOrPropertyWithValue("answer", null);
            assertThat(answer).hasFieldOrPropertyWithValue("score", 0);
        }

        @Test
        @DisplayName("Setter and getter for answer")
        void testAnswer()
        {
            answer.setAnswer("test");
            assertThat(answer).hasFieldOrPropertyWithValue("answer", "test");
            assertEquals("test", answer.getAnswer());
        }

        @Test
        @DisplayName("Setter and getter for score")
        void testScore()
        {
            answer.setScore(13);
            assertThat(answer).hasFieldOrPropertyWithValue("score", 13);
            assertEquals(13, answer.getScore());
        }

        @Test
        @DisplayName("Equals for class Answer and hashCode")
        void testEquals()
        {
            Answer expected = new Answer();
            assertTrue(answer.equals(expected));
            expected.setAnswer("test");
            expected.setScore(13);
            answer.setAnswer("test");
            answer.setScore(13);
            assertTrue(answer.equals(expected));
            assertFalse(answer.equals(null));
            assertFalse(answer.equals(new Object()));
            assertEquals(expected.hashCode(), answer.hashCode());
        }

        @Test
        @DisplayName("The length of Answer::toString is great than zero")
        void testToString()
        {
            assertTrue(answer.toString().length() > 0);
        }
    }
}