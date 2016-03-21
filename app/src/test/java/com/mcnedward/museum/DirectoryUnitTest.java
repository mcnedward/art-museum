package com.mcnedward.museum;

import com.mcnedward.museum.enums.DirectoryPath;
import com.mcnedward.museum.model.Directory;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class DirectoryUnitTest {

    @Test
    public void getDirectoryFromPath_returnsCorrect() throws Exception {
        String sdCardPath = "/storage/sdcard1/Music";
        String emulatedPath = "/storage/emulated/0/WhatsApp";

        assertEquals(DirectoryPath.Base.SD_CARD, DirectoryPath.getDirectoryPathFromPath(sdCardPath));
        assertEquals(DirectoryPath.Base.EMULATED, DirectoryPath.getDirectoryPathFromPath(emulatedPath));
    }

    @Test
    public void getDirectoryFromPath_returnsIncorrect() throws Exception {
        String sdCardPath = "/storage/Music";
        String emulatedPath = "/storage/emulated/1/WhatsApp";

        for (DirectoryPath.Base d : DirectoryPath.Base.values()) {
            assertNotEquals(d, DirectoryPath.getDirectoryPathFromPath(sdCardPath));
            assertNotEquals(d, DirectoryPath.getDirectoryPathFromPath(emulatedPath));
        }
    }

    @Test
    public void getFolderFromPath_returnsCorrect() throws Exception {
        String sdCardPath = "/storage/sdcard1/Music/File.png";
        String emulatedPath = "/storage/emulated/0/WhatsApp/File.png";

        String musicFolder = DirectoryPath.getFolderNameFromPath(sdCardPath);
        String whatsAppFolder = DirectoryPath.getFolderNameFromPath(emulatedPath);

        assertEquals("Music", musicFolder);
        assertEquals("WhatsApp", whatsAppFolder);
    }

    @Test
    public void getFolderHierarchy_findsAll() throws Exception {
        String music1 = "/storage/sdcard1/Top/Music/File1.png";
        String music2 = "/storage/sdcard1/Top/Music2/File2.png";
        String music2Album1 = "/storage/sdcard1/Top/Music2/Album1/File2.png";
        String music2Album2 = "/storage/sdcard1/Top/Music2/Album2/File2.png";
        String music2Album3 = "/storage/sdcard1/Top/Music2/Album3/File2.png";
        String music3 = "/storage/sdcard1/Top/Music3/File3.png";
        String music3Album1 = "/storage/sdcard1/Top/Music3/Album1/File3.png";
        String music3Album2 = "/storage/sdcard1/Top/Music3/Album2/File3.png";
        String music3Album3 = "/storage/sdcard1/Top/Music3/Album3/File3.png";

        Directory topLevel = new Directory("/storage/sdcard1/Top");
        DirectoryPath.handleMediaFile("/storage/sdcard1/Top", topLevel, music1);
        DirectoryPath.handleMediaFile("/storage/sdcard1", topLevel, music2);
        DirectoryPath.handleMediaFile("/storage/sdcard1", topLevel, music2Album1);
        DirectoryPath.handleMediaFile("/storage/sdcard1", topLevel, music2Album2);
        DirectoryPath.handleMediaFile("/storage/sdcard1", topLevel, music2Album3);
        DirectoryPath.handleMediaFile("/storage/sdcard1", topLevel, music3);
        DirectoryPath.handleMediaFile("/storage/sdcard1", topLevel, music3Album1);
        DirectoryPath.handleMediaFile("/storage/sdcard1", topLevel, music3Album2);
        DirectoryPath.handleMediaFile("/storage/sdcard1", topLevel, music3Album3);

        Assert.assertThat(topLevel.getChildDirectories().isEmpty(), is(false));
//        Assert.assertThat(topLevel.get(0), is(notNullValue()));
//        Assert.assertThat(topLevel.get(0).getChildDirectories().isEmpty(), is(false));
//        Assert.assertThat(topLevel.get(0).getChildDirectories().size(), is(3));
    }
}