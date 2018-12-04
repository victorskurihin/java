package ru.otus.services;

import ru.otus.models.IAnswer;
import ru.otus.models.IQuestion;
import ru.otus.models.ISetOfQuestions;

import java.util.function.Supplier;

public class CSVReader implements IReader
{
    private String filename = "questions.csv";

    private ISetOfQuestions setOfQuestions;

    public CSVReader() { /* NOne */ }

    public CSVReader(String filename)
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
