package com.sp.igeeti.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

public class BlurBuilder {
    private static final float BITMAP_SCALE = 0.4f;
    private static final float BLUR_RADIUS = 7.5f;

    /**
     * Method to create blurred {@link Bitmap} from a {@link Bitmap}
     * file and a parent {@link Context} file
     *
     * @param context Parent Context
     * @param image Image to be Blurred
     *
     * @return Blurred image in {@link Bitmap}
     * */
    public static Bitmap getBlurBitmap(Context context, Bitmap image) {
        int width = Math.round(image.getWidth() * BITMAP_SCALE);
        int height = Math.round(image.getHeight() * BITMAP_SCALE);

        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;
    }
    /**
     * Method to create blurred {@link Drawable} from a {@link Bitmap}
     * image and a parent {@link Context} file
     *
     * @param context Parent Context
     * @param image Image to be Blurred
     *
     * @return Blurred Image in {@link Drawable} Format
     * */
    public static Drawable getBlurDrawable(Context context,Bitmap image){
        return new BitmapDrawable(context.getResources(),getBlurBitmap(context,image));
    }

}
