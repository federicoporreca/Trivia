package com.example.trivia;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView question;
    private Button answer1, answer2, answer3, answer4, bottomButton;
    private List<Question> questions;
    private int currentQuestionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        question = findViewById(R.id.game_question);
        answer1 = findViewById(R.id.game_answer_1);
        answer2 = findViewById(R.id.game_answer_2);
        answer3 = findViewById(R.id.game_answer_3);
        answer4 = findViewById(R.id.game_answer_4);
        bottomButton = findViewById(R.id.game_next_question);

        answer1.setOnClickListener(this);
        answer2.setOnClickListener(this);
        answer3.setOnClickListener(this);
        answer4.setOnClickListener(this);
        bottomButton.setOnClickListener(this);

        DataGetter dataGetter = new DataGetter("https://opentdb.com/api.php?amount=10&type=multiple");
        dataGetter.getData(new DataGetter.ResponseCallback() {
            @Override
            public void onError(int errorCode) {
                Toast.makeText(GameActivity.this, R.string.game_error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String response) {
                questions = Question.getList(response);
                loadQuestion();
            }
        });
    }

    private void loadQuestion() {
        question.setText(questions.get(currentQuestionIndex).getQuestion());
        List<String> answers = questions.get(currentQuestionIndex).getShuffledAnswers();
        answer1.setText(answers.get(0));
        answer2.setText(answers.get(1));
        answer3.setText(answers.get(2));
        answer4.setText(answers.get(3));
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        if (button.getId() == R.id.game_next_question) {
            if (currentQuestionIndex != questions.size() - 1) {
                answer1.getBackground().clearColorFilter();
                answer2.getBackground().clearColorFilter();
                answer3.getBackground().clearColorFilter();
                answer4.getBackground().clearColorFilter();
                button.setVisibility(View.INVISIBLE);
                currentQuestionIndex++;
                loadQuestion();
            } else {
                finish();
            }
        } else {
            if (!button.getText().equals(questions.get(currentQuestionIndex).getCorrectAnswer())) {
                button.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
            }
            if (answer1.getText().equals(questions.get(currentQuestionIndex).getCorrectAnswer())) {
                answer1.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
            }
            if (answer2.getText().equals(questions.get(currentQuestionIndex).getCorrectAnswer())) {
                answer2.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
            }
            if (answer3.getText().equals(questions.get(currentQuestionIndex).getCorrectAnswer())) {
                answer3.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
            }
            if (answer4.getText().equals(questions.get(currentQuestionIndex).getCorrectAnswer())) {
                answer1.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
            }
            if (currentQuestionIndex == questions.size() - 1) {
                bottomButton.setText(R.string.game_back_to_menu);
            }
            bottomButton.setVisibility(View.VISIBLE);
        }
    }
}
