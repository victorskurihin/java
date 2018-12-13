package ru.otus.services;

import ru.otus.models.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

@Named("tester")
public class ConsoleTester implements ITester
{
    public final static String NL = System.lineSeparator();

    private Scanner scanner;

    private PrintStream out;

    private String firstName;

    private String surName;

    @Inject
    @Named("questions")
    private IQuestions setOfQuestions;

    @Inject
    public ConsoleTester(InputStream in, PrintStream out)
    {
        this.out = out;
        this.scanner = new Scanner(in);
    }

    public IQuestions getSetOfQuestions()
    {
        return setOfQuestions;
    }

    public void setSetOfQuestions(IQuestions setOfQuestions)
    {
        this.setOfQuestions = setOfQuestions;
    }

    void showQuestion(String question)
    {
        out.println("question: " + question);
    }

    void showAnswers(List<IAnswer> answers)
    {
        int i = 1;
        for (IAnswer answer : answers) {
            out.println(i + ": " + answer.getAnswer());
            i++;
        }
    }

    int readAnswer(List<IAnswer> answers)
    {
        out.print("Enter the answer number: ");

        try {
            int result = scanner.nextInt();

            if (result > answers.size()) {
                return -1;
            }

            return result;
        } catch (InputMismatchException e) {
            return -2;
        } catch (NoSuchElementException e) {
            return -3;
        } catch (IllegalStateException e) {
            return -4;
        } catch (Exception e) {
            return Integer.MIN_VALUE; // Where got unknow error. No need show the error message.
        }
    }

    private String getErrorMessage(int answerIndex)
    {
        switch (answerIndex) {
            case -1:
                return "The number is not in the answer's range";
            case -2:
            case -3:
            case -4:
                return "Input Mismatch. Token does not match the Integer regular expression, or is out of range ";
            default:
                return "Unknow error!!!";
        }
    }

    private void showErrorMessage(int answerIndex)
    {
        out.println("Error message: " + getErrorMessage(answerIndex));
    }

    private void readPersonalInfo()
    {
        out.print(NL + "Inter your first name: ");
        firstName = scanner.nextLine();
        out.print("Inter your surname: ");
        surName = scanner.nextLine();
    }

    public void run()
    {
        out.println("Enter 0 for exit.");
        readPersonalInfo();

        for (IQuestion question : setOfQuestions) {

            out.println();
            int answerIndex = 0;

            do {
                showQuestion(question.getQuestion());
                showAnswers(question.getAnswers());
                answerIndex = readAnswer(question.getAnswers());

                if (answerIndex < 0) {
                    showErrorMessage(answerIndex);
                }
            } while (answerIndex < 0);

            if (0 == answerIndex) return;

            setOfQuestions.addScore(question.getAnswers().get(answerIndex - 1).getScore());
        }

        out.println(NL + "Dear " + firstName + " " + surName);
        out.println("your score: " + setOfQuestions.getScore());
    }

    public int getScore()
    {
        return setOfQuestions.getScore();
    }
}
