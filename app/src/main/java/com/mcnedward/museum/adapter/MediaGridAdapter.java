package com.mcnedward.museum.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.mcnedward.museum.model.Directory;
import com.mcnedward.museum.model.Image;
import com.mcnedward.museum.model.Media;
import com.mcnedward.museum.utils.ActivityUtil;
import com.mcnedward.museum.view.FolderCard;
import com.mcnedward.museum.view.ImageCard;

import java.util.List;

/**
 * Created by Edward on 3/17/2016.
 */
public class MediaGridAdapter extends BaseListAdapter<Media> {
    private static final String TAG = "MediaGridAdapter";

    public MediaGridAdapter(Context context) {
        super(context);
    }

    public MediaGridAdapter(Context context, List<Media> objects) {
        super(context, objects);
    }

    @Override
    protected View getCustomView(Media media) {
        if (media.getMediaType() == Media.MediaType.DIRECTORY) {
            Directory directory = media.getDirectory();
            return new FolderCard(context, directory);
        }
        if (media.getMediaType() == Media.MediaType.IMAGE){
            Image image = media.getImage();
            return new ImageCard(context, image);
        }
        return null;
    }

    @Override
    protected void setViewContent(Media media, View view) {
        if (media.getMediaType() == Media.MediaType.DIRECTORY) {
            Directory directory = media.getDirectory();
            FolderCard folderCard;
            if (view instanceof ImageCard)
                folderCard = new FolderCard(context, directory);
            else
                folderCard = (FolderCard) view;
            folderCard.updateFolder(directory);
        }
        if (media.getMediaType() == Media.MediaType.IMAGE){
            Image image = media.getImage();
            ImageCard imageCard;
            if (view instanceof ImageCard)
                imageCard = new ImageCard(context, image);
            else
                imageCard = (ImageCard) view;
            imageCard.updateImage(image);
        }
    }

    @Override
    protected View.OnClickListener getOnClickListener(final Media media) {
        if (media.getMediaType() == Media.MediaType.DIRECTORY) {
            final Directory directory = media.getDirectory();
            final Activity activity = (Activity) context;
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityUtil.startFolderActivity(directory, activity);
                }
            };
        }
        if (media.getMediaType() == Media.MediaType.IMAGE) {
            Image image = media.getImage();
            return null;
        }
        return null;
    }

}
