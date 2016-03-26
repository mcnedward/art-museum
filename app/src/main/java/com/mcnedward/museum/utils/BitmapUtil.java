package com.mcnedward.museum.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.mcnedward.museum.async.BitmapLoadTask;
import com.mcnedward.museum.enums.ScalingLogic;
import com.mcnedward.museum.listener.BitmapListener;
import com.mcnedward.museum.model.Image;
import com.mcnedward.museum.view.MediaCard;

import java.lang.ref.WeakReference;

/**
 * Created by Edward on 3/17/2016.
 */
public class BitmapUtil {

    public static BitmapLoadTask startBitmapLoadTask(final Context context, Image image, BitmapListener listener) {
        if (cancelPotentialWork(image, image.getMediaCard())) {
            final BitmapLoadTask task = new BitmapLoadTask(context, image, listener);
            final AsyncDrawable drawable = new AsyncDrawable(context.getResources(), image.getBitmap(), task);
            image.getMediaCard().getImageView().setImageDrawable(drawable);
            task.execute();
            return task;
        }
        return null;
    }

    public static boolean cancelPotentialWork(Image image, MediaCard mediaCard) {
        final BitmapLoadTask task = getBitmapLoadTask(mediaCard.getImageView());

        if (task != null) {
            final BitmapListener listener = task.listener;
            if (listener == null || listener != image) {
                task.cancel(true);
            } else {
                return false;
            }
        }
        return true;
    }

    private static BitmapLoadTask getBitmapLoadTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapLoadTask();
            }
        }
        return null;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

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

    static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<BitmapLoadTask> bitmapLoadTaskWeakReference;

        public AsyncDrawable(Resources res, Bitmap bitmap, BitmapLoadTask bitmapLoadTask) {
            super(res, bitmap);
            bitmapLoadTaskWeakReference = new WeakReference<BitmapLoadTask>(bitmapLoadTask);
        }

        public BitmapLoadTask getBitmapLoadTask() {
            return bitmapLoadTaskWeakReference.get();
        }
    }
}
