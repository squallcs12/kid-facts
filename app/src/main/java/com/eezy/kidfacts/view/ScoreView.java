package com.eezy.kidfacts.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eezy.kidfacts.R;

public class ScoreView extends LinearLayout {

    private TextView mScore;

    public ScoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_score, this);
        mScore = (TextView) findViewById(R.id.score);
    }

    public void setScore(int score) {
        mScore.setText(String.valueOf(score));
    }

    public void addScore(int score) {
        int currentScore = Integer.valueOf(mScore.getText().toString());
        int newScore = currentScore + score;
        if (newScore < 0) {
            newScore = 0;
        }
        mScore.setText(String.valueOf(newScore));
    }
}
