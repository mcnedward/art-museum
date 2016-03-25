package com.mcnedward.museum.model;

import android.graphics.Bitmap;

import com.mcnedward.museum.enums.DirectoryUtil;
import com.mcnedward.museum.listener.BitmapListener;
import com.mcnedward.museum.view.FolderCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edward on 3/20/2016.
 */
public class Folder implements BitmapListener, Serializable {

    private String name;
    private String path;
    private List<Folder> childFolders;  // Change this to just children path for Data oriented
    private List<Image> images;
    private boolean viewLoaded;
    private Bitmap thumbnail;
    private FolderCard folderCard;

    public Folder(String path) {
//        directoryUtil = DirectoryUtil.getDirectoryBaseFromPath(path);
        name = DirectoryUtil.getFolderNameFromPath(path);
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

    public void setFolderCard(FolderCard folderCard) {
        this.folderCard = folderCard;
    }

    @Override
    public void notifyBitmapLoaded(Bitmap bitmap) {
        thumbnail = bitmap;
        if (folderCard != null)
            folderCard.setImage(bitmap);
    }
}
