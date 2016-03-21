package com.mcnedward.museum.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.mcnedward.museum.model.Folder;
import com.mcnedward.museum.utils.ActivityUtil;
import com.mcnedward.museum.view.FolderCard;

/**
 * Created by Edward on 3/17/2016.
 */
public class FolderGridAdapter extends BaseListAdapter<Folder> {
    private static final String TAG = "FolderGridAdapter";

    public FolderGridAdapter(Context context) {
        super(context);
    }

    @Override
    protected View getCustomView(Folder folder) {
        return new FolderCard(context, folder);
    }

    @Override
    protected void setViewContent(Folder folder, View view) {
        FolderCard folderCard = (FolderCard) view;
        folderCard.updateFolder(folder);
    }

    @Override
    protected View.OnClickListener getOnClickListener(final Folder folder) {
        final Activity activity = (Activity) context;
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.startFolderActivity(folder, activity);
            }
        };
    }

}
