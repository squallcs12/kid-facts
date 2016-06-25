package com.eezy.kidfacts.content;

import com.eezy.kidfacts.R;
import com.eezy.kidfacts.model.Case;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Content {

    private static final List<Case> CASES = Arrays.asList(
            new Case(R.drawable.eyes_brown_happy, R.drawable.eyes_happy, R.drawable.mouth_happy),
            new Case(R.drawable.eyes_brown_sad, R.drawable.eyes_sad, R.drawable.mouth_sad),
            new Case(R.drawable.eyes_brown_annoying, R.drawable.eyes_annoying, R.drawable.mouth_annoying));

    private static final HashMap<Integer, String> CASE_DESCRIPTIONS = new HashMap<Integer, String>() {{
        put(R.drawable.eyes_brown_happy, "Happy Eyebrows");
        put(R.drawable.eyes_brown_sad, "Sad Eyebrows");
        put(R.drawable.eyes_brown_annoying, "Angry Eyebrows");
        put(R.drawable.eyes_happy, "Happy Eyes");
        put(R.drawable.eyes_sad, "Sad Eyes");
        put(R.drawable.eyes_annoying, "Angry Eyes");
        put(R.drawable.mouth_happy, "Happy Mouth");
        put(R.drawable.mouth_sad, "Sad Mouth");
        put(R.drawable.mouth_annoying, "Angry Mouth");
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
