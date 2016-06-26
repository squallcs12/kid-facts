package com.eezy.kidfacts;

import com.eezy.kidfacts.model.Case;

import java.util.Arrays;
import java.util.List;

public class DemoScript {

    private static final List<Case> EASY_CASES = Arrays.asList(
            new Case(R.drawable.content_drunk, R.drawable.content_driving, R.drawable.content_car_accident),
            new Case(R.drawable.content_drunk, R.drawable.content_take_a_taxi, R.drawable.content_go_safe),
            new Case(R.drawable.content_hard_study, R.drawable.content_be_careful, R.drawable.content_good_grades),
            new Case(R.drawable.content_hard_study, R.drawable.content_negligent, R.drawable.content_bad_grades),
            new Case(R.drawable.content_no_study, R.drawable.content_negligent, R.drawable.content_bad_grades),
            new Case(R.drawable.content_wakeup_early, R.drawable.content_go_to_school, R.drawable.content_on_time),
            new Case(R.drawable.content_wakeup_late, R.drawable.content_go_to_school, R.drawable.content_late_for_school),
            new Case(R.drawable.content_in_class, R.drawable.content_talking_in_class, R.drawable.content_teacher_scold),
            new Case(R.drawable.content_good_grades, R.drawable.content_polite, R.drawable.content_grandpa_loves),
            new Case(R.drawable.content_plant_tree, R.drawable.content_no_liter, R.drawable.content_protect_earth));

    private static final List<Integer> EASY_HIDDEN_ITEM = Arrays.asList(
             0, 1, 2, 1, 2, 0, 1, 2, 2
    );

    private static final List<Case> DIFFICULT_CASES = Arrays.asList(
            new Case(R.drawable.content_chop_tree, R.drawable.content_litter, R.drawable.content_kill_environment),
            new Case(R.drawable.content_plant_tree, R.drawable.content_no_liter, R.drawable.content_protect_earth),
            new Case(R.drawable.content_pay_attention, R.drawable.content_do_assignment, R.drawable.content_understand_lesson),
            new Case(R.drawable.content_do_exercises, R.drawable.content_eat_various_food_type, R.drawable.content_healthy));

    private static final List<Integer> DIFFICULT_VISIBLE_ITEM = Arrays.asList(
            2, 2, 2, 2
    );

    private static final List<Case> EXTREMELY_DIFFICULT_CASES = Arrays.asList(
            new Case(R.drawable.content_good_grades, R.drawable.content_polite, R.drawable.content_grandpa_loves),

    private int mEasyCaseIndex;
    private int mEasyHiddenItemIndex;
    private int mDifficultCaseIndex;
    private int mDifficultVisibleItemIndex;
    private int mExtremelyDifficultCaseIndex;

    public DemoScript(){
        mEasyCaseIndex = 0;
        mEasyHiddenItemIndex = 0;
        mDifficultCaseIndex = 0;
        mDifficultVisibleItemIndex = 0;
        mExtremelyDifficultCaseIndex = 0;
    }

    public Case getEasyCase() {
        Case c = EASY_CASES.get(mEasyCaseIndex);
        mEasyCaseIndex = (mEasyCaseIndex + 1) % EASY_CASES.size();
        return c;
    }

    public Case getDifficultCase() {
        Case c = DIFFICULT_CASES.get(mDifficultCaseIndex);
        mDifficultCaseIndex = (mDifficultCaseIndex + 1) % DIFFICULT_CASES.size();
        return c;
    }

    public Case getExtremelyDifficulCase() {
        Case c = EXTREMELY_DIFFICULT_CASES.get(mExtremelyDifficultCaseIndex);
        mExtremelyDifficultCaseIndex = (mExtremelyDifficultCaseIndex + 1) % EXTREMELY_DIFFICULT_CASES.size();
        return c;
    }

    public int getEasyHiddenItemIndex() {
        Integer i = EASY_HIDDEN_ITEM.get(mEasyHiddenItemIndex);
        mEasyHiddenItemIndex = (mEasyHiddenItemIndex + 1) % EASY_HIDDEN_ITEM.size();
        return i;
    }

    public int getDifficultVisibleItemIndex() {
        Integer i = DIFFICULT_VISIBLE_ITEM.get(mDifficultVisibleItemIndex);
        mDifficultVisibleItemIndex = (mDifficultVisibleItemIndex + 1) % DIFFICULT_VISIBLE_ITEM.size();
        return i;
    }

    public void restartDemo() {
        mEasyCaseIndex = 0;
        mEasyHiddenItemIndex = 0;
        mDifficultCaseIndex = 0;
        mDifficultVisibleItemIndex = 0;
        mExtremelyDifficultCaseIndex = 0;
    }
}
