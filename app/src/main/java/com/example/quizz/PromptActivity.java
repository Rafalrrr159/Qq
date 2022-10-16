package com.example.quizz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PromptActivity extends AppCompatActivity {
    private int promptId;
    private TextView promptTextView;
    public static final String KEY_EXTRA_PROMPT_SHOWN = "promptShown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt);
        Button showPromptButton = findViewById(R.id.ShowPrompt);
        promptId = getIntent().getIntExtra(MainActivity.KEY_EXTRA_ANSWER, 0);
        promptTextView = findViewById(R.id.prompt_text_view);

        showPromptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptTextView.setText(promptId);
                setPromptShownResult(true);
            }
        });

    }

    private void setPromptShownResult(boolean promptWasShown) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_EXTRA_PROMPT_SHOWN, promptWasShown);
        setResult(RESULT_OK, resultIntent);
    }
}