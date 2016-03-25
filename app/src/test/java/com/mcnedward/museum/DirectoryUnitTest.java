package com.mcnedward.museum;

import com.mcnedward.museum.utils.DirectoryUtil;
import com.mcnedward.museum.model.Directory;
import com.mcnedward.museum.model.Image;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.junit.Assert;

import java.util.List;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class DirectoryUnitTest {

    @Test
    public void getDirectoryFromPath_returnsCorrect() throws Exception {
        String sdCardPath = "/storage/sdcard1/Music";
        String emulatedPath = "/storage/emulated/0/WhatsApp";

        assertEquals(DirectoryUtil.Base.SD_CARD, DirectoryUtil.getDirectoryBaseFromPath(sdCardPath));
        assertEquals(DirectoryUtil.Base.EMULATED, DirectoryUtil.getDirectoryBaseFromPath(emulatedPath));
    }

    @Test
    public void getDirectoryFromPath_returnsIncorrect() throws Exception {
        String sdCardPath = "/storage/Music";
        String emulatedPath = "/storage/emulated/1/WhatsApp";

        for (DirectoryUtil.Base d : DirectoryUtil.Base.values()) {
            assertNotEquals(d, DirectoryUtil.getDirectoryBaseFromPath(sdCardPath));
            assertNotEquals(d, DirectoryUtil.getDirectoryBaseFromPath(emulatedPath));
        }
    }

    @Test
    public void getFolderFromPath_returnsCorrect() throws Exception {
        String sdCardPath = "/storage/sdcard1/Music/File.png";
        String emulatedPath = "/storage/emulated/0/WhatsApp/File.png";

        String musicFolder = DirectoryUtil.getFolderNameFromPath(sdCardPath);
        String whatsAppFolder = DirectoryUtil.getFolderNameFromPath(emulatedPath);

        assertEquals("Music", musicFolder);
        assertEquals("WhatsApp", whatsAppFolder);
    }

    @Test
    public void addItemToDirectory_isSuccessful() throws Exception {
        Image item = new Image("/storage/sdcard1/Top/Music/File.png");
        Image music2 = new Image("/storage/sdcard1/Top/Music2/File2.png");
        Image music2Album1 = new Image("/storage/sdcard1/Top/Music2/Album1/File2.png");
        Image music2Album2 = new Image("/storage/sdcard1/Top/Music2/Album2/File2.png");
        Image music2Album3 = new Image("/storage/sdcard1/Top/Music2/Album3/File2.png");

        Directory directory = new Directory("/");

        DirectoryUtil.handleMediaFile("/", directory, item);
        DirectoryUtil.handleMediaFile("/", directory, music2);
        DirectoryUtil.handleMediaFile("/", directory, music2Album1);
        DirectoryUtil.handleMediaFile("/", directory, music2Album2);
        DirectoryUtil.handleMediaFile("/", directory, music2Album3);

        assertNotNull(directory);
        List<Directory> children = directory.getChildDirectories();
        assertNotNull(children);
        Assert.assertThat(children.isEmpty(), is(false));

        List<Directory> children2 = children.get(0).getChildDirectories();
        assertNotNull(children2);
        Assert.assertThat(children2.isEmpty(), is(false));

        List<Directory> children3 = children2.get(0).getChildDirectories();
        assertNotNull(children3);
        Assert.assertThat(children3.isEmpty(), is(false));

        List<Directory> children4 = children3.get(0).getChildDirectories();
        assertNotNull(children4);
        Assert.assertThat(children4.isEmpty(), is(false));
        Assert.assertThat(children4.size(), is(2));

        Directory musicDir = children4.get(0);
        assertNotNull(musicDir);
        Assert.assertThat(musicDir.getImages().isEmpty(), is(false));

        Directory music2Dir = children4.get(1);
        assertNotNull(music2Dir);
        Assert.assertThat(music2Dir.getChildDirectories().isEmpty(), is(false));
        Assert.assertThat(music2Dir.getChildDirectories().size(), is(3));
        Assert.assertThat(music2Dir.getImages().isEmpty(), is(false));
    }

    @Test
    public void getFolderHierarchy_findsAll() throws Exception {
        Image music1 = new Image("/storage/sdcard1/Top/Music/File1.png");
        Image music2 = new Image("/storage/sdcard1/Top/Music2/File2.png");
        Image music2Album1 = new Image("/storage/sdcard1/Top/Music2/Album1/File2.png");
        Image music2Album2 = new Image("/storage/sdcard1/Top/Music2/Album2/File2.png");
        Image music2Album3 = new Image("/storage/sdcard1/Top/Music2/Album3/File2.png");
        Image music3 = new Image("/storage/sdcard1/Top/Music3/File3.png");
        Image music3Album1 = new Image("/storage/sdcard1/Top/Music3/Album1/File3.png");
        Image music3Album2 = new Image("/storage/sdcard1/Top/Music3/Album2/File3.png");
        Image music3Album3 = new Image("/storage/sdcard1/Top/Music3/Album3/File3.png");

        Directory topLevel = new Directory("/storage/sdcard1");
        DirectoryUtil.handleMediaFile("/storage/sdcard1/Top", topLevel, music1);
        DirectoryUtil.handleMediaFile("/storage/sdcard1", topLevel, music2);
        DirectoryUtil.handleMediaFile("/storage/sdcard1", topLevel, music2Album1);
        DirectoryUtil.handleMediaFile("/storage/sdcard1", topLevel, music2Album2);
        DirectoryUtil.handleMediaFile("/storage/sdcard1", topLevel, music2Album3);
        DirectoryUtil.handleMediaFile("/storage/sdcard1", topLevel, music3);
        DirectoryUtil.handleMediaFile("/storage/sdcard1", topLevel, music3Album1);
        DirectoryUtil.handleMediaFile("/storage/sdcard1", topLevel, music3Album2);
        DirectoryUtil.handleMediaFile("/storage/sdcard1", topLevel, music3Album3);

        Assert.assertThat(topLevel.getChildDirectories().isEmpty(), is(false));
//        Assert.assertThat(topLevel.get(0), is(notNullValue()));
//        Assert.assertThat(topLevel.get(0).getChildDirectories().isEmpty(), is(false));
//        Assert.assertThat(topLevel.get(0).getChildDirectories().size(), is(3));
    }
}