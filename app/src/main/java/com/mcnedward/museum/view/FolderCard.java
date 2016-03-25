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
import android.widget.TextView;

import com.mcnedward.museum.R;
import com.mcnedward.museum.activity.fragment.GalleryFragment;
import com.mcnedward.museum.model.Directory;
import com.mcnedward.museum.utils.RippleUtil;

/**
 * Created by Edward on 3/20/2016.
 */
public class FolderCard extends LinearLayout {

    public static int HEIGHT;

    private Context context;
    private ImageView imgFolderThumbnail;
    private TextView txtFolderTitle;
    private TextView txtFolderItemCount;
    private TextView txtFolderDirectoryCount;
    private Directory folder;

    public FolderCard(Context context) {
        super(context);
        initialize(context);
    }

    public FolderCard(Context context, Directory folder) {
        super(context);
        initialize(context, folder);
    }

    public FolderCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        initialize(context, null);
    }

    private void initialize(Context context, Directory folder) {
        this.context = context;
        inflate(context, R.layout.view_folder_card, this);

        imgFolderThumbnail = (ImageView) findViewById(R.id.folder_thumbnail);
        imgFolderThumbnail.setLayoutParams(new LinearLayout.LayoutParams(GalleryFragment.SIZE, GalleryFragment.SIZE));
        imgFolderThumbnail.setBackgroundColor(ContextCompat.getColor(context, R.color.Silver));
        imgFolderThumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);

        txtFolderTitle = (TextView) findViewById(R.id.folder_title);
        txtFolderItemCount = (TextView) findViewById(R.id.folder_item_count);
        txtFolderDirectoryCount = (TextView) findViewById(R.id.folder_directory_count);

        if (folder != null) {
            updateFolder(folder);
        }
    }

    public void updateFolder(Directory folder) {
        this.folder = folder;
        folder.setFolderCard(this);
        updateText();
        setImage(folder.getThumbnail());
    }

    public void updateText() {
        txtFolderTitle.setText(folder.getName());
        txtFolderItemCount.setText(String.valueOf(folder.getImages().size()));
        txtFolderDirectoryCount.setText(String.valueOf(folder.getChildDirectories().size()));
    }

    public void setImage(Bitmap bitmap) {
        BitmapDrawable bd = new BitmapDrawable(context.getResources(), bitmap);
        RippleDrawable drawable = RippleUtil.getRippleDrawable(context, bd);
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
