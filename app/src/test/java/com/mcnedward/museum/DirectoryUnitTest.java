package com.mcnedward.museum;

import com.mcnedward.museum.enums.DirectoryPath;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class DirectoryUnitTest {

    @Test
    public void getDirectoryFromPath_returnsCorrect() throws Exception {
        String sdCardPath = "/storage/sdcard1/Music";
        String emulatedPath = "/storage/emulated/0/WhatsApp";

        assertEquals(DirectoryPath.SD_CARD, DirectoryPath.getDirectoryPathFromPath(sdCardPath));
        assertEquals(DirectoryPath.EMULATED, DirectoryPath.getDirectoryPathFromPath(emulatedPath));
    }

    @Test
    public void getDirectoryFromPath_returnsIncorrect() throws Exception {
        String sdCardPath = "/storage/Music";
        String emulatedPath = "/storage/emulated/1/WhatsApp";

        for (DirectoryPath d : DirectoryPath.values()) {
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
}