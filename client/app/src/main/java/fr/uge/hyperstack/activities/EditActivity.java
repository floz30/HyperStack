package fr.uge.hyperstack.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.uge.hyperstack.R;
import fr.uge.hyperstack.fragment.ImportImageDialogFragment;
import fr.uge.hyperstack.fragment.SlideBottomBarDialogFragment;
import fr.uge.hyperstack.model.PaintElement;
import fr.uge.hyperstack.fragment.ImportSoundDialogFragment;
import fr.uge.hyperstack.model.Layer;
import fr.uge.hyperstack.model.Mode;
import fr.uge.hyperstack.model.drawing.Circle;
import fr.uge.hyperstack.model.drawing.Point;
import fr.uge.hyperstack.model.drawing.Rectangle;
import fr.uge.hyperstack.model.Stack;
import fr.uge.hyperstack.model.drawing.Stroke;
import fr.uge.hyperstack.model.drawing.Triangle;
import fr.uge.hyperstack.model.media.Image;
import fr.uge.hyperstack.utils.Localisation;
import fr.uge.hyperstack.utils.Permission;
import fr.uge.hyperstack.view.EditorView;
import fr.uge.hyperstack.view.listener.EditorViewListener;

@SuppressLint("NonConstantResourceId")
public class EditActivity extends AppCompatActivity {
    private int currentStackNum = -1;
    private Stack currentStack;
    //private EditorView editorView;
    private ImportImageDialogFragment imageDialogFragment;
    private SlideBottomBarDialogFragment slideBottomBarDialogFragment;
    private Localisation localisation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent homeIntent = getIntent();

        //editorView = findViewById(R.id.editorView2);
        currentStack = (Stack) homeIntent.getSerializableExtra("stack");
        //editorView.setCurrentStack(currentStack);
        currentStackNum = homeIntent.getIntExtra("stackNum", -1);
        currentStack.initSlideLayer(getApplicationContext(), findViewById(R.id.editLayout));


        setEditSetup();
        setUpEditMode();

        imageDialogFragment = new ImportImageDialogFragment();
        slideBottomBarDialogFragment = new SlideBottomBarDialogFragment(currentStack.getSlides());

        updateSlideNumberLabel();

        BottomAppBar bottomAppBar = findViewById(R.id.bottomAppBar);
        bottomAppBar.setNavigationOnClickListener(view -> {
            slideBottomBarDialogFragment.show(getSupportFragmentManager(), "slideBottomBar");
        });

        currentStack.drawSlide(0);
//        bottomAppBar.setOnMenuItemClickListener(menuItem -> {
//            switch (menuItem.getItemId()) {
//                case R.id.previousSlide:
//                    if (editorView.currentSlide > 0) {
//                        editorView.currentSlide--;
//                        updateSlideNumberLabel();
//                        editorView.invalidate();
//                    }
//                    return true;
//                case R.id.nextSlide:
//                    if (currentStack.sizeOfStack() > 0 && editorView.currentSlide < currentStack.sizeOfStack() - 1) {
//                        editorView.currentSlide++;
//                        updateSlideNumberLabel();
//                        editorView.invalidate();
//                    }
//                    return true;
//                default:
//                    return false;
//            }
//        });

//        editorView.getCurrentStack().setDrawableElements();

//        Button backButton = findViewById(R.id.backEditButton);
//        backButton.setOnClickListener(v -> {
//            Intent intent = new Intent();
//            editorView.getCurrentStack().addNewSlide();
//            intent.putExtra("newCurrentStack", editorView.getCurrentStack());
//            intent.putExtra("stackNum", currentStackNum);
//            setResult(RESULT_OK, intent);
//            finish();
//        });
    }

    private void updateSlideNumberLabel() {
        TextView slideNumberTextView = findViewById(R.id.slideNumberBottomBarTextView);
//        slideNumberTextView.setText(String.format(getResources().getString(R.string.slide_number_bottom_bar), editorView.currentSlide + 1, currentStack.sizeOfStack()));
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
                showImportImageVideoDialog();
                return true;
            case R.id.action_add_sound:
                loadSound();
                return true;
            case R.id.action_add_location:
                if(localisation == null)
                    localisation = new Localisation(this);
                localisation.runWithPermission(Manifest.permission.ACCESS_FINE_LOCATION, "geolocalisation");
                return true;
            case R.id.action_add_user_input:
                return true;
            case R.id.action_erase:
                clearSlide();
                return true;
            case R.id.logs:
                goToLogs();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Permission.IMAGE_CAPTURE_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    Image img = new Image((Bitmap) extras.get("data"));
                    //currentStack.addLayerElementToSlide(new Layer(img), 0); // TODO
                    imageDialogFragment.dismiss();
                }
                break;
            case Permission.VIDEO_CAPTURE_REQUEST_CODE:
                break;
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

    public void onClickAddSlide(View v) {
        currentStack.addNewSlide();
        updateSlideNumberLabel();
    }

    private void editSlideText() {
        TextView et = findViewById(R.id.slide_text);

        et.setVisibility(View.VISIBLE);
        et.requestFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }

    private void clearSlide() {
//        Stack s = editorView.getCurrentStack();
//        s.resetSlide(editorView.currentSlide);
//        editorView.invalidate();
    }

    private void goToLogs() {
//        EditorView ev = findViewById(R.id.editorView2);
//        Intent intent = new Intent(this, LogsActivity.class);
//        intent.putExtra("logs", ev.getCurrentStack().getLogs());
//        startActivityForResult(intent, 1);
    }

    /**
     * Affiche le BottomSheetDialog proposant d'importer une photo/vidéo ou de prendre une photo/vidéo directement.
     */
    private void showImportImageVideoDialog() {
        imageDialogFragment.show(getSupportFragmentManager(), "importImage");
    }

    private void loadSound() {
        ImportSoundDialogFragment soundDialogFragment = new ImportSoundDialogFragment();
        soundDialogFragment.show(getSupportFragmentManager(), "importSound");
    }

    public void setEditSetup() {
//        editorView.setEditorViewListener(new EditorViewListener() {
//            @Override
//            public void onFingerTouch(float x, float y) {
//                Stroke stroke = new Stroke(Color.RED, 25);
//                stroke.moveTo(x,y);
//                editorView.getStrokeStack().add(stroke);
//                editorView.getCurrentStack().addLayerElementToSlide(stroke, editorView.currentSlide);
//            }
//
//            @Override
//            public void onFingerMove(float x, float y) {
//                PaintElement currentElem = editorView.getStrokeStack().peek();
//                currentElem.onFingerMoveAction(x,y);
//            }
//
//            @Override
//            public void onFingerRaise(float x, float y) { }
//        });
    }

    public static PaintElement initFigure(float x, float y, EditorView ev){
        switch (ev.getCurrentMode()){
            case RECTANGLE: return new Rectangle(new Point(x,y),new Point(x,y),Color.RED,10);
            case TRIANGLE: return new Triangle(Color.RED,10,new Point(x,y),new Point(x,y),new Point(x,y));
            case CIRCLE: return new Circle(Color.RED,new Point(x,y),10,new Point(x,y));
            default: return null;
        }
    }

    public void setFigureSetup() {
        Intent homeIntent = getIntent();
//        editorView.setCurrentStack((Stack) homeIntent.getSerializableExtra("stack"));
//        editorView.setEditorViewListener(new EditorViewListener() {
//            @Override
//            public void onFingerTouch(float x, float y) {
//                PaintElement element = initFigure(x, y, editorView);
//                editorView.getStrokeStack().add(element);
//                editorView.getCurrentStack().addLayerElementToSlide(element, editorView.currentSlide);
//            }
//
//            @Override
//            public void onFingerMove(float x, float y) {
//                PaintElement currentElem = editorView.getStrokeStack().peek();
//                currentElem.onFingerMoveAction(x,y);
//            }
//
//            @Override
//            public void onFingerRaise(float x, float y) {
//                editorView.setCurrentMode(Mode.SELECTION);
//            }
//        });
    }

    private void setUpEditMode() {
//        FloatingActionButton editButton = findViewById(R.id.editButton);
        FloatingActionButton rectButton = findViewById(R.id.rectButton);
        FloatingActionButton triangleButton = findViewById(R.id.triangleButton);

//        editButton.setOnClickListener(
//            (view) -> {
//                setEditSetup();
//                if (!editorView.drawModeOn) {
//                    editorView.drawModeOn = true;
//                    editorView.setCurrentMode(Mode.DRAW);
//                    editButton.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
//                } else{
//                    editorView.drawModeOn = false;
//                    editorView.setCurrentMode(Mode.SELECTION);
//                    editButton.setImageResource(android.R.drawable.ic_menu_edit);
//                }
//            }
//        );

//        rectButton.setOnClickListener(
//                (view) -> {
//                    setFigureSetup();
//                    editorView.setCurrentMode(!editorView.getCurrentMode().equals(Mode.RECTANGLE) ? Mode.RECTANGLE : Mode.SELECTION );
//                }
//        );
//
//        triangleButton.setOnClickListener(
//                (view) -> {
//                    setFigureSetup();
//                    setFigureSetup();
//                    editorView.setCurrentMode(!editorView.getCurrentMode().equals(Mode.TRIANGLE) ? Mode.TRIANGLE : Mode.SELECTION );
//                }
//        );

    }
}