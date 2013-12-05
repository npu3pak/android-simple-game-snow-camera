package com.example.BrightnessFiltering;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: EVSafronov
 * Date: 03.12.13
 * Time: 14:48
 */

//Виджет, занимающийся отрисовкой точек
public class CanvasView extends View {
    public List<Point> bugPoints = Collections.synchronizedList(new ArrayList<Point>());
    public List<Point> shelfPoints = Collections.synchronizedList(new ArrayList<Point>());

    public CanvasView(Context context) {
        super(context);
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public CanvasView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        clearCanvas(canvas, paint);
        drawLetters(canvas, paint);
        invalidate();
    }

    private void clearCanvas(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
    }

    private void drawLetters(Canvas canvas, Paint paint) {
        paint.setColor(Color.YELLOW);
        for (Point point : bugPoints)
            point.draw(canvas, paint);
        for (Point point : shelfPoints)
            point.draw(canvas, paint);
    }
}