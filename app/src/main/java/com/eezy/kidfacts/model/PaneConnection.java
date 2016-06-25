package com.eezy.kidfacts.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.eezy.kidfacts.util.DeviceUtil;

public class PaneConnection {

    private Pane mSrcPane;
    private Pane mDestPane;
    private Paint mPaint;

    public PaneConnection(Pane srcPane, Pane destPane) {
        mSrcPane = srcPane;
        mDestPane = destPane;
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(DeviceUtil.dpToPx(2));
    }

    public void draw(Canvas canvas) {
        if (mSrcPane != null && mDestPane != null) {
            Point start = mSrcPane.center(0, 0);
            Point end = mDestPane.center(0, 0);
            if (start != null && end != null) {
                canvas.drawLine(start.x, start.y, end.x, end.y, mPaint);
            }
        }
    }

    public Pane getSrcPane() {
        return mSrcPane;
    }

    public Pane getDestPane() {
        return mDestPane;
    }

    public boolean equals(PaneConnection connection) {
        return mSrcPane == connection.mSrcPane && mDestPane == connection.mDestPane;
    }
}
