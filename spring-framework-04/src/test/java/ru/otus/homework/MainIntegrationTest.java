package ru.otus.homework;

import org.jline.reader.LineReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.shell.Shell;
import ru.otus.homework.models.AnswerImpl;
import ru.otus.homework.models.QuestionImpl;
import ru.otus.homework.models.Questions;
import ru.otus.homework.services.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("Main Integration Test")
class MainIntegrationTest
{
    @MockBean
    LineReader lineReader;

    @MockBean
    PromptProvider promptProvider;

    @MockBean
    Shell shell;

    @MockBean
    Main main;

    @Autowired
    private MessagesService msg;

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

/*
AttributedCharSequenceResultHandler
attributedCharSequenceResultHandler;
Clear
clear;
CommandValueProvider
commandValueProvider;
Completer
completer;
DefaultResultHandler
defaultResultHandler;
DefaultValidator
defaultValidator;
EnumValueProvider
enumValueProvider;
FileValueProvider
fileValueProvider;
Help
help;
History
history;
HistoryCommand
historyCommand;
InteractiveApplicationRunner
interactiveApplicationRunner;
IterableResultHandler
iterableResultHandler;
LineReader
lineReader;
MainResultHandler
mainResultHandler;
MethodValidationPostProcessor
methodValidationPostProcessor
org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration
org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration
org.springframework.shell.jline.JLineShellAutoConfiguration
org.springframework.shell.jline.JLineShellAutoConfiguration
org.springframework.shell.jline.JLineShellAutoConfiguration$HistoryConfiguration
org.springframework.shell.jline.JLineShellAutoConfiguration$HistoryConfiguration
org.springframework.shell.result.ResultHandlerConfig
org.springframework.shell.result.ResultHandlerConfig
org.springframework.shell.SpringShellAutoConfiguration
org.springframework.shell.SpringShellAutoConfiguration
org.springframework.shell.standard.commands.StandardCommandsAutoConfiguration
org.springframework.shell.standard.commands.StandardCommandsAutoConfiguration
org.springframework.shell.standard.StandardAPIAutoConfiguration
org.springframework.shell.standard.StandardAPIAutoConfiguration
ParameterValidationExceptionResultHandler
parameterValidationExceptionResultHandler;
Parser
parser;
PromptProvider
promptProvider;
Quit
quit;
Script
script;
ScriptApplicationRunner
scriptApplicationRunner;
Shell
shell;
ShellConversionService
shellConversionService;
Stacktrace
stacktrace;
StandardMethodTargetResolver
standardMethodTargetResolver;
StandardParameterResolver
standardParameterResolver;
Terminal
terminal;
TerminalSizeAwareResultHandler
terminalSizeAwareResultHandler;
ThrowableResultHandler
throwableResultHandler;
 */
