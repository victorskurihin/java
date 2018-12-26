package ru.otus.homework.shell;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.otus.homework.models.Questions;
import ru.otus.homework.models.QuestionsImpl;
import ru.otus.homework.services.*;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.homework.services.MessagesServiceImplTest.DEFAULT_SLOCALE;
import static ru.otus.homework.services.MessagesServiceImplTest.MESSAGE_SOURCE;

/*
    public QuizCommands(IOService ios, MessagesService msg,
                        @Qualifier("tester") QuizExecutor quizExecutor,
                        @Qualifier("reader") QuestionsReader questionsReader,
                        AnswerFactory answerFactory, QuestionFactory questionFactory)
 */

@DisplayName("Class QuizCommandsTest")
class QuizCommandsTest
{
    private final IOService ios = new IOServiceSystem();

    private final Questions questions = new QuestionsImpl();

    private final MessagesService msg = new MessagesServiceImpl(DEFAULT_SLOCALE, MESSAGE_SOURCE);

    private final QuizFactory quizFactory = new QuizFactoryImpl(new AnswerFactoryImpl(), new QuestionFactoryImpl());

    private QuizCommands quizCommands;

    @Test
    @DisplayName("is instantiated with new CSVQuestionsReader()")
    void isInstantiatedWithNew() {
        new QuizCommands(ios, msg, null, null);
    }

    /*
    @Nested
    @DisplayName("when new")
    class WhenNew
    {
        @BeforeEach
        void createNewQuestion()
        {
            quizCommands = new QuizCommands();
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
            assertThrows(NullPointerException.class, () -> reader.read(null, null));
        }
    }
    */
}