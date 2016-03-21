package com.mcnedward.museum.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.RippleDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mcnedward.museum.R;
import com.mcnedward.museum.activity.fragment.GalleryFragment;
import com.mcnedward.museum.enums.ImageSize;
import com.mcnedward.museum.model.Image;
import com.mcnedward.museum.utils.RippleUtil;

/**
 * Created by Edward on 3/17/2016.
 */
public class ImageCard extends LinearLayout {

    private Context context;
    private Image image;
    private ImageView imgCard;

    public ImageCard(Context context, Image image) {
        super(context);
        initialize(context, image);
    }

    public ImageCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, null);
    }

    private void initialize(Context context, Image image) {
        this.context = context;
        if (!isInEditMode()) {
            inflate(context, R.layout.view_image_card, this);
        }
        imgCard = (ImageView) findViewById(R.id.card_image);
        imgCard.setLayoutParams(new LinearLayout.LayoutParams(ImageSize.LARGE.size, ImageSize.LARGE.size));
        imgCard.setBackgroundColor(ContextCompat.getColor(context, R.color.Silver));
        imgCard.setScaleType(ImageView.ScaleType.CENTER_CROP);

        RippleUtil.setRippleBackground(this, context);
        if (image != null)
            updateImage(image);
    }

    public void updateImage(Image image) {
        this.image = image;
        if (image.getBitmap() != null) {
            imgCard.setImageBitmap(image.getBitmap());
            BitmapDrawable bd = new BitmapDrawable(context.getResources(), image.getBitmap());
            RippleDrawable drawable = RippleUtil.getRippleDrawable(context, bd);
        }
    }

}
