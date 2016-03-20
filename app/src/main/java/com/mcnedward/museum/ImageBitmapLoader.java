package com.mcnedward.museum;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.mcnedward.museum.adapter.ImageMediaGridAdapter;
import com.mcnedward.museum.enums.ScalingLogic;

/**
 * Created by Edward on 3/18/2016.
 */
public class ImageBitmapLoader extends AsyncTask<String, Void, Bitmap> {
    private static final String TAG = "ImageBitmapLoader";

    private ImageMediaGridAdapter adapter;

    public ImageBitmapLoader(ImageMediaGridAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap unscaledBitmap = BitmapUtil.decodeFile(params[0], ImageSize.LARGE.size, ImageSize.LARGE.size, ScalingLogic.FIT);
        if (unscaledBitmap != null) {
            return BitmapUtil.scaleBitmap(unscaledBitmap, ImageSize.LARGE.size, ImageSize.LARGE.size, ScalingLogic.FIT);
        }
        return null;
    }

}
