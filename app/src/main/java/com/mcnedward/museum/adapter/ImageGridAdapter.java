package com.mcnedward.museum.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;

import com.mcnedward.museum.listener.BitmapListener;
import com.mcnedward.museum.model.Folder;
import com.mcnedward.museum.model.Image;
import com.mcnedward.museum.view.ImageCard;
import com.mcnedward.museum.view.MediaCard;

import java.util.List;

/**
 * Created by Edward on 3/17/2016.
 */
public class ImageGridAdapter extends BaseListAdapter<Image> implements BitmapListener {
    private static final String TAG = "ImageGridAdapter";

    public ImageGridAdapter(Context context) {
        super(context);
    }

    public ImageGridAdapter(Context context, List<Image> images) {
        super(context, images);
    }

    @Override
    protected MediaCard createNewView(Image image) {
        return new ImageCard(context, image);
    }

    @Override
    protected void setViewContent(Image image, MediaCard mediaCard) {
        ImageCard imageCard = (ImageCard) mediaCard;
        imageCard.updateItem(image);
    }

    @Override
    protected String getBitmapPathFromItem(Image item) {
        return item.getPath();
    }

    @Override
    protected View.OnClickListener getOnClickListener(Image image) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }
}
