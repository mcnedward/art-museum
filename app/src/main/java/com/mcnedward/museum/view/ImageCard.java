package com.mcnedward.museum.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mcnedward.museum.R;
import com.mcnedward.museum.enums.ImageSize;
import com.mcnedward.museum.model.Image;
import com.mcnedward.museum.utils.RippleUtil;

/**
 * Created by Edward on 3/17/2016.
 */
public class ImageCard extends MediaCard<Image> {

    public ImageCard(Context context, Image image) {
        super(context, image);
    }

    @Override
    protected int getResourceId() {
        return R.layout.view_image_card;
    }

    @Override
    protected void initializeCard(Context context, Image image) {
        imageView.setLayoutParams(new LinearLayout.LayoutParams(ImageSize.LARGE.size, ImageSize.LARGE.size));
        imageView.setBackgroundColor(ContextCompat.getColor(context, R.color.Silver));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        RippleUtil.setRippleBackground(this, context);
    }

    @Override
    public void update() {

    }

}
