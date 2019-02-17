package com.example.trivia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question {

    private String question;
    private String correctAnswer;
    private List<String> incorrectAnswers;

    public Question(String question, String correctAnswer, List<String> incorrectAnswers) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public List<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public static List<Question> getList(String jsonString) {
        List<Question> questionList = new ArrayList<Question>();
        try {
            JSONArray jsonQuestions = new JSONObject(jsonString).getJSONArray("results");
            for (int i = 0; i < jsonQuestions.length(); i++) {
                JSONObject jsonQuestion = jsonQuestions.getJSONObject(i);
                List<String> incorrectAnswers = new ArrayList<String>();
                for (int j = 0; j < jsonQuestion.getJSONArray("incorrect_answers").length(); j++) {
                    incorrectAnswers.add(jsonQuestion.getJSONArray("incorrect_answers").getString(j));
                }
                questionList.add(new Question(jsonQuestion.getString("question"), jsonQuestion.getString("correct_answer"), incorrectAnswers));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return questionList;
    }

    public List<String> getShuffledAnswers() {
        List<String> answers = new ArrayList<String>();
        answers.add(correctAnswer);
        for (String incorrectAnswer : incorrectAnswers) {
            answers.add(incorrectAnswer);
        }
        Collections.shuffle(answers);
        return answers;
    }
}
