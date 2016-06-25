package com.eezy.kidfacts.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class NavigationControl extends RelativeLayout {

    private List<View> mSteps;
    private View mHomeBtn;

    public NavigationControl(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.navigation_control, this);

        int[] resIds = new int[] {R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5};
        mSteps = new ArrayList<>();
        for (int id : resIds) {
            mSteps.add(findViewById(id));
        }

        mHomeBtn = findViewById(R.id.btn_home);
    }

    public void setSteps(Integer currentStep, Integer totalNumOfSteps) {
        if (currentStep == null || totalNumOfSteps == null) {
            this.setVisibility(GONE);
        } else {
            //Check valid arguments
            if (currentStep > totalNumOfSteps) {
                throw new InvalidParameterException("Invalid Current Step");
            }

            if (totalNumOfSteps > mSteps.size()) {
                throw new InvalidParameterException("Total number of steps exceeds available steps");
            }

            this.setVisibility(VISIBLE);
            mHomeBtn.setVisibility(VISIBLE);

            if (totalNumOfSteps <= 0) {
                for (View step : mSteps) {
                    step.setVisibility(GONE);
                }
            } else {
                for (int i = 0; i < mSteps.size(); ++i) {
                    if (i < totalNumOfSteps) {
                        mSteps.get(i).setVisibility(VISIBLE);
                    } else {
                        mSteps.get(i).setVisibility(GONE);
                    }
                    mSteps.get(i).setSelected(false);
                }

                for (int i = 0; i < currentStep - 1; ++i) {
                    mSteps.get(i).setSelected(true);
                }
            }
        }
    }
}
