package com.mcnedward.museum.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mcnedward.museum.Extension;
import com.mcnedward.museum.ImageSize;
import com.mcnedward.museum.R;
import com.mcnedward.museum.enums.DirectoryPath;
import com.mcnedward.museum.listener.BitmapListener;
import com.mcnedward.museum.model.Folder;
import com.mcnedward.museum.model.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edward on 3/20/2016.
 */
public class FolderView extends LinearLayout {

    private Context context;
    private ImageView imgFolderThumbnail;
    private TextView txtFolderTitle;
    private Folder folder;

    public FolderView(Context context) {
        super(context);
        initialize(context);
    }

    public FolderView(Context context, Folder folder) {
        super(context);
        initialize(context, folder);
    }

    public FolderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        initialize(context, null);
    }

    private void initialize(Context context, Folder folder) {
        this.context = context;
        inflate(context, R.layout.view_folder, this);
        Extension.setRippleBackground(this, context);

        imgFolderThumbnail = (ImageView) findViewById(R.id.folder_thumbnail);
        imgFolderThumbnail.setLayoutParams(new LinearLayout.LayoutParams(ImageSize.LARGE.size, ImageSize.LARGE.size));
        imgFolderThumbnail.setBackgroundColor(ContextCompat.getColor(context, R.color.Silver));
        imgFolderThumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);

        txtFolderTitle = (TextView) findViewById(R.id.folder_title);

        if (folder != null) {
            updateFolder(folder);
        }
    }

    private void setOnTouch() {
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }

    public void updateFolder(Folder folder) {
        this.folder = folder;
        folder.setFolderView(this);
        setTitle(folder.getName());
        setImage(folder.getThumbnail());
    }

    public void setTitle(String title) {
        txtFolderTitle.setText(title);
    }

    public void setImage(Bitmap bitmap) {
        BitmapDrawable bd = new BitmapDrawable(context.getResources(), bitmap);
        RippleDrawable drawable = Extension.getRippleDrawable(context, bd);
        imgFolderThumbnail.setImageDrawable(drawable);
    }

    public void setImage(Drawable drawable) {
        imgFolderThumbnail.setImageDrawable(drawable);
    }

    public void setImage(int color) {
        imgFolderThumbnail.setBackgroundColor(ContextCompat.getColor(context, color));
    }

    public ImageView getImageThumbnail() {
        return imgFolderThumbnail;
    }

    public void setImageThumbnail(ImageView imageView) {
        imgFolderThumbnail = imageView;
    }

}
