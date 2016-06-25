package com.eezy.kidfacts.content;

import com.eezy.kidfacts.R;
import com.eezy.kidfacts.model.Case;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Content {

    private static final List<Case> CASES = Arrays.asList(
            new Case(R.drawable.content_drunk, R.drawable.content_driving, R.drawable.content_car_accident),
            new Case(R.drawable.content_drunk, R.drawable.content_take_a_taxi, R.drawable.content_go_safe),
            new Case(R.drawable.content_chop_tree, R.drawable.content_litter, R.drawable.content_kill_environment),
            new Case(R.drawable.content_plant_tree, R.drawable.content_no_liter, R.drawable.content_protect_earth),
            new Case(R.drawable.content_hard_study, R.drawable.content_be_careful, R.drawable.content_good_grades),
            new Case(R.drawable.content_hard_study, R.drawable.content_negligent, R.drawable.content_bad_grades),
            new Case(R.drawable.content_no_study, R.drawable.content_negligent, R.drawable.content_bad_grades),
            new Case(R.drawable.content_pay_attention, R.drawable.content_do_exercises, R.drawable.content_understand_lesson),
            new Case(R.drawable.content_wakeup_early, R.drawable.content_go_to_school, R.drawable.content_on_time));

    private static final HashMap<Integer, String> CASE_DESCRIPTIONS = new HashMap<Integer, String>() {{
        put(R.drawable.content_drunk, "Drunk");
        put(R.drawable.content_driving, "Driving");
        put(R.drawable.content_car_accident, "Accident");
        put(R.drawable.content_take_a_taxi, "Take a taxi");
        put(R.drawable.content_go_safe, "");
        put(R.drawable.content_chop_tree, "Chop tree");
        put(R.drawable.content_litter, "Liter");
        put(R.drawable.content_kill_environment, "Kill earth");
        put(R.drawable.content_no_liter, "No liter");
        put(R.drawable.content_plant_tree, "Plant tree");
        put(R.drawable.content_protect_earth, "Protect earth");
        put(R.drawable.content_hard_study, "Hard study");
        put(R.drawable.content_be_careful, "Careful");
        put(R.drawable.content_good_grades, "Good grades");
        put(R.drawable.content_negligent, "Negligent");
        put(R.drawable.content_bad_grades, "Bad grades");
        put(R.drawable.content_no_study, "No study");
        put(R.drawable.content_pay_attention, "Pay attention");
        put(R.drawable.content_do_exercises, "Do exercises");
        put(R.drawable.content_understand_lesson, "Understand\nlessons");
        put(R.drawable.content_go_to_school, "Go to school");
        put(R.drawable.content_wakeup_early, "Wake up\nearly");
        put(R.drawable.content_on_time, "On time");
    }};

    public static List<Case> getCases() {
        return new ArrayList<>(CASES);
    }

    public static String getCaseDescription(int caseId) {
        return CASE_DESCRIPTIONS.get(caseId);
    }

    public static boolean isMatched(String cause1, String cause2, String result) {
        return isMatched(Integer.valueOf(cause1), Integer.valueOf(cause2), Integer.valueOf(result));
    }

    public static boolean isMatched(int cause1, int cause2, int result) {
        for (Case aCase : CASES) {
            if (aCase.result == result) {
                if ((cause1 == aCase.cause1 || cause1 == aCase.cause2) &&
                        (cause2 == aCase.cause1 || cause2 == aCase.cause2)) {
                    return true;
                }
            }
        }
        return false;
    }
}
