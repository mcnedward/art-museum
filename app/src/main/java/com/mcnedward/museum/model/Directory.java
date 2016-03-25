package com.mcnedward.museum.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edward on 3/21/2016.
 */
public class Directory {

    private String name;
    private String path;
    private String parentPath;
    private List<Directory> childDirectories;
    private List<String> childItems;

    public Directory(String path) {
        this.path = path;
        name = new File(path).getName();
        childDirectories = new ArrayList<>();
        childItems = new ArrayList<>();
    }

    public Directory(String path, String parentPath) {
        this(path);
        this.parentPath = parentPath;
    }

    public void addChildDirectory(Directory directory) {
        childDirectories.add(directory);
    }

    public void addChildItem(String item) {
        childItems.add(item);
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

    public List<String> getChildItems() {
        return childItems;
    }

    public void setChildItems(List<String> childItems) {
        this.childItems = childItems;
    }
}
