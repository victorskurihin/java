package ru.otus.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SetOfQuestions implements ISetOfQuestions, Iterable<Question>
{
    private List<Question> questions = new ArrayList<>();

    private int activeQuestion = 0;

    private int score;

    public List<Question> getQuestions()
    {
        return questions;
    }

    public void setQuestions(List<Question> questions)
    {
        this.questions = questions;
    }

    @Override
    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    @Override
    public void addScore(int score)
    {
        this.score += score;
    }

    @Override
    public int size() {
        return questions.size();
    }

    @Override
    public Iterator<Question> iterator()
    {
        return new IteratorQuestion(this);
    }

    private class IteratorQuestion implements Iterator<Question>
    {
        private SetOfQuestions setOfQuestions;

        public IteratorQuestion(SetOfQuestions setOfQuestions)
        {
            this.setOfQuestions = setOfQuestions;
        }

        @Override
        public boolean hasNext()
        {
            return setOfQuestions.questions.size() > setOfQuestions.activeQuestion;
        }

        @Override
        public Question next()
        {
            int returnIndex = setOfQuestions.activeQuestion++;

            return setOfQuestions.questions.get(returnIndex);
        }
    }
}
