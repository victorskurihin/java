package ru.otus.homework;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.homework.configs.YamlProperties;
import ru.otus.homework.services.*;
import ru.otus.outside.exeptions.EmptyResourceRuntimeException;
import ru.otus.outside.exeptions.IORuntimeException;

@SpringBootApplication
@EnableConfigurationProperties(YamlProperties.class)
public class Main implements CommandLineRunner
{
    private static final Logger LOGGER = LogManager.getLogger(Main.class.getName());

    private QuizExecutor tester;

    private MessagesService msg;

    private void exitOnExceptionResource(Throwable exception)
    {
        System.err.println(
            msg.get("exception_resource", new Object[] {exception.toString(), exception.getMessage()})
        );
        LOGGER.error(exception);
        System.exit(-1);
    }

    @Autowired
    public Main(@Qualifier("msg") MessagesService msg,
                @Qualifier("reader") QuestionsReader questionsReader,
                @Qualifier("tester") QuizExecutor quizExecutor,
                AnswerFactory answerFactory, QuestionFactory questionFactory
    ){
        this.msg = msg;
        System.out.print(msg.get("hello_world") + " ");

        try {
            questionsReader.read(answerFactory, questionFactory);
        }
        catch (IORuntimeException exceptionIO) {
            LOGGER.error(exceptionIO);
        }
        catch (ArrayIndexOutOfBoundsException exception) {
            exitOnExceptionResource(exception);
        }
        tester = quizExecutor;
    }

    @Override
    public void run(String... args) throws Exception
    {
        try {
            tester.run();
        }
        catch (EmptyResourceRuntimeException exception) {
            exitOnExceptionResource(exception);
        }
    }

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(Main.class);
        app.setBannerMode(Banner.Mode.CONSOLE);
        app.run(args);
    }
}
