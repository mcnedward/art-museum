package com.mcnedward.museum.enums;

import com.mcnedward.museum.model.Directory;
import com.mcnedward.museum.model.Folder;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Created by Edward on 3/20/2016.
 */
public class DirectoryUtil {

    public enum Base {
        SD_CARD("/storage/sdcard1/"),
        EMULATED("/storage/emulated/0/");

        public String path;
        public Directory directory;

        Base(String path) {
            this.path = path;
            directory = new Directory(path);
        }
    }

    public static Directory getDirectoryFromPath(String path) {
        if (path != null && !"".equals(path))
            for (Base b : Base.values())
                if (path.matches(b.path + ".*"))
                    return b.directory;
        return null;
    }

//    public static Directory handle(String path) {
//        File file = new File(path);
//        String topLevelDirectory = findTopLevelDirectory(file);
//        Directory directory = new Directory(topLevelDirectory);
//        if (topLevelDirectory.equals(directory.getPath())) {
//            directory.addChildItem(path);
//        } else {
//            Directory itemDirectory = directory.getDirectoryFromPath(topLevelDirectory);
//            if (itemDirectory != null) {
//                itemDirectory.addChildItem(path);
//            } else {
//                itemDirectory = createDirectory(directory.getPath(), path);
//                itemDirectory.addChildItem(path);
//                directory.addChildDirectory(itemDirectory);
//            }
//        }
//        return directory;
//    }

//    public static String findTopLevelDirectory(File file) {
//        Holder holder = new Holder();
//        holder.file = file;
//        recurse(holder);
//        return holder.topLevelDirectoryName;
//    }
//
//    public static boolean recurse(Holder holder) {
//        String parentPath = holder.file.getParent().replace("\\", "/");
//        if (parentPath.equals("/")) {
//            holder.topLevelDirectoryName = holder.file.getName();
//            return true;
//        }
//        holder.file = holder.file.getParentFile();
//        return recurse(holder);
//    }

    public static Base getDirectoryBaseFromPath(String path) {
        if (path != null && !"".equals(path))
            for (Base b : Base.values())
                if (path.matches(b.path + ".*"))
                    return b;
        return null;
    }

    public static String getFolderNameFromPath(String path) {
        String directory = getDirectoryBaseFromPath(path).path;
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

    public static boolean handleMediaFile(String rootPath, final Directory topLevel, String path) {
        if (path.equals(topLevel.getPath()))
            return true;
        Directory nextLevel = createDirectory(rootPath, path);
        if (nextLevel.getPath().equals(topLevel.getPath())) {
            return true;
        }
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
        rootPath = nextLevel.getPath();
        if (handleMediaFile(rootPath, nextLevel, path)) {
            if (new File(path).getParent().replace("\\", "/").equals(nextLevel.getPath())) {
                nextLevel.addChildItem(path);
            }
            return true;
        } else
            return false;
    }

    public static Directory createDirectory(String parentPath, String itemPath) {
        Stack<String> pathStack = new Stack<>();
        buildPathStack(parentPath, itemPath, pathStack, false);
        Directory directory = new Directory(!pathStack.isEmpty() ? pathStack.pop() : parentPath, parentPath);
        if (!pathStack.isEmpty()) {
            Directory child, parent = directory;
            Iterator<String> iterator = pathStack.iterator();
            while (iterator.hasNext()) {
                String path = pathStack.pop();
                child = new Directory(path, parent.getPath());
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
        if (rootPath.equals(parentPath) || "/".equals(parentPath))
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

    static class Holder {
        File file;
        String topLevelDirectoryName = "";
    }

}
