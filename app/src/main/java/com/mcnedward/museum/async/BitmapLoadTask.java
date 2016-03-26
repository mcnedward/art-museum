package com.mcnedward.museum.async;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.mcnedward.museum.enums.ImageSize;
import com.mcnedward.museum.enums.ScalingLogic;
import com.mcnedward.museum.listener.BitmapListener;
import com.mcnedward.museum.model.Image;
import com.mcnedward.museum.utils.BitmapUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Edward on 3/20/2016.
 */
public class BitmapLoadTask extends AsyncTask<String, Void, Bitmap> {
    private static final String TAG = "BitmapLoadTask";

    private Context context;
    private List<BitmapListener> listeners;
    private Image image;

    public BitmapLoadTask(Context context) {
        this.context = context;
        listeners = new ArrayList<>();
    }

    public BitmapLoadTask(Context context, Image image, BitmapListener... listeners) {
        this(context);
        this.listeners.addAll(Arrays.asList(listeners));
        this.image = image;
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
        if (listeners == null || listeners.size() == 0) return;
        for (BitmapListener listener : listeners)
            listener.notifyBitmapLoaded(bitmap);
    }

}
