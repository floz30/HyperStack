package fr.uge.hyperstack.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import fr.uge.hyperstack.R;
import fr.uge.hyperstack.fragment.ImportImageDialogFragment;
import fr.uge.hyperstack.model.Element;
import fr.uge.hyperstack.fragment.ImportSoundDialogFragment;
import fr.uge.hyperstack.model.Layer;
import fr.uge.hyperstack.model.Mode;
import fr.uge.hyperstack.model.Point;
import fr.uge.hyperstack.model.Rectangle;
import fr.uge.hyperstack.model.Stack;
import fr.uge.hyperstack.model.Stroke;
import fr.uge.hyperstack.model.Triangle;
import fr.uge.hyperstack.view.EditorView;
import fr.uge.hyperstack.view.listener.EditorViewListener;

public class EditActivity extends AppCompatActivity {
    private int currentStackNum = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        setEditSetup();
        setUpEditMode();

        EditorView editorView = findViewById(R.id.editorView2);
        editorView.getCurrentStack().setDrawableElements();

        Button backButton = findViewById(R.id.backEditButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent();
            editorView.getCurrentStack().addNewSlide();
            intent.putExtra("newCurrentStack", editorView.getCurrentStack());
            intent.putExtra("stackNum", currentStackNum);
            setResult(RESULT_OK, intent);
            finish();
        });
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
                loadSound();
                return true;
            case R.id.action_add_location:
                return true;
            case R.id.action_add_user_input:
                return true;
            case R.id.action_erase:
                clearSlide();
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

    private void clearSlide() {
        EditorView ev = findViewById(R.id.editorView2);
        Stack s = ev.getCurrentStack();
        s.resetSlide(ev.currentSlide);
    }

    private void loadImage() {
        ImportImageDialogFragment imageDialogFragment = new ImportImageDialogFragment();
        imageDialogFragment.show(getSupportFragmentManager(), "import");
    }

    private void loadSound() {
        ImportSoundDialogFragment soundDialogFragment = new ImportSoundDialogFragment();
        soundDialogFragment.show(getSupportFragmentManager(), "import");

    }


    public void setEditSetup() {
        EditorView ev = findViewById(R.id.editorView2);

        Intent homeIntent = getIntent();
        ev.setCurrentStack((Stack) homeIntent.getSerializableExtra("stack"));
        currentStackNum = homeIntent.getIntExtra("stackNum", -1);

        ev.setEditorViewListener(new EditorViewListener() {
            @Override
            public void onFingerTouch(float x, float y) {
                Stroke stroke = new Stroke(Color.RED, 25);
                stroke.moveTo(x,y);
                ev.getStrokeStack().add(stroke);
                Layer layer = new Layer();
                layer.addElement(stroke);
                ev.getCurrentStack().addLayerElementToSlide(layer, ev.currentSlide);
            }

            @Override
            public void onFingerMove(float x, float y) {
                Element currentElem = ev.getStrokeStack().peek();
                currentElem.onFingerMoveAction(x,y);
            }

            @Override
            public void onFingerRaise(float x, float y) {

            }
        });
    }

    public static Element initFigure(float x, float y, EditorView ev){
        switch (ev.getCurrentMode()){
            case RECTANGLE: return new Rectangle(new Point(x,y),new Point(x,y),Color.RED,10);
            case TRIANGLE: return new Triangle(Color.RED,10,new Point(x,y),new Point(x,y),new Point(x,y));
            default: return null;
        }
    }

    public void setFigureSetup() {
        EditorView ev = findViewById(R.id.editorView2);
        Intent homeIntent = getIntent();
        ev.setCurrentStack((Stack) homeIntent.getSerializableExtra("stack"));
        ev.setEditorViewListener(new EditorViewListener() {
            @Override
            public void onFingerTouch(float x, float y) {
                Element rect = initFigure(x,y,ev);
                ev.getStrokeStack().add(rect);
                Layer layer = new Layer();
                layer.addElement(rect);
                ev.getCurrentStack().addLayerElementToSlide(layer, ev.currentSlide);
            }

            @Override
            public void onFingerMove(float x, float y) {
                Element currentElem = ev.getStrokeStack().peek();
                currentElem.onFingerMoveAction(x,y);
            }

            @Override
            public void onFingerRaise(float x, float y) {

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
        FloatingActionButton rectButton = findViewById(R.id.rectButton);
        FloatingActionButton triangleButton = findViewById(R.id.triangleButton);
        EditorView editorView = findViewById(R.id.editorView2);
        editButton.setOnClickListener(
            (view) -> {
                setEditSetup();
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


        rectButton.setOnClickListener(
                (view) -> {
                    setFigureSetup();
                   if(editorView.getCurrentMode().equals(Mode.RECTANGLE)){
                       editorView.setCurrentMode(Mode.SELECTION);
                       rectButton.setImageResource(android.R.drawable.btn_default_small);
                   }
                   else{
                       editorView.setCurrentMode(Mode.RECTANGLE);
                       rectButton.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
                   }
                }
        );

        triangleButton.setOnClickListener(
                (view) -> {
                    setFigureSetup();
                    if(editorView.getCurrentMode().equals(Mode.RECTANGLE)){
                        editorView.setCurrentMode(Mode.SELECTION);
                        triangleButton.setImageResource(android.R.drawable.arrow_up_float);
                    }
                    else{
                        editorView.setCurrentMode(Mode.TRIANGLE);
                        triangleButton.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
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