package fr.uge.hyperstack.activities;

import androidx.appcompat.app.AppCompatActivity;
import fr.uge.hyperstack.R;
import fr.uge.hyperstack.model.Stack;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {
    private Stack currentStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent homeIntent = getIntent();
        currentStack = (Stack) homeIntent.getSerializableExtra("stack");

        TextView titleTextView = findViewById(R.id.titleEditTextView);
        titleTextView.setText(currentStack.getTitle());

        Button backButton = findViewById(R.id.backEditButton);
        backButton.setOnClickListener(v -> finish());
    }
}