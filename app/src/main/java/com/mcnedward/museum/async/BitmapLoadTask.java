package com.mcnedward.museum.async;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.mcnedward.museum.enums.ImageSize;
import com.mcnedward.museum.enums.ScalingLogic;
import com.mcnedward.museum.utils.BitmapUtil;
import com.mcnedward.museum.view.MediaCard;

import java.lang.ref.WeakReference;

/**
 * Created by Edward on 3/20/2016.
 * Source: http://developer.android.com/training/displaying-bitmaps/process-bitmap.html
 */
public class BitmapLoadTask extends AsyncTask<String, Void, Bitmap> {
    private static final String TAG = "BitmapLoadTask";

    public String bitmapPath;
    private final WeakReference<MediaCard> mediaCardWeakReference;

    public BitmapLoadTask(MediaCard mediaCard, String bitmapPath) {
        this.bitmapPath = bitmapPath;
        mediaCardWeakReference = new WeakReference<>(mediaCard);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap result = null;
        Bitmap unscaledBitmap = BitmapUtil.decodeFile(bitmapPath, ImageSize.LARGE.size, ImageSize.LARGE.size, ScalingLogic.FIT);
        if (unscaledBitmap != null) {
            result = BitmapUtil.scaleBitmap(unscaledBitmap, ImageSize.LARGE.size, ImageSize.LARGE.size, ScalingLogic.FIT);
        }
        return result;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }
        if (mediaCardWeakReference != null && bitmap != null) {
            final MediaCard mediaCard = mediaCardWeakReference.get();
            if (mediaCard == null) return;
            final BitmapLoadTask task = BitmapUtil.getBitmapLoadTask(mediaCard.getImageView());
            if (this == task && mediaCard != null) {
                mediaCard.setImage(bitmap);
            }
        }
    }
}
