package com.eezy.kidfacts.fragment;

import android.graphics.Paint;
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
import com.eezy.kidfacts.content.Content;
import com.eezy.kidfacts.model.Multicase;
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

    public static void launchWithoutAnim(MainActivity activity) {
        Util.launchFragmentWithoutAnim(activity, new ObjectPickFragment(), 1, 3, TAG, false);
    }

    private MultiPaneView mMultiPaneView;
    private List<Pane> mSelectedPanes;
    private List<Pane> mSelectedClonedPanes;
    private MainActivity mActivity;
    private int mNumOfRealCauses;
    private int mResult;
    private Random mRandom;
    private List<Multicase> mMultiCases;
    private int index;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = (MainActivity) getActivity();
        mRandom = KidFactsApplication.random;
        mMultiCases = Content.getMulticases();
        Collections.shuffle(mMultiCases, mRandom);
        index = 0;

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
        candidates.add(new Point(screenSize.x / 2, (int)(screenSize.y * 0.7)));
        candidates.add(new Point((int)(screenSize.x * 0.18), (int) (screenSize.y  * 0.75)));
        candidates.add(new Point((int)(screenSize.x * (1 - 0.18)), (int) (screenSize.y  * 0.75)));
        candidates.add(new Point((int)(screenSize.x * 0.31), (int)(screenSize.y  * 0.45)));
        candidates.add(new Point((int)(screenSize.x * (1 - 0.31)), (int)(screenSize.y * 0.45)));
        candidates.add(new Point((int)(screenSize.x * 0.1), (int) (screenSize.y * 0.4)));
        candidates.add(new Point((int)(screenSize.x * (1 - 0.1)), (int) (screenSize.y * 0.4)));

        Collections.shuffle(candidates, mRandom);
        List<Integer> resIds = getAMultiCase();
        mNumOfRealCauses = resIds.remove(0);

        multiPaneView.addPane(createBorder(resultCenter, (int) (side * 0.6), (int) (ver * 0.6)));

        mResult = resIds.remove(0);
        Pane result = new Pane("result", GeoUtil.inflate(resultCenter, side / 2, ver / 2));
        setPaneBackgroundAndDescription(result, mResult, Content.getCaseDescription(mResult), getResources().getColor(R.color.happy_subtitle));

        for (int i = 0; i < resIds.size(); ++i) {
            Pane c = new Pane(null, GeoUtil.inflate(candidates.get(i), side / 2, ver / 2));
            setPaneBackgroundAndDescription(c, resIds.get(i), Content.getCaseDescription(resIds.get(i)));
            multiPaneView.addPane(c);
        }

        multiPaneView.addPane(result);
        multiPaneView.registerOnTouchListener(this);
    }

    private Pane createBorder(Point center, int sideX, int sideY) {
        Pane border = new Pane(null, GeoUtil.inflate(center, sideX, sideY));
        Paint borderPaint = new Paint();
        borderPaint.setColor(getContext().getResources().getColor(R.color.happy_subtitle));
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeJoin(Paint.Join.ROUND);
        borderPaint.setStrokeCap(Paint.Cap.ROUND);
        borderPaint.setStrokeWidth(getResources().getDimensionPixelSize(R.dimen.margin_small));
        border.setBorder(borderPaint);
        border.setBackground(getContext(), R.drawable.frame_inner_area);
        return border;
    }

    private void setPaneBackgroundAndDescription(Pane p, int bkResId, String description) {
        setPaneBackgroundAndDescription(p, bkResId, description, getResources().getColor(R.color.happy_title));
    }

    private void setPaneBackgroundAndDescription(Pane p, int bkResId, String description, int color) {
        p.setBackground(getContext(), bkResId, description, 0.5f, 0.96f, color, true);
        p.setName(String.valueOf(bkResId));
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
                } else if (mSelectedPanes.size() < mNumOfRealCauses) {
                    Pane clonedPane = touchedPane.cloneWithoutBackground();
                    clonedPane.setBackground(mActivity, R.drawable.btn_done);
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
        if (mSelectedPanes.size() == mNumOfRealCauses) {
            List<Integer> selectedCauses = new ArrayList<>();
            for (Pane p : mSelectedPanes) {
                selectedCauses.add(Integer.valueOf(p.getName()));
            }

            if (Content.isMatched(mResult, selectedCauses)) {
                showResult(true);
            } else {
                showResult(false);
            }
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
                    mMultiPaneView.removeAllPanes();
                    mSelectedPanes.clear();
                    mSelectedClonedPanes.clear();
                    setup(mMultiPaneView);
                    mMultiPaneView.invalidate();
                } else {
                    mMultiPaneView.removePanes(mSelectedClonedPanes);
                    mSelectedPanes.clear();
                    mSelectedClonedPanes.clear();
                    mMultiPaneView.invalidate();
                }
            }
        }, 1000);
    }

    private List<Integer> getAMultiCase() {
        List<Integer> result = new ArrayList<>();
        List<Multicase> multicases = new ArrayList<>(mMultiCases);

        Multicase aMulticase = multicases.remove(index);
        index = (index + 1) % mMultiCases.size();

        List<Integer> temp = new ArrayList<>();
        List<Integer> temp1 = new ArrayList<>();
        temp1.addAll(aMulticase.causes);
        for (Multicase m : multicases) {
            temp.addAll(m.split());
        }
        Collections.shuffle(temp, mRandom);
        temp1.addAll(temp.subList(0, 7 - aMulticase.causes.size()));
        Collections.shuffle(temp1, mRandom);

        result.add(aMulticase.causes.size());
        result.add(aMulticase.result);
        result.addAll(temp1);
        return result;
    }
}
