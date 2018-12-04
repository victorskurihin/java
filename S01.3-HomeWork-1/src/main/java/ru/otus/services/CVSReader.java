package ru.otus.services;

import ru.otus.models.IAnswer;
import ru.otus.models.IQuestion;
import ru.otus.models.ISetOfQuestions;

import java.util.function.Supplier;

public class CVSReader implements IReader
{
    private String filename = "questions.csv";

    private ISetOfQuestions setOfQuestions;

    public CVSReader() { /* NOne */ }

    public CVSReader(String filename)
    {
        this.filename = filename;
    }

    public ISetOfQuestions getSetOfQuestions()
    {
        return setOfQuestions;
    }

    public void setSetOfQuestions(ISetOfQuestions setOfQuestions)
    {
        this.setOfQuestions = setOfQuestions;
    }

    @Override
    public void read(Supplier<IQuestion> getQuestionBean, Supplier<IAnswer> getAnswerBean) {

    }
}
