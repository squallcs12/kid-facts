package com.eezy.kidfacts.fragment;

import android.widget.ImageView;

import com.eezy.kidfacts.MainActivity;
import com.eezy.kidfacts.R;
import com.eezy.kidfacts.util.Util;
import com.eezy.kidfacts.view.PageTitleView;

public class HappyHighScoreFragment extends BaseHighScoreFragment {

    public static final String TAG = HappyHighScoreFragment.class.getSimpleName();

    public static void launch(MainActivity activity) {
        Util.launchFragment(activity, new HappyHighScoreFragment(), 0, 0, TAG, false);
    }

    @Override
    protected void setBackground(ImageView background) {
        background.setImageResource(R.drawable.bk_happy_with_effect);
    }

    @Override
    protected void setFrameBackground(ImageView frameBackground) {
        frameBackground.setImageResource(R.drawable.bk_happy_frame);
    }

    @Override
    protected void setPageTitle(PageTitleView pageTitleView) {
        pageTitleView.setPageTitleColor(getResources().getColor(R.color.happy_title));
        pageTitleView.setPageSubtitleColor(getResources().getColor(R.color.happy_subtitle));
        pageTitleView.setPageTitle(getString(R.string.congrat));
        pageTitleView.setPageSubTitle(getString(R.string.you_have_finished));
    }
}
