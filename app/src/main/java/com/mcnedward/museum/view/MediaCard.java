package com.mcnedward.museum.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mcnedward.museum.R;
import com.mcnedward.museum.model.Media;
import com.mcnedward.museum.utils.RippleUtil;

/**
 * Created by Edward on 3/20/2016.
 */
public abstract class MediaCard<T extends Media> extends LinearLayout {

    protected Context context;
    protected T item;
    protected ImageView imageView;

    public MediaCard(Context context, T item) {
        super(context);
        initialize(context, item);
    }

    public MediaCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, null);
    }

    private void initialize(Context context, T item) {
        this.context = context;
        this.item = item;
        inflate(context, getResourceId(), this);
        imageView = (ImageView) findViewById(R.id.folder_thumbnail);

        if (item != null) {
            initializeCard(context, item);
            updateItem(item);
            setImage(item.getBitmap());
        }
    }

    protected abstract void initializeCard(Context context, T item);

    protected abstract int getResourceId();

    protected abstract void update();

    public void updateItem(T item) {
        this.item = item;
        setImage(item.getBitmap());
        item.setMediaCard(this);
        update();
    }

    public void setImage(Bitmap bitmap) {
        if (bitmap == null) return;
        BitmapDrawable bd = new BitmapDrawable(context.getResources(), bitmap);
        RippleDrawable drawable = RippleUtil.getRippleDrawable(context, bd);
        imageView.setImageDrawable(drawable);
    }

    public void setImage(Drawable drawable) {
        imageView.setImageDrawable(drawable);
    }

    public void resetImage() {
        setImage(R.color.LightSteelBlue);
    }

    public void setImage(int color) {
        imageView.setBackgroundColor(ContextCompat.getColor(context, color));
    }

    public ImageView getImageView() {
        return imageView;
    }
}
