package com.mcnedward.museum.model;

import android.graphics.Bitmap;

import com.mcnedward.museum.view.MediaCard;

/**
 * Created by Edward on 3/25/2016.
 */
public interface IMedia {

    Bitmap getBitmap();

    MediaCard getMediaCard();

}
