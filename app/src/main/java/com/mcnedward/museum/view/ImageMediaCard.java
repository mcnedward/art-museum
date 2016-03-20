package com.mcnedward.museum.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mcnedward.museum.R;
import com.mcnedward.museum.model.Image;

/**
 * Created by Edward on 3/17/2016.
 */
public class ImageMediaCard extends LinearLayout {

    private Context context;
    private int height, width;
    private Image image;
    private ImageView imageView;

    public ImageMediaCard(Context context, Image image) {
        super(context);
        this.context = context;
        this.image = image;
        initialize();
    }

    public ImageMediaCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    private void initialize() {
        if (!isInEditMode()) {
            inflate(context, R.layout.view_images, this);
        }
        imageView = (ImageView) findViewById(R.id.image);
        setupImage();
    }

    public void setImage(Image image) {
        this.image = image;
        setupImage();
    }

    private void setupImage() {
        imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.empty));
    }

}
