package fr.uge.hyperstack.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import fr.uge.hyperstack.R;
import fr.uge.hyperstack.model.Layer;
import fr.uge.hyperstack.model.Mode;
import fr.uge.hyperstack.model.Stroke;
import fr.uge.hyperstack.model.Stack;
import fr.uge.hyperstack.view.EditorView;
import fr.uge.hyperstack.view.listener.EditorViewListener;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        setEditSetup();
        setUpEditMode();

        Button backButton = findViewById(R.id.backEditButton);
        backButton.setOnClickListener(v -> finish());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        return true;
    }

    public void setEditSetup() {
        EditorView ev = findViewById(R.id.editorView2);

        Intent homeIntent = getIntent();
        ev.setCurrentStack((Stack) homeIntent.getSerializableExtra("stack"));

        ev.setEditorViewListener(new EditorViewListener() {
            @Override
            public void onFingerTouch(double x, double y) {
                Stroke stroke = new Stroke(Color.RED, 25);
                stroke.moveTo((float)x,(float)y);
                ev.getStrokeStack().add(stroke);
            }

            @Override
            public void onFingerMove(double x, double y) {
                Stroke currentStroke = ev.getStrokeStack().peek();
                currentStroke.lineTo((float)x, (float)y);
                Layer layer = new Layer();
                layer.addElement(currentStroke);
                ev.getCurrentStack().addLayerElementToSlide(layer, ev.currentSlide);
            }

            @Override
            public void onFingerRaise(double x, double y) {

            }
        });
    }

    private void setUpEditMode() {
        FloatingActionButton editButton = findViewById(R.id.editButton);
        EditorView editorView = findViewById(R.id.editorView2);
        editButton.setOnClickListener(
            (view) -> {
                if (!editorView.drawModeOn) {
                    editorView.drawModeOn = true;
                    editorView.setCurrentMode(Mode.DRAW);
                    editButton.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
                } else{
                    editorView.drawModeOn = false;
                    editorView.setCurrentMode(Mode.SELECTION);
                    editButton.setImageResource(android.R.drawable.ic_menu_edit);
                }
            }

        );
    }
}