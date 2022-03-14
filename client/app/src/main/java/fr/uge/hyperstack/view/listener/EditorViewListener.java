package fr.uge.hyperstack.view.listener;

import android.graphics.Canvas;

public interface EditorViewListener {
    void onFingerTouch(float x, float y);
    void onFingerMove(float x, float y);
    void onFingerRaise(float x, float y);
}
