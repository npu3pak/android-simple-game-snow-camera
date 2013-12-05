package com.example.BrightnessFiltering;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created with IntelliJ IDEA.
 * User: EVSafronov
 * Date: 03.12.13
 * Time: 14:53
 */
public class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawPoint(x, y, paint);
    }
}
