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
            new Case(R.drawable.content_chop_tree, R.drawable.content_litter, R.drawable.content_kill_environment));

    private static final HashMap<Integer, String> CASE_DESCRIPTIONS = new HashMap<Integer, String>() {{
        put(R.drawable.content_drunk, "Drunk");
        put(R.drawable.content_driving, "Driving");
        put(R.drawable.content_car_accident, "Accident");
        put(R.drawable.content_take_a_taxi, "Take a taxi");
        put(R.drawable.content_go_safe, "");
        put(R.drawable.content_chop_tree, "Chop tree");
        put(R.drawable.content_litter, "Litter");
        put(R.drawable.content_kill_environment, "Kill earth");
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
                return (cause1 == aCase.cause1 || cause1 == aCase.cause2) &&
                        (cause2 == aCase.cause1 || cause2 == aCase.cause2);
            }
        }
        return false;
    }
}
