package ru.otus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import ru.otus.exeptions.ExceptionIO;
import ru.otus.models.IAnswer;
import ru.otus.models.IQuestion;
import ru.otus.services.ConsoleTester;
import ru.otus.services.IReader;

import java.util.function.Supplier;

public class Application
{
    private static final Logger LOGGER = LogManager.getLogger(Application.class.getName());

    private ConsoleTester exam;

    public Application(ApplicationContext context)
    {
        Supplier<IAnswer> getAnswerBean = () -> context.getBean("answer", IAnswer.class);
        Supplier<IQuestion> getQuestionBean = () -> context.getBean("question", IQuestion.class);
        IReader reader = context.getBean("reader", IReader.class);

        try {
            reader.read(getQuestionBean, getAnswerBean);
        }
        catch (ExceptionIO exceptionIO) {
            LOGGER.error(exceptionIO);
        }
        exam = context.getBean("tester", ConsoleTester.class);
    }

    public void run()
    {
        exam.run();
    }
}
