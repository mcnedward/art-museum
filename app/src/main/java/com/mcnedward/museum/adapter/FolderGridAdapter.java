package com.mcnedward.museum.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.mcnedward.museum.model.Directory;
import com.mcnedward.museum.utils.ActivityUtil;
import com.mcnedward.museum.view.FolderCard;
import com.mcnedward.museum.view.MediaCard;

import java.util.List;

/**
 * Created by Edward on 3/17/2016.
 */
public class FolderGridAdapter extends BaseListAdapter<Directory> {
    private static final String TAG = "FolderGridAdapter";

    public FolderGridAdapter(Context context) {
        super(context);
    }

    public FolderGridAdapter(Context context, List<Directory> childDirectories) {
        super(context, childDirectories);
    }

    @Override
    protected MediaCard createNewView(Directory directory) {
        return new FolderCard(context, directory);
    }

    @Override
    protected void setViewContent(Directory directory, MediaCard view) {
        FolderCard folderCard = (FolderCard) view;
        folderCard.updateItem(directory);
    }

    @Override
    protected String getBitmapPathFromItem(Directory item) {
        return item.getThumbnailImage().getPath();
    }

    @Override
    protected View.OnClickListener getOnClickListener(final Directory directory) {
        final Activity activity = (Activity) context;
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.startFolderActivity(directory, activity);
            }
        };
    }
}
