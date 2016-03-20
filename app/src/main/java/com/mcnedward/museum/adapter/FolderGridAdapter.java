package com.mcnedward.museum.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.mcnedward.museum.model.Folder;
import com.mcnedward.museum.view.FolderView;

/**
 * Created by Edward on 3/17/2016.
 */
public class FolderGridAdapter extends BaseListAdapter<Folder> {
    private static final String TAG = "FolderGridAdapter";

    public FolderGridAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = new FolderView(context, getItem(position));
        FolderView folderView = (FolderView) convertView;
        folderView.updateFolder(getItem(position));
        return folderView;
    }

    @Override
    protected void setOnClickListener(Folder media, View view) {

    }

}
