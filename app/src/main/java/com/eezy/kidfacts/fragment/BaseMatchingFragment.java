package com.eezy.kidfacts.fragment;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.eezy.kidfacts.MainActivity;
import com.eezy.kidfacts.R;
import com.eezy.kidfacts.content.Content;
import com.eezy.kidfacts.model.Pane;
import com.eezy.kidfacts.util.DeviceUtil;
import com.eezy.kidfacts.util.GeoUtil;
import com.eezy.kidfacts.view.Character;
import com.eezy.kidfacts.view.DragablePaneView;
import com.eezy.kidfacts.view.MovingHand;
import com.eezy.kidfacts.view.PageTitleView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseMatchingFragment extends Fragment {

    public static final String TAG = BaseMatchingFragment.class.getSimpleName();

    private View mView;
    private DragablePaneView mDragablePaneView;
    protected MainActivity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = (MainActivity) getActivity();
        mView = inflater.inflate(R.layout.fragment_matching, container, false);
        mDragablePaneView = (DragablePaneView) mView.findViewById(R.id.dragable_pane_view);

        setupResources();
        setBackground((ImageView) mView.findViewById(R.id.background));
        setTitle((PageTitleView) mView.findViewById(R.id.page_title_view));
        setUpPaneView(mDragablePaneView, true);
        return mView;
    }

    private void setUpPaneView(final DragablePaneView paneView, boolean hasDemo) {
        Point screenSize = DeviceUtil.getScreenSize(mActivity);
        Context context = getContext();
        Point startPos = null;
        Point endPos;

        final List<Integer> resIds = retrieveResources();

        int top = (int) (screenSize.y * 0.27);
        int bottom = (int) (screenSize.y * 0.46);
        int side = 2 * (bottom - top) / 3;
        int margin = (int) (screenSize.x * 0.14);
        int smallMargin = margin / 8;

        final Pane cause1 = new Pane(null);
        cause1.addVertex(margin, top);
        cause1.addVertex(margin + side, top);
        cause1.addVertex(margin + side, bottom);
        cause1.addVertex(margin, bottom);

        final Pane cause2 = new Pane(null);
        cause2.addVertex(screenSize.x / 2 - side / 2, top);
        cause2.addVertex(screenSize.x / 2 + side / 2, top);
        cause2.addVertex(screenSize.x / 2 + side / 2, bottom);
        cause2.addVertex(screenSize.x / 2 - side / 2, bottom);

        Rect boundingRect = GeoUtil.getBoundingRect(cause1, cause2);
        Pane plusSign = new Pane(null, GeoUtil.inflate(new Point(boundingRect.centerX(), boundingRect.centerY()), smallMargin));
        plusSign.setBackground(context, R.drawable.plus_sign);

        final Pane result = new Pane(null);
        result.addVertex(screenSize.x - margin - side, top);
        result.addVertex(screenSize.x - margin, top);
        result.addVertex(screenSize.x - margin, bottom);
        result.addVertex(screenSize.x - margin - side, bottom);

        boundingRect = GeoUtil.getBoundingRect(cause2, result);
        Pane equalSign = new Pane(null, GeoUtil.inflate(new Point(boundingRect.centerX(), boundingRect.centerY()), smallMargin));
        equalSign.setBackground(context, R.drawable.equal_sign);

        final List<Pane> targetPanes = fillInResources(resIds, cause1, cause2, result);
        endPos = targetPanes.get(0).center(0, 0);
        for (Pane p : targetPanes) {
            p.setBackground(context, R.drawable.question_mark);
        }

        Pane border = new Pane(null, GeoUtil.inflate(GeoUtil.getBoundingRect(cause1, result), smallMargin, smallMargin));
        Paint borderPaint = new Paint();
        borderPaint.setColor(context.getResources().getColor(R.color.happy_subtitle));
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeJoin(Paint.Join.ROUND);
        borderPaint.setStrokeCap(Paint.Cap.ROUND);
        borderPaint.setStrokeWidth(getResources().getDimensionPixelSize(R.dimen.margin_small));
        border.setBorder(borderPaint);
        border.setBackground(context, R.drawable.frame_inner_area);
        paneView.addPane(border);

        paneView.addPane(cause1);
        paneView.addPane(plusSign);
        paneView.addPane(cause2);
        paneView.addPane(equalSign);
        paneView.addPane(result);

        DragablePaneView.TargetPaneReachListener listener = new DragablePaneView.TargetPaneReachListener() {
            @Override
            public void onTargetReach(Pane targetPane, Pane draggingPane) {
                targetPane.setBackground(draggingPane.getBackground());
                targetPane.setName(draggingPane.getName());
                verifyResult(cause1, cause2, result);
            }
        };

        final List<Pane> availableOptions = new ArrayList<>();

        for (int i = 0; i < resIds.size(); ++i) {
            Pane pivotPane;
            int offset = (bottom - top) + 3 * smallMargin;

            if (i == 0) {
                pivotPane = cause1;
            } else if (i == 1) {
                pivotPane = plusSign;
            } else if (i == 2) {
                pivotPane = cause2;
            } else if (i == 3) {
                pivotPane = equalSign;
            } else if (i == 4) {
                pivotPane = result;
            } else {
                break;
            }

            Point center = pivotPane.center(0, offset);
            Pane p = new Pane(null, GeoUtil.inflate(center, side / 2, (bottom - top) / 2));
            int id = resIds.get(i);
            setPaneBackgroundAndDescription(p, id, Content.getCaseDescription(id));
            p.setMovable(true);
            paneView.addPane(p);
            availableOptions.add(p);
            for (Pane targetPane : targetPanes) {
                paneView.addTargetPaneFor(targetPane, p, listener);
            }

            if (i == 1) {
                startPos = p.center(0, 0);
            }
        }

        paneView.setGhostMode(true);
        paneView.invalidate();

        if (hasDemo) {
            MovingHand.show(this, (ViewGroup) mView, startPos, endPos);
        }

        mView.findViewById(R.id.btn_help).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Point> connectionPoints = getConnectionPoints();
                for (Point c : connectionPoints) {
                    int missingIndex = c.x;
                    int missingItem = c.y;
                    Point startPos = null;
                    Point endPos = null;

                    if (missingIndex == 0) {
                        endPos = cause1.center(0, 0);
                    } else if (missingIndex == 1) {
                        endPos = cause2.center(0, 0);
                    } else {
                        endPos = result.center(0, 0);
                    }

                    for (Pane p : availableOptions) {
                        int id = Integer.valueOf(p.getName());
                        if (id == missingItem) {
                            startPos = p.center(0, 0);
                            break;
                        }
                    }

                    if (startPos != null && endPos != null) {
                        mActivity.addScore(-2);
                        MovingHand.show(BaseMatchingFragment.this, (ViewGroup) mView, startPos, endPos);
                    }
                }
            }
        });
    }

    protected void setPaneBackgroundAndDescription(Pane p, int bkResId, String description) {
        p.setBackground(getContext(), bkResId, description, 0.5f, 0.96f, getResources().getColor(R.color.desc_color));
        p.setName(String.valueOf(bkResId));
    }

    private void verifyResult(Pane cause1, Pane cause2, Pane result) {
        if (cause1.getBackground() == null || cause2.getBackground() == null || result.getBackground() == null) {
            return;
        }

        if (TextUtils.isEmpty(cause1.getName()) || TextUtils.isEmpty(cause2.getName()) || TextUtils.isEmpty(result.getName())) {
            return;
        }

        if (Content.isMatched(cause1.getName(), cause2.getName(), result.getName())) {
            Character.sayYay1(mActivity);
            mActivity.addScore(3);
            mActivity.mNumOfCorrectAnswers++;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Character.remove(mActivity);
                    if (mActivity.mNumOfCorrectAnswers < 10) {
                        mDragablePaneView.removeAllPanes();
                        mDragablePaneView.resetTargetPaneReachListener();
                        setUpPaneView(mDragablePaneView, false);
                    } else {
                        HappyHighScoreFragment.launch(mActivity);
                    }
                }
            }, 1000);
        } else {
            Character.sayUhOh1(mActivity);
            mActivity.addScore(-1);
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Character.remove(mActivity);
//                }
//            }, 1000);
        }
    }

    protected abstract void setupResources();
    protected abstract List<Integer> retrieveResources();
    protected abstract void setTitle(PageTitleView pageTitleView);
    protected abstract void setBackground(ImageView background);
    protected abstract List<Pane> fillInResources(List<Integer> resIds, Pane cause1, Pane cause2, Pane result);
    protected abstract List<Point> getConnectionPoints();
}
