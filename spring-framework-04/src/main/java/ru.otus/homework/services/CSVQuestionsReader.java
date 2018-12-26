package ru.otus.homework.services;

import ru.otus.homework.models.Answer;
import ru.otus.homework.models.Question;
import ru.otus.homework.models.Questions;

import static ru.otus.outside.utils.IOHelper.readFile;
import static ru.otus.outside.utils.StringHelper.split;

public class CSVQuestionsReader implements QuestionsReader
{
    private String filename = "";

    private Questions questions;

    private QuizFactory quizFactory;

    public CSVQuestionsReader() { /* None */ }

    public CSVQuestionsReader(Questions questions, String fileName, QuizFactory quizFactory)
    {
        this.questions = questions;
        this.filename = fileName;
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
        readFile(this.getClass(), filename, line -> {
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
