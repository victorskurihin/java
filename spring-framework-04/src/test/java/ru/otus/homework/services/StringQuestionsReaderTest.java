package ru.otus.homework.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.otus.homework.models.Questions;
import ru.otus.homework.models.QuestionsImpl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.homework.services.TestDataQuestions.QUEST;
import static ru.otus.homework.services.TestDataQuestions.createTestQuestions;

@DisplayName("Class StringQuestionsReaderTest")
class StringQuestionsReaderTest
{
    private StringQuestionsReader reader;

    @Test
    @DisplayName("is instantiated with new StringQuestionsReaderTest()")
    void isInstantiatedWithNew() {
        new StringQuestionsReader();
    }

    @Nested
    @DisplayName("when new")
    class WhenNew
    {
        @BeforeEach
        void createNewQuestion()
        {
            reader = new StringQuestionsReader();
        }

        @Test
        @DisplayName("default values in StringQuestionsReader()")
        void defaults()
        {
            assertThat(reader).hasFieldOrPropertyWithValue("data", "");
            assertThat(reader).hasFieldOrProperty("questions").isNotNull();
        }

        @Test
        @DisplayName("Setter and getter for questions")
        void testSetGetQuestions()
        {
            QuestionsImpl questions = new QuestionsImpl();
            reader.setQuestions(questions);
            assertThat(reader).hasFieldOrPropertyWithValue("questions", questions);
            assertTrue(questions == reader.getQuestions());
        }
    }

    @Nested
    @DisplayName("when read")
    class WhenRead
    {
        private AnswerFactory getAnswer;
        private QuestionFactory getQuestion;

        @BeforeEach
        void createNewQuestion()
        {
            QuizFactory quizFactory = new QuizFactoryImpl(new AnswerFactoryImpl(), new QuestionFactoryImpl());
            reader = new StringQuestionsReader(new QuestionsImpl(), QUEST, quizFactory);
        }

        @Test
        @DisplayName("mockit filename in StringQuestionsReader()")
        void defaults() {
            assertThat(reader).hasFieldOrPropertyWithValue("data", QUEST);
        }

        @Test
        @DisplayName("QuestionsReader::read one line")
        void readOneLine()
        {
            reader.read();
            assertEquals(1, reader.getQuestions().size());
            Questions expected = createTestQuestions();
            assertThat(reader).hasFieldOrPropertyWithValue("questions", expected);
        }
    }
}