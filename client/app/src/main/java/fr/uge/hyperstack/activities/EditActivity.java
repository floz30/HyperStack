package fr.uge.hyperstack.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import fr.uge.hyperstack.R;
import fr.uge.hyperstack.fragment.ImportImageDialogFragment;
import fr.uge.hyperstack.model.Layer;
import fr.uge.hyperstack.model.Mode;
import fr.uge.hyperstack.model.Stack;
import fr.uge.hyperstack.model.Stroke;
import fr.uge.hyperstack.view.EditorView;
import fr.uge.hyperstack.view.listener.EditorViewListener;

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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_text:
                editSlideText();
                return true;
            case R.id.action_add_image:
                loadImage();
                return true;
            case R.id.action_add_sound:
                return true;
            case R.id.action_add_location:
                return true;
            case R.id.action_add_user_input:
                return true;
            case R.id.action_erase:
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    /**
     * Loses focus on TextView when clicking outside of the TextView
     * See https://stackoverflow.com/questions/4828636/edittext-clear-focus-on-touch-outside
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }


    private void editSlideText() {
        TextView et = findViewById(R.id.slide_text);

        et.setVisibility(View.VISIBLE);
        et.requestFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }

    private void loadImage() {
        ImportImageDialogFragment imageDialogFragment = new ImportImageDialogFragment();
        imageDialogFragment.show(getSupportFragmentManager(), "import");
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

    private static void setUpSlideNavigationButton(Context context, EditorView editorView, FloatingActionButton previousButton, FloatingActionButton nextButton, int value) {
        editorView.currentSlide += value;
        editorView.invalidate();
        nextButton.setEnabled(editorView.getCurrentStack().sizeOfStack() > 0 && editorView.currentSlide < editorView.getCurrentStack().sizeOfStack() - 1);
        previousButton.setEnabled(editorView.currentSlide > 0);
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

        FloatingActionButton previousButton = findViewById(R.id.previousSlideButton);
        FloatingActionButton nextButton = findViewById(R.id.nextSlideButton);
        nextButton.setEnabled(editorView.getCurrentStack().sizeOfStack() > 0 && editorView.currentSlide < editorView.getCurrentStack().sizeOfStack() - 1);
        previousButton.setEnabled(editorView.currentSlide > 0);

        previousButton.setOnClickListener(
                (view) -> setUpSlideNavigationButton(this, editorView, previousButton, nextButton, -1)
        );
        nextButton.setOnClickListener(
                (view) -> setUpSlideNavigationButton(this, editorView, previousButton, nextButton, 1)
        );

    }
}