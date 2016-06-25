package com.eezy.kidfacts.util;

import android.graphics.Point;
import android.graphics.Rect;

import com.eezy.kidfacts.model.Pane;

import java.util.List;

public class GeoUtil {

    public static boolean isPointInPolygon(Point point, List<Point> polygon) {
        // Ray casting alogrithm
        // http://rosettacode.org/wiki/Ray-casting_algorithm
        int crossings = 0;

        // for each edge
        for (int i=0; i < polygon.size(); i++) {
            Point a = polygon.get(i);
            int j = i + 1;
            //to close the last edge, you have to take the first point of your polygon
            if (j >= polygon.size()) {
                j = 0;
            }
            Point b = polygon.get(j);
            if (rayCrossesSegment(point, a, b)) {
                crossings++;
            }
        }

        // odd number of crossings?
        return (crossings % 2 == 1);
    }

    private static boolean rayCrossesSegment(Point point, Point a, Point b) {
        // Ray Casting algorithm checks, for each segment, if the point is
        // 1) to the left of the segment and
        // 2) not above nor below the segment.
        // If these two conditions are met, it returns true
        double px = point.y;
        double py = point.x;
        double ax = a.y;
        double ay = a.x;
        double bx = b.y;
        double by = b.x;

        if (ay > by) {
            ax = b.y;
            ay = b.x;
            bx = a.y;
            by = a.x;
        }
        // alter longitude to cater for 180 degree crossings
        if (px < 0 || ax < 0 || bx < 0) {
            px += 360;
            ax += 360;
            bx += 360;
        }
        // if the point has the same latitude as a or b, increase slightly py
        if (py == ay || py == by) py += 0.00000001;


        // if the point is above, below or to the right of the segment, it returns false
        if ((py > by || py < ay) || (px > Math.max(ax, bx))) {
            return false;
        } else if (px < Math.min(ax, bx)) { // if the point is not above, below or to the right and is to the left, return true
            return true;
        } else { // if the two above conditions are not met, you have to compare the slope of segment [a,b] (the red one here) and segment [a,p] (the blue one here) to see if your point is to the left of segment [a,b] or not
            double red = (ax != bx) ? ((by - ay) / (bx - ax)) : Double.POSITIVE_INFINITY;
            double blue = (ax != px) ? ((py - ay) / (px - ax)) : Double.POSITIVE_INFINITY;
            return (blue >= red);
        }
    }

    public static Rect inflate(Point center, int side) {
        return new Rect(center.x - side, center.y - side, center.x + side, center.y + side);
    }

    public static Rect inflate(Point center, int sideX, int sideY) {
        return new Rect(center.x - sideX, center.y - sideY, center.x + sideX, center.y + sideY);
    }

    public static Rect inflate(Rect rect, int sideX, int sideY) {
        return new Rect(rect.left - sideX, rect.top - sideY, rect.right + sideX, rect.bottom + sideY);
    }

    public static Rect deflate(Rect rect, int left, int top, int right, int bottom) {
        return new Rect(rect.left + left, rect.top + top, rect.right - right, rect.bottom - bottom);
    }

    public static double vectorLength(Point vector) {
        return Math.sqrt(vector.x * vector.x + vector.y * vector.y);
    }

    public static double lengthBetween(Point vector1, Point vector2) {
        return vectorLength(new Point(vector2.x - vector1.x, vector2.y - vector1.y));
    }

    public static Point offsetPoint(Point point1, Point point2, int offset) {
        double length = lengthBetween(point1, point2);
        double x = point1.x + offset * (point2.x - point1.x) / length;
        double y = point1.y + offset * (point2.y - point1.y) / length;
        return new Point((int) x, (int) y);
    }

    // Which point in the set is nearest to the given point
    public static Point findNearestPoint(Point p, List<Point> pSet) {
        if (p == null || pSet == null || pSet.size() == 0) {
            return null;
        }

        Point nearestPoint = pSet.get(0);
        double smallestDistance = lengthBetween(p, nearestPoint);

        for (int i = 1; i < pSet.size(); ++i) {
            double d = lengthBetween(p, pSet.get(i));
            if (d < smallestDistance) {
                smallestDistance = d;
                nearestPoint = pSet.get(i);
            }
        }

        return nearestPoint;
    }

    public static Rect getBoundingRect(Pane... panes) {
        if (panes == null || panes.length == 0) {
            return null;
        }

        Rect boundingRect = panes[0].getBoundingRect();
        for (int i = 1; i < panes.length; ++i) {
            Rect candidateRect = panes[i].getBoundingRect();
            boundingRect.left = candidateRect.left < boundingRect.left ? candidateRect.left : boundingRect.left;
            boundingRect.top = candidateRect.top < boundingRect.top ? candidateRect.top : boundingRect.top;
            boundingRect.right = candidateRect.right > boundingRect.right ? candidateRect.right : boundingRect.right;
            boundingRect.bottom = candidateRect.bottom > boundingRect.bottom ? candidateRect.bottom : boundingRect.bottom;
        }
        return boundingRect;
    }
}
