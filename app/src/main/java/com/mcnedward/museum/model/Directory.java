package com.mcnedward.museum.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edward on 3/21/2016.
 */
public class Directory extends Media {

    private String name;
    private String path;
    private String parentPath;
    private List<Directory> childDirectories;
    private List<Image> images;

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

    public List<Directory> getChildDirectories() {
        return childDirectories;
    }

    public List<Image> getImages() {
        return images;
    }

}
