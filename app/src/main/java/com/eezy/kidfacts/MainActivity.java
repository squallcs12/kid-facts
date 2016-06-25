package com.eezy.kidfacts;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.eezy.kidfacts.fragment.DifficultMatchingFragment;
import com.eezy.kidfacts.fragment.EasyMatchingFragment;
import com.eezy.kidfacts.fragment.ExtremelyDifficultMatchingFragment;
import com.eezy.kidfacts.fragment.MapFragment;
import com.eezy.kidfacts.util.MediaUtil;
import com.eezy.kidfacts.view.NavigationControl;
import com.eezy.kidfacts.view.ScoreView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private NavigationControl mControl;
    private MediaUtil mMediaUtil;
    private ScoreView mScoreView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_home).setOnClickListener(this);
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        mControl = (NavigationControl) findViewById(R.id.navigation_control);
        mMediaUtil = new MediaUtil(this);
        mScoreView = (ScoreView) findViewById(R.id.score_view);
        mScoreView.setScore(0);
        MapFragment.launch(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onPause() {
        mMediaUtil.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMediaUtil.resume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_home:
                MapFragment.launch(this);
                break;
            case R.id.btn_1:
                EasyMatchingFragment.launch(this);
                break;
            case R.id.btn_2:
                DifficultMatchingFragment.launch(this);
                break;
            case R.id.btn_3:
                ExtremelyDifficultMatchingFragment.launch(this);
                break;
        }
    }

    public void setSteps(Integer currentStep, Integer totalNumOfSteps) {
        mControl.setSteps(currentStep, totalNumOfSteps);
    }

    public void showScore(boolean show) {
        mScoreView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void addScore(int score) {
        mScoreView.addScore(score);
    }

    public void sayYay1() {
        mMediaUtil.sayYay1();
    }
    public void sayYay2() {
        mMediaUtil.sayYay2();
    }
    public void sayYay3() {
        mMediaUtil.sayYay3();
    }

    public void sayOhNo() {
        mMediaUtil.sayOhNo();
    }
    public void sayUhOh1() {
        mMediaUtil.sayUhOh1();
    }
    public void sayUhOh2() {
        mMediaUtil.sayUhOh2();
    }
    public void sayUhOh3() {
        mMediaUtil.sayUhOh3();
    }

    public void sayWow1() {
        mMediaUtil.sayWow1();
    }
    public void sayWow2() {
        mMediaUtil.sayWow2();
    }
    public void sayWow3() {
        mMediaUtil.sayWow3();
    }

    public void allowSound(boolean allow) {
        mMediaUtil.allowSound(allow);
    }
    public void allowBackgroundMusic1(boolean allow) {
        mMediaUtil.allowBackgroundMusic1(allow);
    }
    public void allowBackgroundMusic2(boolean allow) {
        mMediaUtil.allowBackgroundMusic2(allow);
    }
    public boolean isSoundAllowed() {
        return mMediaUtil.isSoundAllowed();
    }
    public boolean isBackgroundMusic1Allowed() {
        return mMediaUtil.isBackgroundMusic1Allowed();
    }
}

