package com.eezy.kidfacts.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.eezy.kidfacts.MainActivity;
import com.eezy.kidfacts.R;
import com.eezy.kidfacts.callback.AnimationWithDelayListener;
import com.eezy.kidfacts.util.DeviceUtil;
import com.eezy.kidfacts.view.PageTitleView;

public abstract class BaseHighScoreFragment extends Fragment {

    public static final String TAG = BaseHighScoreFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final MainActivity activity = (MainActivity) getActivity();
        final View v = inflater.inflate(R.layout.fragment_high_score, container, false);
        setBackground((ImageView) v.findViewById(R.id.background));
        setFrameBackground((ImageView) v.findViewById(R.id.frame_background));

        final PageTitleView pageTitleView = (PageTitleView) v.findViewById(R.id.page_title_view);
        pageTitleView.setPageTitlePadding(0, 0, 0, DeviceUtil.dpToPx(4));
        setPageTitle(pageTitleView);

        Animation anim = AnimationUtils.loadAnimation(activity, R.anim.zoom_out_and_rotate);
        anim.setAnimationListener(new AnimationWithDelayListener(2800) {
            @Override
            public void onAnimationJustEnd() {
                activity.sayWow2();
            }

            @Override
            public void onAnimationEndWithDelay() {
                if (!activity.isFinishing() && isAdded()) {
                    ((ViewGroup) v).removeView(pageTitleView);
                }
            }
        });

        pageTitleView.startAnimation(anim);
        ((TextView) v.findViewById(R.id.high_score)).setText("Your score is: " + activity.getScore());
        return v;
    }

    protected abstract void setBackground(ImageView background);
    protected abstract void setFrameBackground(ImageView frameBackground);
    protected abstract void setPageTitle(PageTitleView pageTitleView);
}
