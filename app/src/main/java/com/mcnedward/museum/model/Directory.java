package com.mcnedward.museum.model;

import android.graphics.Bitmap;

import com.mcnedward.museum.listener.BitmapListener;
import com.mcnedward.museum.view.FolderCard;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edward on 3/21/2016.
 */
public class Directory implements IMedia, BitmapListener, Serializable {

    private String name;
    private String path;
    private String parentPath;
    private List<Directory> childDirectories;
    private List<Image> images;
    private boolean viewLoaded;
    private transient Bitmap thumbnail;
    private transient FolderCard folderCard;

    public Directory(String path) {
        this.path = path;
        name = new File(path).getName();
        childDirectories = new ArrayList<>();
        images = new ArrayList<>();
    }

    public Directory(String path, String parentPath) {
        this(path);
        this.parentPath = parentPath;
    }

    public void addChildDirectory(Directory directory) {
        childDirectories.add(directory);
    }

    public void addImage(Image image) {
        images.add(image);
    }

    public boolean hasChildDirectories() {
        return !childDirectories.isEmpty();
    }

    public Directory getDirectoryFromPath(String directoryPath) {
        for (Directory d : childDirectories) {
            if (d.getPath().equals(directoryPath))
                return d;
        }
        return null;
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

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public List<Directory> getChildDirectories() {
        return childDirectories;
    }

    public void setChildDirectories(List<Directory> childDirectories) {
        this.childDirectories = childDirectories;
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
