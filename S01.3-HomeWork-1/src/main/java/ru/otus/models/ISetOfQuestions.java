package ru.otus.models;

import java.util.Iterator;

public interface ISetOfQuestions
{
    int getScore();

    void addScore(int score);

    int size();

    Iterator<Question> iterator();
}
