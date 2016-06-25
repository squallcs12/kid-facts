package com.eezy.kidfacts.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.text.TextUtils;

import com.eezy.kidfacts.util.GeoUtil;

import java.util.ArrayList;
import java.util.List;

public class Pane {
    private String mName;
    private List<Point> mVertices;
    private Paint mBorderPaint;

    private Paint mBkPaint;
    private Bitmap mBkBitmap;
    private boolean mIsMovable;

    public Pane(String name) {
        mName = name;
        mVertices = new ArrayList<>();
        mBkPaint = new Paint();
        mBorderPaint = null;
        mBkBitmap = null;
        mIsMovable = false;
    }

    public Pane(String name, Rect boundingRect) {
        this(name);
        addVertex(boundingRect.left, boundingRect.top);
        addVertex(boundingRect.right, boundingRect.top);
        addVertex(boundingRect.right, boundingRect.bottom);
        addVertex(boundingRect.left, boundingRect.bottom);
    }

    public void addVertex(int x, int y) {
        mVertices.add(new Point(x, y));
    }

    public void addVertex(Point point) {
        mVertices.add(point);
    }

    public void setBackground(Context context, int bkResId) {
        setBackground(context, bkResId, null, 0, 0, 0, false);
    }

    public void setBackground(Context context, int bkResId, String text, float textPosPercentX, float textPosPercentY, int textColor, boolean scaleBitmapForText) {
        if (TextUtils.isEmpty(text)) {
            mBkBitmap = BitmapFactory.decodeResource(context.getResources(), bkResId);
        } else {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;
            mBkBitmap = BitmapFactory.decodeResource(context.getResources(), bkResId, options);
            if (scaleBitmapForText) {
                mBkBitmap = scaleBitmap(mBkBitmap);
            }
            Canvas canvas = new Canvas(mBkBitmap);
            Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/UTM Cookies.ttf"));
            textPaint.setTextSize(36);
            textPaint.setColor(textColor);

            int x = (int) (canvas.getWidth() * textPosPercentX);
            int y = (int) (canvas.getHeight() * textPosPercentY);
            for (String line: text.split("\n")) {
                canvas.drawText(line, x, y, textPaint);
                y += textPaint.descent() - textPaint.ascent();
            }
        }

        Shader shader = new BitmapShader(mBkBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        shader.setLocalMatrix(getTransformedMatrix());
        mBkPaint.setShader(shader);
    }

    public Bitmap scaleBitmap(Bitmap input) {
        Bitmap result = Bitmap.createBitmap(input.getWidth(), (int)(input.getWidth() / 0.999), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(result);
        Rect r = new Rect(0, 0, input.getWidth(), input.getHeight());
        c.drawBitmap(input, r, r, new Paint());
        return result;
    }

    public void setBackground(Bitmap background) {
        mBkBitmap = background;
        Shader shader = new BitmapShader(mBkBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        shader.setLocalMatrix(getTransformedMatrix());
        mBkPaint.setShader(shader);
    }

    public Bitmap getBackground() {
        return mBkBitmap;
    }

    public void setRotation(float degrees) {
        Shader shader = mBkPaint.getShader();
        if (shader != null) {
            Matrix m = new Matrix();
            shader.getLocalMatrix(m);
            Point center = center(0, 0);
            m.postRotate(degrees, center.x, center.y);
            shader.setLocalMatrix(m);
        }
    }

    public void setBorder(Paint paint) {
        mBorderPaint = paint;
    }

    public void setMovable(boolean movable) {
        mIsMovable = movable;
    }

    public boolean isMovable() {
        return mIsMovable;
    }

    public void draw(Canvas canvas) {
        Path path = getPath();
        if (path != null) {
            if (mBkBitmap != null) {
                canvas.drawPath(path, mBkPaint);
            }
            if (mBorderPaint != null) {
                canvas.drawPath(path, mBorderPaint);
            }
        }
    }

    public boolean isClicked(int x, int y) {
        return isValid() && GeoUtil.isPointInPolygon(new Point(x, y), mVertices);
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    protected Path getPath() {
        if (!isValid()) {
            return null;
        }

        Path path = new Path();
        Point firstPoint = mVertices.get(0);
        path.moveTo(firstPoint.x, firstPoint.y);

        for (int i = 1; i < mVertices.size(); ++i) {
            Point nextPoint = mVertices.get(i);
            path.lineTo(nextPoint.x, nextPoint.y);
        }
        path.lineTo(firstPoint.x, firstPoint.y);
        return path;
    }

    private boolean isValid() {
        return mVertices != null && mVertices.size() >= 3;
    }

    protected List<Point> getVertices() {
        return mVertices;
    }

    public Rect getBoundingRect() {
        if (!isValid()) {
            return null;
        }

        Point topLeftPivot = new Point(mVertices.get(0));
        Point rightBottomPivot = new Point(mVertices.get(0));
        for (int i = 1; i < mVertices.size(); ++i) {
            Point p = mVertices.get(i);
            topLeftPivot.x = p.x < topLeftPivot.x ? p.x : topLeftPivot.x;
            topLeftPivot.y = p.y < topLeftPivot.y ? p.y : topLeftPivot.y;
            rightBottomPivot.x = p.x > rightBottomPivot.x ? p.x : rightBottomPivot.x;
            rightBottomPivot.y = p.y > rightBottomPivot.y ? p.y : rightBottomPivot.y;
        }
        return new Rect(topLeftPivot.x, topLeftPivot.y, rightBottomPivot.x, rightBottomPivot.y);
    }

    public Point leftTop(int offsetX, int offsetY) {
        Rect r = getBoundingRect();
        return r != null ? new Point(r.left + offsetX, r.top + offsetY) : null;
    }

    public Point rightTop(int offsetX, int offsetY) {
        Rect r = getBoundingRect();
        return r != null ? new Point(r.right + offsetX, r.top + offsetY) : null;
    }

    public Point leftBottom(int offsetX, int offsetY) {
        Rect r = getBoundingRect();
        return r != null ? new Point(r.left + offsetX, r.bottom + offsetY) : null;
    }

    public Point rightBottom(int offsetX, int offsetY) {
        Rect r = getBoundingRect();
        return r != null ? new Point(r.right + offsetX, r.bottom + offsetY) : null;
    }

    public Point center(int offsetX, int offsetY) {
        Rect r = getBoundingRect();
        return r != null ? new Point(r.centerX() + offsetX, r.centerY() + offsetY) : null;
    }

    public int width() {
        Rect r = getBoundingRect();
        return (r != null) ? (r.right - r.left) : -1;
    }

    public int height() {
        Rect r = getBoundingRect();
        return (r != null) ? (r.bottom - r.top) : -1;
    }

    private Matrix getTransformedMatrix() {
        Rect boundingRect = getBoundingRect();
        Matrix result = new Matrix();
        if (boundingRect != null) {
            result.postScale((float)boundingRect.width() / mBkBitmap.getWidth(),
                    (float)boundingRect.height() / mBkBitmap.getHeight());
            result.postTranslate(boundingRect.left, boundingRect.top);
        }
        return result;
    }

    public Pane clone() {
        Pane clonePane = cloneWithoutBackground();
        clonePane.mBkBitmap = mBkBitmap;
        clonePane.mBkPaint = new Paint();
        clonePane.mBkPaint.setAlpha(83);

        if (clonePane.mBkBitmap != null) {
            Shader shader = new BitmapShader(clonePane.mBkBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            shader.setLocalMatrix(clonePane.getTransformedMatrix());
            clonePane.mBkPaint.setShader(shader);
        }
        return clonePane;
    }

    public Pane cloneWithoutBackground() {
        Pane clonePane = newPane(getName() + " Clone");
        for (Point vertex : mVertices) {
            clonePane.addVertex(new Point(vertex));
        }

        clonePane.mBkPaint = new Paint();
        return clonePane;
    }

    protected Pane newPane(String name) {
        return new Pane(name);
    }

    public boolean intersect(Pane pane) {
        Rect src = getBoundingRect();
        Rect dest = pane.getBoundingRect();
        return src != null && dest != null && src.intersect(dest);
    }

    public void setAlpha(int alpha) {
        mBkPaint.setAlpha(alpha);
    }

    public void moveTo(int x, int y) {
        Point leftTop = leftTop(0, 0);
        if (leftTop != null) {
            move(x - leftTop.x, y - leftTop.y);
        }
    }

    public void moveTo(Point point) {
        moveTo(point.x, point.y);
    }

    public void move(int x, int y) {
        for (Point p : mVertices) {
            p.x += x;
            p.y += y;
        }

        if (mBkBitmap != null) {
            mBkPaint.getShader().setLocalMatrix(getTransformedMatrix());
        }
    }

    public void moveCenter(int x, int y) {
        move(x, y);
    }

    public void moveCenterTo(int x, int y) {
        Point center = center(0, 0);
        if (center != null) {
            moveCenter(x - center.x, y - center.y);
        }
    }
}
