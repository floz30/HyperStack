package fr.uge.hyperstack.view.listener;

import android.graphics.Canvas;

public interface EditorViewListener {
    void onFingerTouch(double x, double y);
    void onFingerMove(double x, double y);
    void onFingerRaise(double x, double y);
}
