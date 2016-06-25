package com.eezy.kidfacts.view;

import android.graphics.Point;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.eezy.kidfacts.MainActivity;
import com.eezy.kidfacts.R;

public class MovingHand {

    public static void showWithDelay(final Fragment fragment, final ViewGroup viewGroup, final Point startPos, final Point endPos, int delay) {
        final MainActivity activity = (MainActivity) fragment.getContext();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!activity.isFinishing() && fragment.isAdded()) {
                    show(fragment, viewGroup, startPos, endPos);
                }
            }
        }, delay);
    }

    public static void show(final Fragment fragment, final ViewGroup viewGroup, Point startPos, Point endPos) {
        final MainActivity activity = (MainActivity) fragment.getContext();
        final ImageView movingHand = new ImageView(activity);
        movingHand.setImageResource(R.drawable.hand_pointing);

        int s = activity.getResources().getDimensionPixelSize(R.dimen.moving_hand_size);
        viewGroup.addView(movingHand, new ViewGroup.LayoutParams(s, s));

        Animation anim = new TranslateAnimation(Animation.ABSOLUTE, startPos.x - s/2, Animation.ABSOLUTE, endPos.x - s/2,
                Animation.ABSOLUTE, startPos.y, Animation.ABSOLUTE, endPos.y);
        anim.setStartOffset(400);
        anim.setDuration(2000);
        anim.setFillAfter(true);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!activity.isFinishing() && fragment.isAdded()) {
                            viewGroup.removeView(movingHand);
                        }
                    }
                }, 1000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        movingHand.startAnimation(anim);
    }
}
