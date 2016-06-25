package com.eezy.kidfacts.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.eezy.kidfacts.model.Pane;
import com.eezy.kidfacts.model.PaneConnection;

import java.util.ArrayList;
import java.util.List;

public class MultiPaneView extends View implements View.OnTouchListener {

    private List<Pane> mPanes;
    private List<PaneConnection> mConnections;
    private OnTouchListener mTouchListener;

    public MultiPaneView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPanes = new ArrayList<>();
        mConnections = new ArrayList<>();
        setOnTouchListener(this);
    }

    public void addPane(Pane pane) {
        mPanes.add(pane);
    }

    public void addPane(int location, Pane pane) {
        mPanes.add(location, pane);
    }

    public void movePaneBefore(Pane movingPane, Pane anchorPane) {
        if (mPanes.contains(movingPane) && mPanes.contains(anchorPane)) {
            mPanes.remove(movingPane);
            mPanes.add(mPanes.indexOf(anchorPane), movingPane);
        }
    }

    public void removePane(Pane pane) {
        mPanes.remove(pane);
    }

    public void removePanes(List<Pane> panes) {
        if (panes != null) {
            for (Pane p : panes) {
                mPanes.remove(p);
            }
        }
    }

    public void removeAllPanes() {
        if (mPanes != null) {
            mPanes.clear();
        }
    }

    public void addConnection(Pane srcPane, Pane destPane) {
        if (mPanes.contains(srcPane) && mPanes.contains(destPane)) {
            PaneConnection pc = new PaneConnection(srcPane, destPane);
            if (!hasPaneConnection(pc)) {
                mConnections.add(pc);
            }
        }
    }

    public void removeConnectionsStartWith(Pane srcPane) {
        List<PaneConnection> toBeRemoved = new ArrayList<>();
        for (PaneConnection pc : mConnections) {
            if (srcPane == pc.getSrcPane()) {
                toBeRemoved.add(pc);
            }
        }
        mConnections.removeAll(toBeRemoved);
    }

    public boolean hasPaneConnection(PaneConnection paneConnection) {
        for (PaneConnection pc : mConnections) {
            if (pc.equals(paneConnection)) {
                return true;
            }
        }
        return false;
    }

    public List<PaneConnection> getPaneConnections() {
        return mConnections;
    }

    private Pane getClickedPane(int x, int y) {
        for (int i = mPanes.size() - 1; i >= 0; --i) {
            if (mPanes.get(i).isClicked(x, y)) {
                return mPanes.get(i);
            }
        }
        return null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Pane pane : mPanes) {
            pane.draw(canvas);
        }
        for (PaneConnection connection : mConnections) {
            connection.draw(canvas);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mTouchListener != null) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            mTouchListener.onTouch(getClickedPane(x, y), event);
        }
        return true;
    }

    public interface OnTouchListener {
        void onTouch(Pane touchedPane, MotionEvent event);
    }

    public void registerOnTouchListener(OnTouchListener onTouchListener) {
        mTouchListener = onTouchListener;
    }

    public List<Pane> getPanes() {
        return mPanes;
    }

    public Pane findPaneByName(String name) {
        for (Pane p : mPanes) {
            if (name == null && p.getName() == null || name != null && name.equals(p.getName())) {
                return p;
            }
        }
        return null;
    }
}
