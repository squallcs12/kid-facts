package com.eezy.kidfacts.model;

import android.graphics.Rect;

public class MovableRect {

    private Rect mRect;

    public MovableRect() {
        this(null);
    }

    public MovableRect(Rect rect) {
        mRect = rect;
    }

    public void set(Rect rect) {
        mRect = rect;
    }

    public Rect move(int x, int y) {
        mRect.left += x;
        mRect.right += x;
        mRect.top += y;
        mRect.bottom += y;
        return mRect;
    }

    public Rect moveTo(int x, int y) {
        int dX = x - mRect.left;
        int dY = y - mRect.top;
        return move(dX, dY);
    }

    public Rect moveTo(int x) {
        return moveTo(x, mRect.top);
    }

    public int width() {
        return mRect.width();
    }

    public int height() {
        return mRect.height();
    }
}
