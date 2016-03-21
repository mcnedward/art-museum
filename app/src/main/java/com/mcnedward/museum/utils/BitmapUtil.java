package com.mcnedward.museum.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.mcnedward.museum.enums.ScalingLogic;

/**
 * Created by Edward on 3/17/2016.
 */
public class BitmapUtil {

    public static Bitmap scaleBitmap(Bitmap bitmap, int dstWidth, int dstHeight, ScalingLogic scalingLogic) {
        Rect srcRect = calculateSrcRect(bitmap.getWidth(), bitmap.getHeight(), dstWidth, dstHeight, scalingLogic);
        Rect dstRect = calculateSrcRect(bitmap.getWidth(), bitmap.getHeight(), dstWidth, dstHeight, scalingLogic);
        Bitmap scaledBitmap = Bitmap.createBitmap(dstRect.width(), dstRect.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.drawBitmap(bitmap, srcRect, dstRect, new Paint(Paint.FILTER_BITMAP_FLAG));
        return scaledBitmap;
    }

    private static Rect calculateSrcRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight, ScalingLogic scalingLogic) {
        if (scalingLogic == ScalingLogic.CROP) {
            final float srcAspect = (float) srcWidth / (float) srcHeight;
            final float dstAspect = (float) dstWidth / (float) dstWidth;

            if (srcAspect > dstAspect) {
                final int srcRectWidth = (int) (srcHeight * dstAspect);
                final int srcRectLeft = (srcWidth - srcRectWidth) / 2;
                return new Rect(srcRectLeft, 0, srcRectLeft + srcRectWidth, srcHeight);
            } else {
                final int srcRectHeight = (int) (srcWidth / dstAspect);
                final int srcRectTop = (srcHeight - srcRectHeight) / 2;
                return new Rect(0, srcRectTop, srcWidth, srcRectTop + srcRectHeight);
            }
        } else
            return new Rect(0, 0, srcWidth, srcHeight);
    }

    private static Rect calculateDstRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight, ScalingLogic scalingLogic) {
        if (scalingLogic == ScalingLogic.FIT) {
            final float srcAspect = (float) srcWidth / (float) srcHeight;
            final float dstAspect = (float) dstWidth / (float) dstWidth;

            if (srcAspect > dstAspect)
                return new Rect(0, 0, dstWidth, (int) (dstWidth / srcAspect));
            else
                return new Rect(0, 0, (int) (dstHeight * srcAspect), dstHeight);
        } else
            return new Rect(0, 0, dstWidth, dstHeight);
    }

    public static Bitmap decodeFile(String pathName, int dstWidth, int dstHeight, ScalingLogic scalingLogic) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options.inJustDecodeBounds = false;
        options.inSampleSize = calculateSampleSize(options.outWidth, options.outHeight, dstWidth, dstHeight, scalingLogic);
        Bitmap unscaledBitmap = BitmapFactory.decodeFile(pathName, options);
        return unscaledBitmap;
    }

    private static int calculateSampleSize(int srcWidth, int srcHeight, int dstWidth, int dstHeight, ScalingLogic scalingLogic) {
        final float srcAspect = (float) srcWidth / (float) srcHeight;
        final float dstAspect = (float) dstWidth / (float) dstWidth;

        if (scalingLogic == ScalingLogic.FIT) {
            if (srcAspect > dstAspect)
                return srcWidth / dstWidth;
            else
                return srcHeight / dstHeight;
        } else {
            if (srcAspect > dstAspect)
                return srcHeight / dstHeight;
            else
                return srcWidth / dstWidth;
        }
    }

}
