package com.mcnedward.museum.enums;

import com.mcnedward.museum.model.Directory;
import com.mcnedward.museum.model.Folder;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Created by Edward on 3/20/2016.
 */
public class DirectoryPath {

    public enum Base {
        SD_CARD("/storage/sdcard1/"),
        EMULATED("/storage/emulated/0/");

        public String path;

        Base(String path) {
            this.path = path;
        }
    }

    public static Base getDirectoryPathFromPath(String path) {
        if (path != null && !"".equals(path))
            for (Base b : Base.values())
                if (path.matches(b.path + ".*"))
                    return b;
        return null;
    }

    public static String getFolderNameFromPath(String path) {
        String directory = getDirectoryPathFromPath(path).path;
        Pattern regex = Pattern.compile(String.format("%s(.*?)/.*", directory));
        return regex.matcher(path).replaceAll("$1");
    }

    private static String getParentPath(String path) {
        File file = new File(path);
        return file.getParent();
    }

    private static Directory getDirectoryForItem(Directory top, String path) {
        for (Directory d : top.getChildDirectories()) {
            if (d.getPath().equals(path))
                return d;
            if (d.hasChildDirectories())
                return getDirectoryForItem(d, path);
        }
        return null;
    }

    public static boolean handleMediaFile(final String rootPath, final Directory topLevel, String path) {
        if (path.equals(topLevel.getPath()))
            return true;
        Directory nextLevel = createDirectory(rootPath, path);
        if (nextLevel.getPath().equals(topLevel.getPath()))
            return true;
        boolean exists = false;
        for (Directory d : topLevel.getChildDirectories()) {
            if (d.getPath().equals(nextLevel.getPath())) {
                exists = true;
                nextLevel = d;
                break;
            }
        }
        if (!exists) {
            topLevel.addChildDirectory(nextLevel);
        }
        return handleMediaFile(rootPath, nextLevel, path);

        // Check if next level of directory exists
//        Directory nextLevel = createDirectory(topLevelDirectory.getPath(), path);
//
//        Directory directoryForItem = getDirectoryForItem(topLevelDirectory, getParentPath(path));
//        if (directoryForItem == null) {
//            directoryForItem = createDirectory(topLevelDirectory.getPath(), path);
//            topLevelDirectory.addChildDirectory(directoryForItem);
//        }
    }

    public static Directory createDirectory(String parentPath, String itemPath) {
        Stack<String> pathStack = new Stack<>();
        buildPathStack(parentPath, itemPath, pathStack, false);
        Directory directory = new Directory(pathStack.pop());
        if (!pathStack.isEmpty()) {
            Directory child, parent = directory;
            Iterator<String> iterator = pathStack.iterator();
            while (iterator.hasNext()) {
                String path = pathStack.pop();
                child = new Directory(path);
                parent.addChildDirectory(child);
                parent = child;
            }
        }
        return directory;
    }

    public static boolean buildPathStack(String rootPath, String itemPath, Stack<String> pathStack, boolean addToStack) {
        File file = new File(itemPath);
        if (addToStack)
            pathStack.push(itemPath);
        String parentPath = file.getParent().replace("\\", "/");
        if (rootPath.equals(parentPath) || pathIsBasePath(parentPath))
            return true;
        return buildPathStack(rootPath, parentPath, pathStack, true);
    }

    private static boolean pathIsBasePath(String path) {
        for (Base b : Base.values()) {
            if (new File(b.path).getPath().equals(path))
                return true;
        }
        return false;
    }

    private static Directory createChildDirectory(Directory parentDirectory, String path) {
        Directory child = new Directory(path);
        parentDirectory.addChildDirectory(child);
        return child;
    }

    private static Folder handleDirectories(List<Directory> existingFolders, Directory folder) {
//        String parentPath = getParent(folder.getParentPath());
//        if (parentPath != null) {
//            boolean exists = false;
//            for (Folder existingFolder : existingFolders) {
//                if (parentPath.equals(existingFolder.getPath()))
//                    exists = true;
//            }
//            if (!exists) {
//                Folder parentFolder = new Folder()
//                if (parentFolder.getPath().equals())
//            }
//        }
        return null;
    }

    private static boolean isBaseDirectory(String path) {
        for (Base b : Base.values())
            if (b.path.equals(path))
                return true;
        return false;
    }

}
