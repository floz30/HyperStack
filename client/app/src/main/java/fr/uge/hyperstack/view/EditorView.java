package fr.uge.hyperstack.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import fr.uge.hyperstack.model.PaintElement;
import fr.uge.hyperstack.model.Mode;
import fr.uge.hyperstack.view.listener.EditorViewListener;

public class EditorView extends View implements Serializable {
    private EditorViewListener editorViewListener;
    private fr.uge.hyperstack.model.Stack currentStack;
    private final List<PaintElement> strokeStack = new ArrayList<>();
    private Mode currentMode = Mode.SELECTION;
    public boolean drawModeOn;


    public EditorView(Context context) {
        super(context);
    }


    public void setEditorViewListener(EditorViewListener evl) {
        this.editorViewListener = evl;
        invalidate();
    }

    public void addElement(PaintElement element) {
        this.strokeStack.add(element);
    }

    public List<PaintElement> getStrokeStack() {
        return strokeStack;
    }

    public Mode getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(Mode currentMode) {
        this.currentMode = currentMode;
    }

    public EditorViewListener getEditorViewListener() {
        return editorViewListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (PaintElement element : strokeStack) {
            element.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();

        float x = event.getX();
        float y = event.getY();

        if (currentMode == Mode.DRAW || currentMode == Mode.TRIANGLE || currentMode == Mode.RECTANGLE) {
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    editorViewListener.onFingerTouch(x, y);
                    invalidate();
                    return true;
                case MotionEvent.ACTION_MOVE:
                    editorViewListener.onFingerMove(x, y);
                    break;
                case MotionEvent.ACTION_UP:
                    editorViewListener.onFingerRaise(x, y);
                    break;
            }
        }
        invalidate();
        return super.onTouchEvent(event);
    }

}