package com.eezy.kidfacts.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eezy.kidfacts.MainActivity;
import com.eezy.kidfacts.R;
import com.eezy.kidfacts.util.DeviceUtil;

public class Character {

    public static void show(Activity activity, int resId, String wording, int bkResId) {
        Character.remove(activity);
        RelativeLayout layout = (RelativeLayout) activity.findViewById(R.id.activity_main);
        if (layout != null) {
            LayoutInflater.from(activity).inflate(R.layout.character, layout);
            ((ImageView) layout.findViewById(R.id.character_image)).setImageResource(resId);
            TextView wordingTv = (TextView) layout.findViewById(R.id.character_wording);
            RelativeLayout wordingLayout = (RelativeLayout) layout.findViewById(R.id.character_wording_container);
            wordingTv.setText(wording);
            wordingLayout.setBackgroundResource(bkResId);

            View v = activity.findViewById(R.id.character);
            v.startAnimation(getAnimation());
        }
    }

    public static void remove(Activity activity) {
        RelativeLayout layout = (RelativeLayout) activity.findViewById(R.id.activity_main);
        if (layout != null) {
            View character = layout.findViewById(R.id.character);
            if (character != null) {
                layout.removeView(character);
            }
        }
    }

    public static void sayYay1(Activity activity) {
        correct(activity);
        ((MainActivity) activity).sayYay1();
    }
    public static void sayYay2(Activity activity) {
        correct(activity);
        ((MainActivity) activity).sayYay2();
    }
    public static void sayYay3(Activity activity) {
        correct(activity);
        ((MainActivity) activity).sayYay3();
    }
    public static void sayOhNo(Activity activity) {
        tryAgain(activity);
        ((MainActivity) activity).sayOhNo();
    }
    public static void sayUhOh1(Activity activity) {
        tryAgain(activity);
        ((MainActivity) activity).sayUhOh1();
    }
    public static void sayUhOh2(Activity activity) {
        tryAgain(activity);
        ((MainActivity) activity).sayUhOh2();
    }
    public static void sayUhOh3(Activity activity) {
        tryAgain(activity);
        ((MainActivity) activity).sayUhOh3();
    }

    private static void correct(Activity activity) {
        show(activity, R.drawable.character_happy, activity.getString(R.string.correct), R.drawable.char_bubble_right);
    }
    private static void tryAgain(Activity activity) {
        show(activity, R.drawable.character_sad, activity.getString(R.string.try_again), R.drawable.char_bubble_retry);
    }

    private static Animation getAnimation() {
        Animation anim = new TranslateAnimation(0, 0, DeviceUtil.dpToPx(100), 0);
        anim.setDuration(225);
        return anim;
    }
}
