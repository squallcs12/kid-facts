package com.eezy.kidfacts.fragment;

import com.eezy.kidfacts.MainActivity;
import com.eezy.kidfacts.util.Util;

public class ExtremelyDifficultMatchingFragment extends BaseMatchingFragment {

    public static final String TAG = ExtremelyDifficultMatchingFragment.class.getSimpleName();

    public static void launch(MainActivity activity) {
        Util.launchFragment(activity, new ExtremelyDifficultMatchingFragment(), 3, 3, TAG, true);
    }
}
