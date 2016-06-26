package com.eezy.kidfacts.fragment;

import android.graphics.Point;
import android.widget.ImageView;

import com.eezy.kidfacts.MainActivity;
import com.eezy.kidfacts.KidFactsApplication;
import com.eezy.kidfacts.R;
import com.eezy.kidfacts.content.Content;
import com.eezy.kidfacts.model.Case;
import com.eezy.kidfacts.model.Pane;
import com.eezy.kidfacts.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ExtremelyDifficultMatchingFragment extends BaseMatchingFragment {

    public static final String TAG = ExtremelyDifficultMatchingFragment.class.getSimpleName();

    public static void launch(MainActivity activity) {
        Util.launchFragment(activity, new ExtremelyDifficultMatchingFragment(), 3, 3, TAG, true);
    }

    private List<Integer> caseIndices;
    private int index;
    private List<Integer> missingItems;

    @Override
    protected void setupResources() {
        int n = Content.getCases().size();
        caseIndices = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            caseIndices.add(i);
        }
        Collections.shuffle(caseIndices, KidFactsApplication.random);
        index = 0;
    }

    @Override
    protected void setBackground(ImageView background) {
        background.setImageResource(R.drawable.bk_happy);
    }

    @Override
    protected List<Pane> fillInResources(List<Integer> resIds, Pane cause1, Pane cause2, Pane result) {
        List<Pane> targetPanes = new ArrayList<>();
        targetPanes.add(cause1);
        targetPanes.add(cause2);
        targetPanes.add(result);
        return targetPanes;
    }

    @Override
    protected List<Point> getConnectionPoints() {
        List<Point> result = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            result.add(new Point(i, missingItems.get(i)));
        }
        return result;
    }

    @Override
    protected List<Integer> retrieveResources() {
        List<Integer> result = new ArrayList<>();
        Random r = KidFactsApplication.random;
        List<Case> cases = Content.getCases();

        Case currentCase;
        if (mActivity.mDemoScript != null) {
            currentCase = mActivity.mDemoScript.getExtremelyDifficulCase();
            cases.remove(currentCase);
        } else {
            currentCase = cases.remove((int) (caseIndices.get(index)));
        }

        index = (index + 1) % caseIndices.size();

        List<Integer> items = currentCase.split();
        missingItems = new ArrayList<>(items);

        List<Integer> candidateItems = new ArrayList<>();
        candidateItems.addAll(items);

        List<Integer> temp = new ArrayList<>();
        for (Case c : cases) {
            temp.addAll(c.split());
        }

        candidateItems.addAll(Util.pickRandomlyAvoidDuplication(temp, r, 2));
        Collections.shuffle(candidateItems, r);

        result.addAll(candidateItems);
        return result;
    }
}
