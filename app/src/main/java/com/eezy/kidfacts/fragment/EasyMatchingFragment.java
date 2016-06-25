package com.eezy.kidfacts.fragment;

import com.eezy.kidfacts.MainActivity;
import com.eezy.kidfacts.util.Util;

public class EasyMatchingFragment extends BaseMatchingFragment {

    public static final String TAG = EasyMatchingFragment.class.getSimpleName();

    public static void launch(MainActivity activity) {
        Util.launchFragment(activity, new EasyMatchingFragment(), 1, 3, TAG, true);
    }
}
