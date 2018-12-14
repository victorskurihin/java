package ru.otus.models;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("answer")
@Scope("prototype")
public class Answer implements IAnswer
{
    private String answer;

    private int score;

    @Override
    public String getAnswer()
    {
        return answer;
    }

    @Override
    public void setAnswer(String answer)
    {
        this.answer = answer;
    }

    @Override
    public int getScore()
    {
        return score;
    }

    @Override
    public void setScore(int score)
    {
        this.score = score;
    }

    @Override
    public int hashCode()
    {
        return score + (null != answer ? 13 * answer.hashCode() : 0);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) {
            return false;
        }
        if ( ! (obj instanceof Answer)) {
            return false;
        }
        Answer other = (Answer) obj;

        return (null != answer ? answer.equals(other.answer) : null == other.answer) && score == other.score;
    }

    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + "{ answer=\"" + answer + "\", score=" + score + " }";
    }
}
