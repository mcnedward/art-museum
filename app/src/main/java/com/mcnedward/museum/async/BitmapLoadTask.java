package com.mcnedward.museum.async;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;

import com.mcnedward.museum.enums.ImageSize;
import com.mcnedward.museum.enums.ScalingLogic;
import com.mcnedward.museum.listener.BitmapListener;
import com.mcnedward.museum.model.Image;
import com.mcnedward.museum.utils.BitmapUtil;

import java.lang.ref.WeakReference;

/**
 * Created by Edward on 3/20/2016.
 */
public class BitmapLoadTask extends AsyncTask<String, Void, Bitmap> {
    private static final String TAG = "BitmapLoadTask";

    private Context context;
    public BitmapListener listener;
    private Image image;
    private final WeakReference<BitmapListener> listenerWeakReference;

    public BitmapLoadTask(Context context, Image image, BitmapListener... listeners) {
        this.context = context;
        this.listener = listeners[0];
        this.image = image;
        listenerWeakReference = new WeakReference<>(listeners[0]);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap result = null;
        Bitmap unscaledBitmap = BitmapUtil.decodeFile(image.getPath(), ImageSize.LARGE.size, ImageSize.LARGE.size, ScalingLogic.FIT);
        if (unscaledBitmap != null) {
            result = BitmapUtil.scaleBitmap(unscaledBitmap, ImageSize.LARGE.size, ImageSize.LARGE.size, ScalingLogic.FIT);
        }
        image.setBitmap(result);
        return result;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap == null || listener == null || listenerWeakReference == null) return;
        final BitmapListener listenerReference = listenerWeakReference.get();
        if (listenerReference != null && listenerReference == listener)
            listenerReference.notifyBitmapLoaded(bitmap);
    }

}
