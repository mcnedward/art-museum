package com.mcnedward.museum.model;

import android.content.Context;
import android.graphics.Bitmap;

import com.mcnedward.museum.enums.DirectoryPath;
import com.mcnedward.museum.listener.BitmapListener;
import com.mcnedward.museum.view.FolderView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edward on 3/20/2016.
 */
public class Folder implements BitmapListener {

    private Context context;
    private String name;
    private String path;
    private List<Image> images;
    private DirectoryPath directoryPath;
    private boolean viewLoaded;
    private Bitmap thumbnail;
    private FolderView folderView;

    public Folder(Context context, String path) {
        this.context = context;
        directoryPath = DirectoryPath.getDirectoryPathFromPath(path);
        name = DirectoryPath.getFolderNameFromPath(path);
        this.path = directoryPath + name;
        images = new ArrayList<>();
        viewLoaded = false;
    }

    public void addImage(Image image) {
        images.add(image);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public DirectoryPath getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(DirectoryPath directoryPath) {
        this.directoryPath = directoryPath;
    }

    public boolean isViewLoaded() {
        return viewLoaded;
    }

    public void setViewLoaded(boolean viewLoaded) {
        this.viewLoaded = viewLoaded;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setFolderView(FolderView folderView) {
        this.folderView = folderView;
    }

    @Override
    public void notifyBitmapLoaded(Bitmap bitmap) {
        thumbnail = bitmap;
        if (folderView != null)
            folderView.setImage(bitmap);
    }
}
