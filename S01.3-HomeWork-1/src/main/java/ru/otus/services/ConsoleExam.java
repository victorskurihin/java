package ru.otus.services;

import ru.otus.models.Answer;
import ru.otus.models.ISetOfQuestions;
import ru.otus.models.Question;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

public class ConsoleExam implements IExam
{
    private InputStream in;

    private OutputStream out;

    private ISetOfQuestions setOfQuestions;

    public ConsoleExam(InputStream in, OutputStream out)
    {
        this.in = in;
        this.out = out;
    }

    public ISetOfQuestions getSetOfQuestions()
    {
        return setOfQuestions;
    }

    public void setSetOfQuestions(ISetOfQuestions setOfQuestions)
    {
        this.setOfQuestions = setOfQuestions;
    }

    private void showQuestion(String question)
    {
        System.out.println("question: " + question);
    }

    private void showAnswers(List<Answer> answers)
    {
        int i = 1;
        for (Answer answer : answers) {
            System.out.println(i + ": " + answer.getAnswer());
            i++;
        }
    }

    private int readAnswer(List<Answer> answers)
    {
        Scanner in = new Scanner(System.in);

        try {
            int result = in.nextInt();

            if (result > answers.size()) {
                return -100;
            }

            return result;
        } catch (InputMismatchException e) {
            return -1;
        } catch (NoSuchElementException e) {
            return -2;
        } catch (IllegalStateException e) {
            return -3;
        } catch (Exception e) {
            return 0; // Where got unknow error. No need show the error message.
        }
    }

    private String getErrorMessage(int answerIndex)
    {
        switch (answerIndex) {
            case -100:
                return "The number is not in the answer's range";
            case -1:
            case -2:
            case -3:
                return "Input Mismatch. Token does not match the Integer regular expression, or is out of range ";
            default:
                return "Unknow error!!!";
        }
    }

    private void showErrorMessage(int answerIndex)
    {
        System.out.println("Error message: " + getErrorMessage(answerIndex));
    }

    public void run()
    {
        Iterator<Question> iterator = setOfQuestions.iterator();

        while (iterator.hasNext()) {
            int answerIndex = 0;
            Question question = iterator.next();

            do {
                showQuestion(question.getQuestion());
                showAnswers(question.getAnswers());

                answerIndex = readAnswer(question.getAnswers());
                if (answerIndex == 0) {
                    return; // TOTO custom Exception UserInterrupted.
                } else
                    showErrorMessage(answerIndex);
            } while (answerIndex < 1);

            setOfQuestions.addScore(question.getAnswers().get(answerIndex).getScore());
        }
    }

    public int getScore()
    {
        return setOfQuestions.getScore();
    }
}
