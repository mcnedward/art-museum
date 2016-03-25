package com.mcnedward.museum.async;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.mcnedward.museum.utils.DirectoryUtil;
import com.mcnedward.museum.model.Directory;
import com.mcnedward.museum.model.Folder;
import com.mcnedward.museum.model.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edward on 3/19/2016.
 */
public class FolderLoader extends AsyncTaskLoader<List<Directory>> {
    private static final String TAG = "BitmapLoadTask";

    private Context context;

    public FolderLoader(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public List<Directory> loadInBackground() {
        List<Folder> folders = new ArrayList<>();
        Directory directory = new Directory("/storage");

        String[] mediaQueries = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.TITLE,
                MediaStore.Images.Media.DATA,
        };
        Uri externalContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        try {
            Cursor cursor = context.getContentResolver().query(externalContentUri, mediaQueries, null, null, null);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
                String bucketDisplayName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                String dateAdded = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED));
                String displayName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE));
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

                Image image = new Image(id, bucketDisplayName, dateAdded, displayName, title, path);

                DirectoryUtil.handleMediaFile(directory.getPath(), directory, image);
                Log.d(TAG, image.getPath());
            }
            cursor.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }

        // First load folder thumbnails
        for (Folder folder : folders) {
            List<Image> images = folder.getImages();
            if (images.isEmpty()) continue;
            Image image = images.get(0);
            new BitmapLoadTask(context, image, folder).execute();
        }

        List<Directory> directories = new ArrayList<>();
        getChildDirectories(directory, directories);

        return directories;
    }

    private void getChildDirectories(Directory directory, List<Directory> directories) {
        for (Directory d : directory.getChildDirectories()) {
            if (DirectoryUtil.pathIsBasePath(d.getPath())) {
                directories.addAll(d.getChildDirectories());
            } else
                getChildDirectories(d, directories);
        }
    }

}
