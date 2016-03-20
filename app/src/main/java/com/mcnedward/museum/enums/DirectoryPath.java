package com.mcnedward.museum.enums;

import java.util.regex.Pattern;

/**
 * Created by Edward on 3/20/2016.
 */
public enum DirectoryPath {

    SD_CARD("/storage/sdcard1/"),
    EMULATED("/storage/emulated/0/");

    public String path;

    DirectoryPath(String path) {
        this.path = path;
    }

    public static DirectoryPath getDirectoryPathFromPath(String path) {
        if (path != null && !"".equals(path))
            for (DirectoryPath d : values())
                if (path.matches(d.path + ".*"))
                    return d;
        return null;
    }

    public static String getFolderNameFromPath(String path) {
        String directory = getDirectoryPathFromPath(path).path;
        Pattern regex = Pattern.compile(String.format("%s(.*?)/.*", directory));
        return regex.matcher(path).replaceAll("$1");
    }

}
