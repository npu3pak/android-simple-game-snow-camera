package com.example.BrightnessFiltering;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: EVSafronov
 * Date: 05.12.13
 * Time: 9:11
 */

//Хелпер, генерирующий список возможных начальных координат для движущихся точек
//Эти координаты соответствуют темным участкам изображения. 1 пиксель == 1 начальная координата
public class ImageAnalyzer {
    public static Position[] getBrightnessFilteredPositions(float brightnessThreshold, Bitmap bmp) {
        List<Position> startPositions = new ArrayList<Position>();
        for (int i = 0; i < bmp.getHeight(); i++)
            for (int j = 0; j < bmp.getWidth(); j++) {
                int pixel = bmp.getPixel(j, i);
                if (getBrightness(pixel) < brightnessThreshold)
                    startPositions.add(new Position(j, i));
            }
        Position[] positions = new Position[startPositions.size()];
        startPositions.toArray(positions);
        return positions;
    }

    /*
        Спер из класса Color. Там этот полезный метод почему-то аннотирован как hide.
    */
    private static float getBrightness(int color) {
        if (color == -1) //Белый цвет
            return 1;
        else {
            int r = (color >> 16) & 0xFF;
            int g = (color >> 8) & 0xFF;
            int b = color & 0xFF;
            int V = Math.max(b, Math.max(r, g));
            return (V / 255.f);
        }
    }
}
