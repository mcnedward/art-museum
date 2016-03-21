package com.mcnedward.museum.utils;

import android.app.Activity;
import android.content.Intent;

import com.mcnedward.museum.activity.FolderActivity;
import com.mcnedward.museum.model.Folder;
import com.mcnedward.museum.model.Image;

import java.util.ArrayList;

/**
 * Created by Edward on 3/20/2016.
 */
public class ActivityUtil {
    private static final String TAG = "ActivityUtil";

    public static void startFolderActivity(final Folder folder, final Activity activity) {
        Intent intent = new Intent(activity, FolderActivity.class);
        ArrayList<Image> images = new ArrayList<>(folder.getImages());
        intent.putExtra("images", images);
        intent.putExtra("folderName", folder.getName());
        activity.startActivity(intent);
    }

}
