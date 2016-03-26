package com.mcnedward.museum.async;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.mcnedward.museum.model.Directory;
import com.mcnedward.museum.utils.DirectoryUtil;

import java.util.List;

/**
 * Created by Edward on 3/26/2016.
 */
public class DirectoryLoader extends AsyncTaskLoader<List<Directory>> {

    private Context context;
    private List<Directory> directories;

    public DirectoryLoader(Context context, List<Directory> directories) {
        super(context);
        this.context = context;
        this.directories = directories;
    }

    @Override
    public List<Directory> loadInBackground() {
        DirectoryUtil.startThumbnailLoading(context, directories);
        return directories;
    }
}
