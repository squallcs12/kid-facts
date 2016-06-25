package com.eezy.kidfacts.fragment;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.eezy.kidfacts.KidFactsApplication;
import com.eezy.kidfacts.MainActivity;
import com.eezy.kidfacts.R;
import com.eezy.kidfacts.model.Pane;
import com.eezy.kidfacts.util.DeviceUtil;
import com.eezy.kidfacts.util.GeoUtil;
import com.eezy.kidfacts.util.Util;
import com.eezy.kidfacts.view.Character;
import com.eezy.kidfacts.view.MultiPaneView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ObjectPickFragment extends Fragment implements MultiPaneView.OnTouchListener {

    public static final String TAG = ObjectPickFragment.class.getSimpleName();
    public static final int MAX_NUM_OF_SELECTION = 4;

    public static void launch(MainActivity activity) {
        Util.launchFragment(activity, new ObjectPickFragment(), 4, 5, TAG, true);
    }

    private MultiPaneView mMultiPaneView;
    private List<Pane> mSelectedPanes;
    private List<Pane> mSelectedClonedPanes;
    private MainActivity mActivity;
    private Random mRandom;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = (MainActivity) getActivity();
        mRandom = KidFactsApplication.random;
        View v = inflater.inflate(R.layout.fragment_object_pick, container, false);
        mMultiPaneView = (MultiPaneView) v.findViewById(R.id.multi_pane_view);
        setup(mMultiPaneView);
        mSelectedPanes = new ArrayList<>();
        mSelectedClonedPanes = new ArrayList<>();
        return v;
    }

    private void setup(MultiPaneView multiPaneView) {
        Point screenSize = DeviceUtil.getScreenSize(getActivity());
        int halfPaneSize = (int)(screenSize.y * 0.18);

        List<Point> centers = new ArrayList<>();
        centers.add(new Point(screenSize.x / 2, screenSize.y * 37 / 90));
        centers.add(new Point(screenSize.x / 2, (int)(screenSize.y * 0.8)));
        centers.add(new Point((int)(screenSize.x * 0.18), 3 * screenSize.y / 4));
        centers.add(new Point((int)(screenSize.x * (1 - 0.18)), 3 * screenSize.y / 4));
        centers.add(new Point((int)(screenSize.x * 0.1), screenSize.y / 3));
        centers.add(new Point((int)(screenSize.x * (1 - 0.1)), screenSize.y / 3));
        centers.add(new Point((int)(screenSize.x * 0.31), (int)(screenSize.y  * 0.55)));
        centers.add(new Point((int)(screenSize.x * (1 - 0.31)), (int)(screenSize.y * 0.55)));

        Collections.shuffle(centers, mRandom);
        int[] resIds = new int[] {
            R.drawable.content_bad_grades,
            R.drawable.content_bad_grades,
            R.drawable.content_bad_grades,
            R.drawable.content_bad_grades,
            R.drawable.content_bad_grades,
            R.drawable.content_bad_grades,
            R.drawable.content_bad_grades,
            R.drawable.content_bad_grades
        };

        Pane bad1 = new Pane("bad1", GeoUtil.inflate(centers.get(0), halfPaneSize));
        bad1.setBackground(mActivity, resIds[0]);
        Pane bad2 = new Pane("bad2", GeoUtil.inflate(centers.get(1), halfPaneSize));
        bad2.setBackground(mActivity, resIds[1]);
        Pane bad3 = new Pane("bad3", GeoUtil.inflate(centers.get(2), halfPaneSize));
        bad3.setBackground(mActivity, resIds[2]);
        Pane bad4 = new Pane("bad4", GeoUtil.inflate(centers.get(3), halfPaneSize));
        bad4.setBackground(mActivity, resIds[3]);
        Pane good1 = new Pane("good1", GeoUtil.inflate(centers.get(4), halfPaneSize));
        good1.setBackground(mActivity, resIds[4]);
        Pane good2 = new Pane("good2", GeoUtil.inflate(centers.get(5), halfPaneSize));
        good2.setBackground(mActivity, resIds[5]);
        Pane good3 = new Pane("good3", GeoUtil.inflate(centers.get(6), halfPaneSize));
        good3.setBackground(mActivity, resIds[6]);
        Pane good4 = new Pane("good4", GeoUtil.inflate(centers.get(7), halfPaneSize));
        good4.setBackground(mActivity, resIds[7]);

        multiPaneView.addPane(bad1);
        multiPaneView.addPane(bad2);
        multiPaneView.addPane(bad3);
        multiPaneView.addPane(bad4);
        multiPaneView.addPane(good1);
        multiPaneView.addPane(good2);
        multiPaneView.addPane(good3);
        multiPaneView.addPane(good4);

        multiPaneView.registerOnTouchListener(this);
    }

    @Override
    public void onTouch(Pane touchedPane, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (touchedPane != null) {
                if (mSelectedPanes.contains(touchedPane)) {
                    Pane clonedPane = mSelectedClonedPanes.get(mSelectedPanes.indexOf(touchedPane));
                    mMultiPaneView.removePane(clonedPane);
                    mMultiPaneView.invalidate();
                    mSelectedPanes.remove(touchedPane);
                    mSelectedClonedPanes.remove(clonedPane);
                } else if (mSelectedClonedPanes.contains(touchedPane)) {
                    Pane originalTouchPane = mSelectedPanes.get(mSelectedClonedPanes.indexOf(touchedPane));
                    mMultiPaneView.removePane(touchedPane);
                    mMultiPaneView.invalidate();
                    mSelectedPanes.remove(originalTouchPane);
                    mSelectedClonedPanes.remove(touchedPane);
                } else if (mSelectedPanes.size() < MAX_NUM_OF_SELECTION) {
                    Pane clonedPane = touchedPane.cloneWithoutBackground();
                    clonedPane.setBackground(mActivity, R.drawable.btn_music_on);
                    clonedPane.setAlpha(190);
                    mMultiPaneView.addPane(clonedPane);
                    mMultiPaneView.invalidate();
                    mSelectedPanes.add(touchedPane);
                    mSelectedClonedPanes.add(clonedPane);
                    verifyResult();
                }
            }
        }
    }

    private void verifyResult() {
        if (mSelectedPanes.size() == MAX_NUM_OF_SELECTION) {
            for (Pane pane : mSelectedPanes) {
                if (pane.getName().contains("good")) {
                    //Not done yet
                    showResult(false);
                    return;
                }
            }
            // Correct answer, move on to the next step
            showResult(true);
        }
    }

    private void showResult(final boolean correctResult) {
        if (correctResult) {
            Character.sayYay3(mActivity);
        } else {
            Character.sayOhNo(mActivity);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Character.remove(mActivity);
                if (correctResult) {
                    //Not decided yet
                } else {
                    mMultiPaneView.removePanes(mSelectedClonedPanes);
                    mSelectedPanes.clear();
                    mSelectedClonedPanes.clear();
                    mMultiPaneView.invalidate();
                }
            }
        }, 1000);
    }
}
