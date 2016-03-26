package com.mcnedward.museum.model;

import android.graphics.Bitmap;

import com.mcnedward.museum.listener.BitmapListener;
import com.mcnedward.museum.view.ImageCard;

import java.io.Serializable;

/**
 * Created by Edward on 3/17/2016.
 */
public class Image extends Media {

    private int id;
    private String bucketDisplayName;
    private String dateAdded;
    private String displayName;
    private String title;
    private String path;

    public Image(String path) {
        this.path = path;
    }

    public Image(int id, String bucketDisplayName, String dateAdded,
                 String displayName, String title, String path) {
        this.id = id;
        this.bucketDisplayName = bucketDisplayName;
        this.dateAdded = dateAdded;
        this.displayName = displayName;
        this.title = title;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBucketDisplayName() {
        return bucketDisplayName;
    }

    public void setBucketDisplayName(String bucketDisplayName) {
        this.bucketDisplayName = bucketDisplayName;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return String.format("%s [%s} - Date: %s", displayName, title, dateAdded);
    }
}
