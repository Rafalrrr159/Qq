package com.example.quizz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final String QUIZ_TAG = "MainActivity";
    public static final String activityLog = "Wywołana została metoda z cyklu życia: ";
    private static final String KEY_CURRENT_INDEX = "currentIndex";
    public static final String KEY_EXTRA_ANSWER = "com.example.zadanie";
    private static final int REQUEST_CODE_PROMPT = 0;
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button promptButton;
    private TextView questionTextView;
    private int currentIndex = 0;
    private boolean promptWasShown = false;

    private Question[] questions = new Question[]{
            new Question(R.string.q_activity, true),
            new Question(R.string.q_version, false),
            new Question(R.string.q_listener, true),
            new Question(R.string.q_resources, true),
            new Question(R.string.q_find_resources, false),
    };

    int[] prompts = {
            R.string.prompt1,
            R.string.prompt2,
            R.string.prompt3,
            R.string.prompt4,
            R.string.prompt5,
    };

    private void setNextQuestion(){
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(QUIZ_TAG, activityLog + "onCreate");
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }

        questionTextView = findViewById(R.id.question_text_view);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        promptButton = findViewById(R.id.prompt_button);

        setNextQuestion();

        trueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                CheckAnswer(true);
            }
        });
        falseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                CheckAnswer(false);
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                currentIndex = (currentIndex + 1)%questions.length;
                promptWasShown = false;
                setNextQuestion();
            }
        });
        promptButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PromptActivity.class);
            int prompt = prompts[currentIndex];
            intent.putExtra(KEY_EXTRA_ANSWER, prompt);
            startActivityForResult(intent, REQUEST_CODE_PROMPT);

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(QUIZ_TAG, activityLog + "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(QUIZ_TAG, activityLog + "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(QUIZ_TAG, activityLog + "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(QUIZ_TAG, activityLog + "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(QUIZ_TAG, activityLog + "onDestroy");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(QUIZ_TAG, activityLog + "onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == REQUEST_CODE_PROMPT) {
            if (data == null) return;
            promptWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_PROMPT_SHOWN, false);

        }
    }

    private void CheckAnswer(boolean userAnswer)
    {
        boolean goodAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;

        if (promptWasShown) {
            if(goodAnswer == userAnswer) {
                resultMessageId = R.string.correct_answerr;
            } else {
                resultMessageId = R.string.incorrect_answerr;
            }
        } else {
            if(goodAnswer == userAnswer) {
                resultMessageId = R.string.correct_answer;
            } else {
                resultMessageId = R.string.incorrect_answer;
            }
        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }
}