package com.mcnedward.museum.async;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.mcnedward.museum.model.Directory;
import com.mcnedward.museum.model.Image;
import com.mcnedward.museum.utils.DirectoryUtil;

import java.util.List;

/**
 * Created by Edward on 3/26/2016.
 */
public class ImageLoader extends AsyncTaskLoader<List<Image>> {

    private Context context;
    private List<Image> images;

    public ImageLoader(Context context, List<Image> images) {
        super(context);
        this.context = context;
        this.images = images;
    }

    @Override
    public List<Image> loadInBackground() {
        for (Image image : images) {
            new BitmapLoadTask(context, image, image).execute();
        }
        return images;
    }
}
