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

public class EasyMatchingFragment extends BaseMatchingFragment {

    public static final String TAG = EasyMatchingFragment.class.getSimpleName();

    public static void launch(MainActivity activity) {
        Util.launchFragment(activity, new EasyMatchingFragment(), 1, 3, TAG, true);
    }

    private List<Integer> caseIndices;
    private int missingItem;
    private int missingIndex;
    private int index;

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
        int id;

        switch (resIds.remove(0)) {
            case 0:
                targetPanes.add(cause1);
                id = resIds.remove(0);
                setPaneBackgroundAndDescription(cause2, id, Content.getCaseDescription(id));
                id = resIds.remove(0);
                setPaneBackgroundAndDescription(result, id, Content.getCaseDescription(id));
                break;
            case 1:
                id = resIds.remove(0);
                setPaneBackgroundAndDescription(cause1, id, Content.getCaseDescription(id));
                targetPanes.add(cause2);
                id = resIds.remove(0);
                setPaneBackgroundAndDescription(result, id, Content.getCaseDescription(id));
                break;
            default:
                id = resIds.remove(0);
                setPaneBackgroundAndDescription(cause1, id, Content.getCaseDescription(id));
                id = resIds.remove(0);
                setPaneBackgroundAndDescription(cause2, id, Content.getCaseDescription(id));
                targetPanes.add(result);
                break;
        }

        return targetPanes;
    }

    @Override
    protected List<Point> getConnectionPoints() {
        List<Point> result = new ArrayList<>();
        result.add(new Point(missingIndex, missingItem));
        return result;
    }

    @Override
    protected List<Integer> retrieveResources() {
        List<Integer> result = new ArrayList<>();
        Random r = KidFactsApplication.random;
        List<Case> cases = Content.getCases();

        Case currentCase;
        if (mActivity.mDemoScript != null) {
            currentCase = mActivity.mDemoScript.getEasyCase();
            cases.remove(currentCase);
        } else {
            currentCase = cases.remove((int) (caseIndices.get(index)));
        }

        index = (index + 1) % caseIndices.size();
        missingIndex = r.nextInt(3);
        if (mActivity.mDemoScript != null) {
            missingIndex = mActivity.mDemoScript.getEasyHiddenItemIndex();
        }

        List<Integer> items = currentCase.split();
        missingItem = items.remove(missingIndex);

        result.add(missingIndex);
        result.addAll(items);

        List<Integer> candidateItems = new ArrayList<>();
        candidateItems.add(missingItem);

        List<Integer> temp = new ArrayList<>();
        for (Case c : cases) {
            temp.addAll(c.split());
        }

        candidateItems.addAll(Util.pickRandomlyAvoidDuplication(temp, r, 4));
        Collections.shuffle(candidateItems, r);

        result.addAll(candidateItems);
        return result;
    }
}
