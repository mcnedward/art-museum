package com.mcnedward.museum.model;

/**
 * Created by Edward on 3/25/2016.
 */
public class Media {

    public enum MediaType {
        DIRECTORY, IMAGE
    }

    private MediaType mediaType;
    private Directory directory;
    private Image image;

    public Media(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public Media(Image image) {
        this(MediaType.IMAGE);
        this.image = image;
    }

    public Media(Directory directory) {
        this(MediaType.DIRECTORY);
        this.directory = directory;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public Directory getDirectory() {
        return directory;
    }

    public Image getImage() {
        return image;
    }
}
