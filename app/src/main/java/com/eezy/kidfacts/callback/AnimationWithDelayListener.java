package com.eezy.kidfacts.callback;

import android.os.Handler;
import android.view.animation.Animation;

public abstract class AnimationWithDelayListener implements Animation.AnimationListener{

    private int mDelay;
    private Handler mHandler;

    public AnimationWithDelayListener(int delay) {
        mDelay = delay;
        mHandler = new Handler();
    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        onAnimationJustEnd();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onAnimationEndWithDelay();
            }
        }, mDelay);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public abstract void onAnimationJustEnd();
    public abstract void onAnimationEndWithDelay();
}
