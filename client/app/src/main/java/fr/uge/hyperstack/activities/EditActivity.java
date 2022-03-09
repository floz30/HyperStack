package fr.uge.hyperstack.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import fr.uge.hyperstack.R;
import fr.uge.hyperstack.fragment.SlideFragmentStateAdapter;
import fr.uge.hyperstack.model.Stack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EditActivity extends AppCompatActivity {
    private Stack currentStack;
    private ViewPager2 viewPagerSlides;

    public void onClickReturn(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent homeIntent = getIntent();
        currentStack = (Stack) homeIntent.getSerializableExtra("stack");

        this.viewPagerSlides = findViewById(R.id.viewPager);

        SlideFragmentStateAdapter adapter = new SlideFragmentStateAdapter(this);
        this.viewPagerSlides.setAdapter(adapter);

        Button backButton = findViewById(R.id.backEditButton);
        backButton.setOnClickListener(v -> finish());
    }
}