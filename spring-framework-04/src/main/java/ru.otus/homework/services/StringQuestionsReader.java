package ru.otus.homework.services;

import ru.otus.homework.models.Answer;
import ru.otus.homework.models.Question;
import ru.otus.homework.models.Questions;

import static ru.otus.outside.utils.IOHelper.readFile;
import static ru.otus.outside.utils.IOHelper.readStringAsFile;
import static ru.otus.outside.utils.StringHelper.split;

public class StringQuestionsReader implements QuestionsReader
{
    private String data = "";

    private Questions questions;

    private QuizFactory quizFactory;

    public StringQuestionsReader() { /* None */ }

    public StringQuestionsReader(Questions questions, String stream, QuizFactory quizFactory)
    {
        this.questions = questions;
        this.data = stream;
        this.quizFactory = quizFactory;
    }

    public Questions getQuestions()
    {
        return questions;
    }

    public void setQuestions(Questions questions)
    {
        this.questions = questions;
    }

    @Override
    public void read()
    {
        readStringAsFile(data, line -> {
            String[] fields = split(line);

            Question question = quizFactory.getQuestionFactory().getObject();
            assert question != null;
            question.setQuestion(fields[0]);

            for (int i = 1; i < fields.length; i += 2) {
                Answer answer = quizFactory.getAnswerFactory().getObject();
                assert answer != null;
                answer.setAnswer(fields[i]);

                //noinspection ConstantConditions
                if (i < fields.length) {
                    answer.setScore(Integer.parseInt(fields[i + 1]));
                }
                question.addAnswer(answer);
            }

            questions.addQuestion(question);
        });
    }
}
