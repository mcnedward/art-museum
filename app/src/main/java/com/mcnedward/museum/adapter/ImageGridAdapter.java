package com.mcnedward.museum.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;

import com.mcnedward.museum.listener.BitmapListener;
import com.mcnedward.museum.model.Folder;
import com.mcnedward.museum.model.Image;
import com.mcnedward.museum.view.ImageCard;

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
    protected View getCustomView(Image image) {
        return new ImageCard(context, image);
    }

    @Override
    protected void setViewContent(Image image, View view) {
        ImageCard imageCard = (ImageCard) view;
        imageCard.updateImage(image);
    }

    @Override
    protected View.OnClickListener getOnClickListener(Image image) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }

    @Override
    public void notifyBitmapLoaded(Bitmap bitmap) {
        notifyDataSetChanged();
    }
}
