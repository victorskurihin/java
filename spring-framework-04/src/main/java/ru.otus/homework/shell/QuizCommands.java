package ru.otus.homework.shell;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.services.*;
import ru.otus.outside.exeptions.IORuntimeException;

@ShellComponent
public class QuizCommands
{
    private static final Logger LOGGER = LogManager.getLogger(QuizCommands.class.getName());

    private MessagesService msg;

    private QuizExecutor quizExecutor;

    private QuestionsReader questionsReader;

    public QuizCommands(IOService ios, MessagesService msg,
                        @Qualifier("tester") QuizExecutor quizExecutor,
                        @Qualifier("reader") QuestionsReader questionsReader)
    {
        ios.getOut().println(msg.get("start_quiz"));
        ios.getOut().println(msg.get("exit_quit"));
        this.msg = msg;
        this.quizExecutor = quizExecutor;
        this.questionsReader = questionsReader;
    }

    private void exitOnExceptionResource(Throwable exception)
    {
        System.err.println(
            msg.get("exception_resource", new Object[] {exception.toString(), exception.getMessage()})
        );
        LOGGER.error(exception);
        System.exit(-1);
    }

    @ShellMethod(key = "quiz", value = "Start quiz.")
    public void startQuiz()
    {
        try {
            questionsReader.read();
        }
        catch (IORuntimeException exceptionIO) {
            LOGGER.error(exceptionIO);
        }
        catch (ArrayIndexOutOfBoundsException exception) {
            exitOnExceptionResource(exception);
        }
        quizExecutor.run();
    }

    @ShellMethod("Translate text from one language to another.")
    public String locale(@ShellOption(defaultValue = "en_US") String to)
    {
        return msg.setLocale(to);
    }
}
