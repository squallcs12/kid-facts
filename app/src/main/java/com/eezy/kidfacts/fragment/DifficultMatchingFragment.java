package com.eezy.kidfacts.fragment;

import com.eezy.kidfacts.MainActivity;
import com.eezy.kidfacts.util.Util;

public class DifficultMatchingFragment extends BaseMatchingFragment {

    public static final String TAG = DifficultMatchingFragment.class.getSimpleName();

    public static void launch(MainActivity activity) {
        Util.launchFragment(activity, new DifficultMatchingFragment(), 2, 3, TAG, true);
    }
}
