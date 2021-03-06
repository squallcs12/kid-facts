package com.eezy.kidfacts.content;

import com.eezy.kidfacts.R;
import com.eezy.kidfacts.model.Case;
import com.eezy.kidfacts.model.Multicase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Content {

    private static final List<Case> CASES = Arrays.asList(
            new Case(R.drawable.content_drunk, R.drawable.content_driving, R.drawable.content_car_accident),
            new Case(R.drawable.content_drunk, R.drawable.content_take_a_taxi, R.drawable.content_go_safe),
            new Case(R.drawable.content_chop_tree, R.drawable.content_litter, R.drawable.content_kill_environment),
            new Case(R.drawable.content_plant_tree, R.drawable.content_no_litter, R.drawable.content_protect_earth),
            new Case(R.drawable.content_hard_study, R.drawable.content_be_careful, R.drawable.content_good_grades),
            new Case(R.drawable.content_hard_study, R.drawable.content_negligent, R.drawable.content_bad_grades),
            new Case(R.drawable.content_no_study, R.drawable.content_negligent, R.drawable.content_bad_grades),
            new Case(R.drawable.content_pay_attention, R.drawable.content_do_assignment, R.drawable.content_understand_lesson),
            new Case(R.drawable.content_wakeup_early, R.drawable.content_go_to_school, R.drawable.content_on_time),
            new Case(R.drawable.content_wakeup_late, R.drawable.content_go_to_school, R.drawable.content_late_for_school),
            new Case(R.drawable.content_in_class, R.drawable.content_talking_in_class, R.drawable.content_teacher_scold),
            new Case(R.drawable.content_good_grades, R.drawable.content_polite, R.drawable.content_grandpa_loves),
            new Case(R.drawable.content_do_exercises, R.drawable.content_eat_various_food_type, R.drawable.content_healthy));

    private static final List<Multicase> MULTICASES = Arrays.asList(
            new Multicase(R.drawable.content_kill_environment, Arrays.asList(R.drawable.content_chop_tree, R.drawable.content_litter, R.drawable.content_illegal_hunting, R.drawable.content_pollute_air)),
            new Multicase(R.drawable.content_good_grades, Arrays.asList(R.drawable.content_hard_study, R.drawable.content_be_careful, R.drawable.content_pay_attention)),
            new Multicase(R.drawable.content_grandpa_loves, Arrays.asList(R.drawable.content_polite, R.drawable.content_good_grades, R.drawable.content_hard_study)),
            new Multicase(R.drawable.content_car_accident, Arrays.asList(R.drawable.content_drunk, R.drawable.content_rush, R.drawable.content_run_a_red_light))
    );

    private static final HashMap<Integer, String> CASE_DESCRIPTIONS = new HashMap<Integer, String>() {{
        put(R.drawable.content_drunk, "Drunk");
        put(R.drawable.content_driving, "Driving");
        put(R.drawable.content_car_accident, "Accident");
        put(R.drawable.content_take_a_taxi, "Take a taxi");
        put(R.drawable.content_go_safe, "");
        put(R.drawable.content_chop_tree, "Chop tree");
        put(R.drawable.content_litter, "Litter");
        put(R.drawable.content_kill_environment, "Destroy env");
        put(R.drawable.content_no_litter, "No litter");
        put(R.drawable.content_plant_tree, "Plant tree");
        put(R.drawable.content_protect_earth, "Protect env");
        put(R.drawable.content_hard_study, "Hard study");
        put(R.drawable.content_be_careful, "Careful");
        put(R.drawable.content_good_grades, "Good grades");
        put(R.drawable.content_negligent, "Negligent");
        put(R.drawable.content_bad_grades, "Bad grades");
        put(R.drawable.content_no_study, "No study");
        put(R.drawable.content_pay_attention, "Pay attention");
        put(R.drawable.content_do_assignment, "Do assignments");
        put(R.drawable.content_understand_lesson, "Understand\nlessons");
        put(R.drawable.content_go_to_school, "Go to school");
        put(R.drawable.content_wakeup_early, "Wake up\nearly");
        put(R.drawable.content_on_time, "On time");
        put(R.drawable.content_wakeup_late, "Wake up\nlate");
        put(R.drawable.content_late_for_school, "Late\nfor school");
        put(R.drawable.content_in_class, "In class");
        put(R.drawable.content_talking_in_class, "Talking");
        put(R.drawable.content_teacher_scold, "Teacher scold");
        put(R.drawable.content_polite, "Polite");
        put(R.drawable.content_grandpa_loves, "Parents love");
        put(R.drawable.content_rush, "Rush");
        put(R.drawable.content_run_a_red_light, "Run a\nred light");
        put(R.drawable.content_illegal_hunting, "Illegal\nhunting");
        put(R.drawable.content_pollute_air, "Pollute air");
        put(R.drawable.content_do_exercises, "Do exercises");
        put(R.drawable.content_eat_various_food_type, "Eat various\nfood type");
        put(R.drawable.content_healthy, "Healthy");
    }};

    public static List<Case> getCases() {
        return new ArrayList<>(CASES);
    }

    public static List<Multicase> getMulticases() {
        return new ArrayList<>(MULTICASES);
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

    public static boolean isMatched(int result, List<Integer> possibleCauses) {
        for (Multicase aCase : MULTICASES) {
            if (aCase.result == result && isTheSameList(aCase.causes, possibleCauses)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isTheSameList(List<Integer> l1, List<Integer> l2) {
        if (l1.size() != l2.size()) {
            return false;
        }

        for (int i : l1) {
            if (!l2.contains(i)) {
                return false;
            }
        }

        return true;
    }
}
