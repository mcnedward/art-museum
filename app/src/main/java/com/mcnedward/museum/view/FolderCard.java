package com.mcnedward.museum.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mcnedward.museum.R;
import com.mcnedward.museum.activity.fragment.GalleryFragment;
import com.mcnedward.museum.model.Directory;

/**
 * Created by Edward on 3/20/2016.
 */
public class FolderCard extends MediaCard<Directory> {

    private TextView txtFolderTitle;
    private TextView txtFolderItemCount;
    private TextView txtFolderDirectoryCount;
    private Directory folder;

    public FolderCard(Context context, Directory folder) {
        super(context, folder);
    }

    @Override
    protected int getResourceId() {
        return R.layout.view_folder_card;
    }

    @Override
    protected void initializeCard(Context context, Directory folder) {
        this.folder = folder;

        imageView = (ImageView) findViewById(R.id.folder_thumbnail);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(GalleryFragment.SIZE, GalleryFragment.SIZE));
        imageView.setBackgroundColor(ContextCompat.getColor(context, R.color.Silver));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        txtFolderTitle = (TextView) findViewById(R.id.folder_title);
        txtFolderItemCount = (TextView) findViewById(R.id.folder_item_count);
        txtFolderDirectoryCount = (TextView) findViewById(R.id.folder_directory_count);
    }

    @Override
    public void update() {
        txtFolderTitle.setText(folder.getName());
        txtFolderTitle.setSelected(true);
        txtFolderItemCount.setText(String.valueOf(folder.getImages().size()));
        txtFolderDirectoryCount.setText(String.valueOf(folder.getChildDirectories().size()));
    }

}
