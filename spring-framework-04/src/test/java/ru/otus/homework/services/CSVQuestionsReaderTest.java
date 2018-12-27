package ru.otus.homework.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.otus.homework.models.*;
import ru.otus.outside.exeptions.IORuntimeException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.homework.services.TestDataQuestions.QUEST;
import static ru.otus.homework.services.TestDataQuestions.createTestQuestions;
import static ru.otus.outside.utils.IOHelperTestHelper.mockUp_IOHelper_getBufferedReader;
import static ru.otus.outside.utils.IOHelperTestHelper.mockUp_IOHelper_getBufferedReader_IORuntimeException;

@DisplayName("Class CSVQuestionsReader")
class CSVQuestionsReaderTest
{
    private CSVQuestionsReader reader;

    @Test
    @DisplayName("is instantiated with new CSVQuestionsReader()")
    void isInstantiatedWithNew() {
        new CSVQuestionsReader();
    }

    @Nested
    @DisplayName("when new")
    class WhenNew
    {
        @BeforeEach
        void createNewQuestion()
        {
            reader = new CSVQuestionsReader();
        }

        @Test
        @DisplayName("default values in CSVQuestionsReader()")
        void defaults()
        {
            assertThat(reader).hasFieldOrPropertyWithValue("filename", "");
            assertThat(reader).hasFieldOrProperty("questions").isNotNull();
        }

        @Test
        @DisplayName("Setter and getter for questions")
        void testSetGetQuestions()
        {
            QuestionsImpl questions = new QuestionsImpl();
            QuestionImpl question = new QuestionImpl();
            List<Question> questionList = new ArrayList<>(Collections.singletonList(question));
            questions.setQuestions(questionList);
            reader.setQuestions(questions);
            assertThat(reader).hasFieldOrPropertyWithValue("questions", questions);
            assertEquals(1, reader.getQuestions().size());
        }

        @Test
        @DisplayName("read(null, null) throws ExceptionIO")
        void testNullPointerException()
        {
            assertThrows(NullPointerException.class, () -> reader.read());
        }
    }

    @Nested
    @DisplayName("when read")
    class WhenRead
    {
        public static final String questEmpty = "";
        public static final String questOne = "\"Q1?\"";
        public static final String questBadLine = "\"Q1?\",\"A1.1\"";

        private AnswerFactory getAnswer;
        private QuestionFactory getQuestion;

        @BeforeEach
        void createNewQuestion()
        {
            QuizFactory quizFactory = new QuizFactoryImpl(new AnswerFactoryImpl(), new QuestionFactoryImpl());
            reader = new CSVQuestionsReader(new QuestionsImpl(), "test.csv", quizFactory);
        }

        @Test
        @DisplayName("mockit filename in CSVQuestionsReader()")
        void defaults() {
            assertThat(reader).hasFieldOrPropertyWithValue("filename", "test.csv");
        }

        @Test
        @DisplayName("QuestionsReader::read from empty stream")
        void readEmpty()
        {
            mockUp_IOHelper_getBufferedReader(questEmpty);

            reader.read();
            assertEquals(0, reader.getQuestions().size());
        }

        @Test
        @DisplayName("QuestionsReader::read only question")
        void readOnlyQuestion()
        {
            mockUp_IOHelper_getBufferedReader(questOne);

            reader.read();
            assertEquals(1, reader.getQuestions().size());
        }

        @Test
        @DisplayName("QuestionsReader::read bad line")
        void readBadLine()
        {
            mockUp_IOHelper_getBufferedReader(questBadLine);

            assertThrows(ArrayIndexOutOfBoundsException.class, () -> reader.read());
        }

        @Test
        @DisplayName("QuestionsReader::read one line")
        void readOneLine()
        {
            mockUp_IOHelper_getBufferedReader(QUEST);

            reader.read();
            Questions expected = createTestQuestions();
            assertThat(reader).hasFieldOrPropertyWithValue("questions", expected);
        }

        @Test
        @DisplayName("QuestionsReader::read throw IOException inboxed to IORuntimeException")
        void readNull()
        {
            mockUp_IOHelper_getBufferedReader_IORuntimeException();

            assertThrows(IORuntimeException.class, () -> reader.read());
        }
    }
}