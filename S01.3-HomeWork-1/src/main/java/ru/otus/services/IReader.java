package ru.otus.services;

import ru.otus.models.IAnswer;
import ru.otus.models.IQuestion;
import ru.otus.models.ISetOfQuestions;

import java.util.function.Supplier;

public interface IReader
{
    void read(Supplier<IQuestion> getQuestionBean, Supplier<IAnswer> getAnswerBean);
}
