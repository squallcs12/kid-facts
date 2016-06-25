package com.eezy.kidfacts.view;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.eezy.kidfacts.model.Pane;

import java.util.ArrayList;
import java.util.List;

public class DragablePaneView extends MultiPaneView implements MultiPaneView.OnTouchListener {

    private Pane mDraggingPane;
    private Point mDraggingAnchor;
    private Point mLeftTopAnchor;

    private List<Pane> mTargetPanes;
    private List<Pane> mSrcPanes;
    private List<TargetPaneReachListener> mTargetPaneReachListeners;

    private Pane mGhostPane;
    private boolean mGhostMode;
    private boolean mEnableTouch;
    private OnDraggingListener mOnDraggingListener;

    public DragablePaneView(Context context, AttributeSet attrs) {
        super(context, attrs);
        registerOnTouchListener(this);
        mTargetPanes = new ArrayList<>();
        mSrcPanes = new ArrayList<>();
        mTargetPaneReachListeners = new ArrayList<>();
        mEnableTouch = true;
    }

    public void addTargetPaneFor(Pane targetPane, Pane srcPane, TargetPaneReachListener targetPaneReachListener) {
        if (targetPane != null && srcPane != null && targetPaneReachListener != null) {
            if (getPanes().contains(targetPane) && getPanes().contains(srcPane)) {
                mTargetPanes.add(targetPane);
                mSrcPanes.add(srcPane);
                mTargetPaneReachListeners.add(targetPaneReachListener);
            }
        }
    }

    public void resetTargetPaneReachListener() {
        mTargetPanes.clear();
        mSrcPanes.clear();
        mTargetPaneReachListeners.clear();
    }

    public void setOnDraggingListener(OnDraggingListener listener) {
        mOnDraggingListener = listener;
    }

    public void setGhostMode(boolean ghostMode) {
        mGhostMode = ghostMode;
    }

    public void enableTouch(boolean enable) {
        mEnableTouch = enable;
    }

    @Override
    public void onTouch(Pane touchedPane, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN && touchedPane != null && touchedPane.isMovable() && mEnableTouch) {
            mDraggingPane = touchedPane;
            mDraggingAnchor = new Point(x, y);
            mLeftTopAnchor = touchedPane.leftTop(0, 0);
            if (mGhostMode) {
                mGhostPane = mDraggingPane.clone();
                addPane(mGhostPane);
                invalidate();
            }
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (mDraggingPane != null) {
                for (int i = 0; i < mTargetPaneReachListeners.size(); ++i) {
                    if (mDraggingPane == mSrcPanes.get(i)) {
                        Pane targetPane = mTargetPanes.get(i);
                        TargetPaneReachListener listener = mTargetPaneReachListeners.get(i);
                        if (mGhostMode && mGhostPane.intersect(targetPane) ||
                                !mGhostMode && mDraggingPane.intersect(targetPane)) {
                            listener.onTargetReach(targetPane, mDraggingPane);
                            break;
                        }
                    }
                }
            }

            if (mGhostPane != null) {
                removePane(mGhostPane);
                invalidate();
            } else if (mDraggingPane != null) {
                if (mOnDraggingListener != null) {
                    mOnDraggingListener.onDrop(mDraggingPane, new Point(x, y));
                }
            }

            mDraggingPane = null;
            mDraggingAnchor = null;
            mLeftTopAnchor = null;
            mGhostPane = null;
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE && mDraggingPane != null) {
            int newX = mLeftTopAnchor.x + x - mDraggingAnchor.x;
            int newY = mLeftTopAnchor.y + y - mDraggingAnchor.y;
            if (mGhostMode) {
                mGhostPane.moveTo(newX, newY);
            } else {
                mDraggingPane.moveTo(newX, newY);
            }
            if (mOnDraggingListener != null) {
                mOnDraggingListener.onDragging(mDraggingPane, new Point(x, y));
            }
            //FIXME only invalidate the moving part
            invalidate();
        }
    }

    public interface TargetPaneReachListener {
        void onTargetReach(Pane targetPane, Pane draggingPane);
    }

    public interface OnDraggingListener {
        void onDragging(Pane draggingPane, Point touchedPoint);
        void onDrop(Pane draggingPane, Point touchedPoint);
    }
}
