package com.mcnedward.museum.model;

import android.graphics.Bitmap;

import com.mcnedward.museum.listener.BitmapListener;
import com.mcnedward.museum.view.MediaCard;

import java.io.Serializable;

/**
 * Created by Edward on 3/25/2016.
 */
public abstract class Media implements IMedia, BitmapListener, Serializable {

    protected transient Bitmap bitmap;
    protected transient MediaCard mediaCard;

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public MediaCard getMediaCard() {
        return mediaCard;
    }

    public void setMediaCard(MediaCard mediaCard) {
        this.mediaCard = mediaCard;
    }

    @Override
    public void notifyBitmapLoaded(Bitmap bitmap) {
        this.bitmap = bitmap;
        if (mediaCard != null)
            mediaCard.setImage(bitmap);
    }
}
