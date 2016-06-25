package com.eezy.kidfacts.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eezy.kidfacts.R;

public class PageTitleView extends RelativeLayout {

    private TextView mPageTitle;
    private TextView mPageSubTitle;

    public PageTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_page_title, this);
        mPageTitle = (TextView)findViewById(R.id.page_title);
        mPageSubTitle = (TextView)findViewById(R.id.page_subtitle);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PageTitleView, 0, 0);
        try {
            String title = typedArray.getString(R.styleable.PageTitleView_page_title);
            String titleColor = typedArray.getString(R.styleable.PageTitleView_page_title_color);
            String subTitle = typedArray.getString(R.styleable.PageTitleView_page_subtitle);
            String subTitleColor = typedArray.getString(R.styleable.PageTitleView_page_subtitle_color);
            Drawable shadow = typedArray.getDrawable(R.styleable.PageTitleView_title_shadow);

            mPageTitle.setText(title);
            if (!TextUtils.isEmpty(titleColor)) {
                mPageTitle.setTextColor(Color.parseColor(titleColor));
            }
            mPageSubTitle.setText(subTitle);
            if (!TextUtils.isEmpty(subTitleColor)) {
                mPageSubTitle.setTextColor(Color.parseColor(subTitleColor));
            }
            if (shadow != null) {
                findViewById(R.id.root_layout).setBackground(shadow);
            }
        } finally {
            typedArray.recycle();
        }
    }

    public void setPageTitle(String title) {
        mPageTitle.setText(title);
    }

    public void setPageTitleColor(int color) {
        mPageTitle.setTextColor(color);
    }

    public void setPageTitlePadding(int left, int top, int right, int bottom) {
        mPageTitle.setPadding(left, top, right, bottom);
    }

    public void setPageSubTitle(String subTitle) {
        mPageSubTitle.setText(subTitle);
    }

    public void setPageSubtitleColor(int color) {
        mPageSubTitle.setTextColor(color);
    }

    public void removeSubTitle() {
        mPageSubTitle.setVisibility(GONE);
    }
}
