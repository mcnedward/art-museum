package com.mcnedward.museum.utils;

import com.mcnedward.museum.model.Directory;
import com.mcnedward.museum.model.Image;

import java.io.File;
import java.util.Iterator;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Created by Edward on 3/20/2016.
 */
public class DirectoryUtil {

    public enum Base {
        STORAGE("/storage/"),
        SD_CARD("/storage/sdcard1/"),
        EMULATED("/storage/emulated/0/");

        public String path;

        Base(String path) {
            this.path = path;
        }
    }

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

    public static boolean handleMediaFile(String rootPath, final Directory topLevel, Image image) {
        String path = image.getPath();
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
        if (handleMediaFile(rootPath, nextLevel, image)) {
            if (new File(path).getParent().replace("\\", "/").equals(nextLevel.getPath())) {
                nextLevel.addImage(image);
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

    public static boolean pathIsBasePath(String path) {
        for (Base b : Base.values()) {
            if (new File(b.path).getPath().replace("\\", "/").equals(path))
                return true;
        }
        return false;
    }

}
