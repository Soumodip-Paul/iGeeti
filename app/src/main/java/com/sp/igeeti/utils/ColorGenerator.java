package com.sp.igeeti.utils;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import androidx.annotation.ColorInt;

import java.util.Random;

public class ColorGenerator {
    private ColorGenerator(){}
    /**
     * Returns a random color in Hexadecimal to {@code int} form
     * @param alpha Alpha Component
     * */
    @ColorInt
    public static int getRandomColorInt(int alpha){
        Random random = new Random();
        return Color.argb(
                alpha,
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256));
    }
    /**
     * Returns a random color in {@link Drawable}
     * @param alpha Alpha Component
     * */
    @Deprecated
    public static Drawable getRandomColorDrawable(int alpha){
        return new ColorDrawable(
            getRandomColorInt(alpha)
        );
    }
    @ColorInt
    public static int adjustAlpha(@ColorInt int color, int alpha) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }
    /**
     * Method to create {@link GradientDrawable} from {@link ColorInt}
     * as input
     *
     * @param color Base Color
     * @param toAlpha up to Alpha of the Gradient
     *
     * @return LinearGradient of Base Color
     * */
    public static Drawable createGradientBottomTop(@ColorInt int color,int toAlpha){
        return new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,new int[] {
                color , ColorGenerator.adjustAlpha(color,toAlpha)
        });
    }

}
