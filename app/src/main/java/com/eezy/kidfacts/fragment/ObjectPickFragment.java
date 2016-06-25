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

    public static void launch(MainActivity activity) {
        Util.launchFragment(activity, new ObjectPickFragment(), 1, 3, TAG, false);
    }

    private int MAX_NUM_OF_SELECTION = 5;

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
        int ver = (int) (screenSize.y * 0.27);
        int side = (int) ((ver) * 0.999);

        Point resultCenter = new Point(screenSize.x / 2, (int) (screenSize.y * 0.33));


        List<Point> candidates = new ArrayList<>();
        candidates.add(new Point(screenSize.x / 2, (int)(screenSize.y * 0.65)));
        candidates.add(new Point((int)(screenSize.x * 0.18), (int) (screenSize.y  * 0.7)));
        candidates.add(new Point((int)(screenSize.x * (1 - 0.18)), (int) (screenSize.y  * 0.7)));
        candidates.add(new Point((int)(screenSize.x * 0.31), (int)(screenSize.y  * 0.45)));
        candidates.add(new Point((int)(screenSize.x * (1 - 0.31)), (int)(screenSize.y * 0.45)));

        Collections.shuffle(candidates, mRandom);
        int[] resIds = new int[] {
            R.drawable.content_bad_grades,
            R.drawable.content_bad_grades,
            R.drawable.content_bad_grades,
            R.drawable.content_bad_grades,
            R.drawable.content_bad_grades
        };

        Pane result = new Pane("result", GeoUtil.inflate(resultCenter, side / 2, ver / 2));
        result.setBackground(mActivity, resIds[0]);

        Pane c1 = new Pane("c1", GeoUtil.inflate(candidates.get(0), side / 2, ver / 2));
        c1.setBackground(mActivity, resIds[0]);
        Pane c2 = new Pane("c1", GeoUtil.inflate(candidates.get(1), side / 2, ver / 2));
        c2.setBackground(mActivity, resIds[1]);
        Pane c3 = new Pane("c1", GeoUtil.inflate(candidates.get(2), side / 2, ver / 2));
        c3.setBackground(mActivity, resIds[2]);
        Pane c4 = new Pane("c1", GeoUtil.inflate(candidates.get(3), side / 2, ver / 2));
        c4.setBackground(mActivity, resIds[3]);
        Pane c5 = new Pane("c1", GeoUtil.inflate(candidates.get(4), side / 2, ver / 2));
        c5.setBackground(mActivity, resIds[4]);

        multiPaneView.addPane(result);
        multiPaneView.addPane(c1);
        multiPaneView.addPane(c2);
        multiPaneView.addPane(c3);
        multiPaneView.addPane(c4);
        multiPaneView.addPane(c5);

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
//        if (mSelectedPanes.size() == MAX_NUM_OF_SELECTION) {
//            for (Pane pane : mSelectedPanes) {
//                if (pane.getName().contains("good")) {
//                    //Not done yet
//                    showResult(false);
//                    return;
//                }
//            }
//            // Correct answer, move on to the next step
//            showResult(true);
//        }
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
