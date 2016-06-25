package com.eezy.kidfacts.fragment;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.eezy.kidfacts.MainActivity;
import com.eezy.kidfacts.R;
import com.eezy.kidfacts.model.Pane;
import com.eezy.kidfacts.util.DeviceUtil;
import com.eezy.kidfacts.util.Util;
import com.eezy.kidfacts.view.MultiPaneView;

public class MapFragment extends Fragment implements MultiPaneView.OnTouchListener, View.OnClickListener {

    public static final String TAG = MapFragment.class.getSimpleName();
    private static final String HAPPY_STORY = "Happy Story";
    private static final String SAD_STORY = "Sad Story";
    private static final String SHARING_STORY = "Sharing Story";
    private static final String ANNOYING_STORY = "Annoying Story";

    private MainActivity mActivity;
    private ImageView mSoundBtn;
    private ImageView mMusicBtn;

    public static void launch(MainActivity activity) {
        Util.launchFragmentWithoutAnim(activity, new MapFragment(), null, null, TAG, false);
        activity.allowBackgroundMusic2(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = (MainActivity) getActivity();
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        mSoundBtn = (ImageView) v.findViewById(R.id.btn_sound);
        mSoundBtn.setImageResource(mActivity.isSoundAllowed() ? R.drawable.btn_sound_on : R.drawable.btn_sound_off);
        mSoundBtn.setOnClickListener(this);
        mMusicBtn = (ImageView) v.findViewById(R.id.btn_music);
        mMusicBtn.setImageResource(mActivity.isBackgroundMusic1Allowed() ? R.drawable.btn_music_on : R.drawable.btn_music_off);
        mMusicBtn.setOnClickListener(this);
        MultiPaneView multiPaneView = (MultiPaneView) v.findViewById(R.id.multi_pane_view);
        setUpMultiPaneView(multiPaneView);
        multiPaneView.registerOnTouchListener(this);
        return v;
    }

    private void setUpMultiPaneView(MultiPaneView multiPaneView) {
        Point screenSize = DeviceUtil.getScreenSize(mActivity);
        Paint borderPaint = new Paint();
        borderPaint.setColor(Color.BLACK);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeJoin(Paint.Join.ROUND);
        borderPaint.setStrokeWidth(DeviceUtil.dpToPx(8));
        borderPaint.setAntiAlias(true);

        double ratioAB = 0.47;
        double ratioFE = 0.56;
        Point A = new Point(screenSize.x * 17 / 48, 0);
        Point B = new Point(screenSize.x * 16 / 80, screenSize.y);
        Point C = new Point(screenSize.x, screenSize.y * 45 / 160);
        Point D = new Point(screenSize.x - screenSize.x * 110 / 400, screenSize.y);
        Point E = new Point((int)(A.x - ratioAB * (A.x - B.x)), (int)(ratioAB * screenSize.y));
        Point F = new Point((int)(screenSize.x - ratioFE * (screenSize.x - E.x)),
                (int)(E.y - (1 - ratioFE) * (E.y - C.y)));

        Pane happyPane = new Pane(HAPPY_STORY);
        happyPane.addVertex(0, 0);
        happyPane.addVertex(A);
        happyPane.addVertex(B);
        happyPane.addVertex(0, screenSize.y);
        happyPane.setBackground(mActivity, R.drawable.pane_happy, getString(R.string.education), 0.405f, 0.71f, Color.parseColor("#936C43"), false);
        happyPane.setBorder(borderPaint);

        Pane sadPane = new Pane(SAD_STORY);
        sadPane.addVertex(A);
        sadPane.addVertex(screenSize.x, 0);
        sadPane.addVertex(C);
        sadPane.addVertex(E);
        sadPane.setBackground(mActivity, R.drawable.pane_sad, getString(R.string.traffic_safety), 0.5f, 0.64f, Color.WHITE, false);
        sadPane.setBorder(borderPaint);

        Pane sharingPane = new Pane(SHARING_STORY);
        sharingPane.addVertex(E);
        sharingPane.addVertex(F);
        sharingPane.addVertex(D);
        sharingPane.addVertex(B);
        sharingPane.setBackground(mActivity, R.drawable.pane_sharing, getString(R.string.environment), 0.48f, 0.88f, getResources().getColor(R.color.sharing), false);
        sharingPane.setBorder(borderPaint);

        Pane annoyingPane = new Pane(ANNOYING_STORY);
        annoyingPane.addVertex(F);
        annoyingPane.addVertex(C);
        annoyingPane.addVertex(screenSize.x, screenSize.y);
        annoyingPane.addVertex(D);
        annoyingPane.setBackground(mActivity, R.drawable.pane_annoying, getString(R.string.emotions), 0.62f, 0.7f, Color.parseColor("#E9D6BD"), false);
        annoyingPane.setBorder(borderPaint);

        multiPaneView.addPane(happyPane);
        multiPaneView.addPane(sadPane);
        multiPaneView.addPane(sharingPane);
        multiPaneView.addPane(annoyingPane);
        multiPaneView.invalidate();
    }

    @Override
    public void onTouch(Pane touchedPane, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP && touchedPane != null) {
            switch (touchedPane.getName()) {
                case HAPPY_STORY:
                    EasyMatchingFragment.launch(mActivity);
                    break;
                case SAD_STORY:
                    break;
                case SHARING_STORY:
                    break;
                case ANNOYING_STORY:
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sound:
                mActivity.allowSound(!mActivity.isSoundAllowed());
                mSoundBtn.setImageResource(mActivity.isSoundAllowed() ? R.drawable.btn_sound_on : R.drawable.btn_sound_off);
                break;
            case R.id.btn_music:
                mActivity.allowBackgroundMusic1(!mActivity.isBackgroundMusic1Allowed());
                mMusicBtn.setImageResource(mActivity.isBackgroundMusic1Allowed() ? R.drawable.btn_music_on : R.drawable.btn_music_off);
                break;
        }
    }
}
