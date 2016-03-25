package com.mcnedward.museum.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.mcnedward.museum.R;
import com.mcnedward.museum.adapter.FolderGridAdapter;
import com.mcnedward.museum.adapter.ImageGridAdapter;
import com.mcnedward.museum.model.Directory;
import com.mcnedward.museum.model.Image;
import com.mcnedward.museum.async.BitmapLoadTask;
import com.mcnedward.museum.utils.DirectoryUtil;
import com.mcnedward.museum.view.FolderCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edward on 3/20/2016.
 */
public class FolderActivity extends AppCompatActivity {
    private static final String TAG = "FolderActivity";

    private Directory directory;
    private List<Image> images;
    private GridView gridFolders;
    private GridView gridImages;
    private FolderGridAdapter folderGridAdapter;
    private ImageGridAdapter imageGridAdapter;
    private List<BitmapLoadTask> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        directory = (Directory) getIntent().getSerializableExtra("directory");
        setTitle(directory.getName());
        images = (ArrayList<Image>) getIntent().getSerializableExtra("images");

        initializeGrids();

        loadFolders();
        loadImages();
    }

    private void initializeGrids() {
        gridFolders = (GridView) findViewById(R.id.grid_folders);
        RelativeLayout.LayoutParams folderLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, FolderCard.HEIGHT);
        gridFolders.setLayoutParams(folderLayoutParams);

        gridImages = (GridView) findViewById(R.id.grid_images);
    }

    private void loadFolders() {
        folderGridAdapter = new FolderGridAdapter(this);
        gridFolders.setAdapter(folderGridAdapter);

        DirectoryUtil.startThumbnailLoading(this, directory);
        folderGridAdapter.addAll(directory.getChildDirectories());
    }

    private void loadImages() {
        imageGridAdapter = new ImageGridAdapter(this, images);
        gridImages.setAdapter(imageGridAdapter);
        tasks = new ArrayList<>();
        for (Image image : images) {
            BitmapLoadTask task = new BitmapLoadTask(this, image, imageGridAdapter);
            tasks.add(task);
            task.execute();
        }
    }

    @Override
    public void onDestroy() {
        for (BitmapLoadTask task : tasks)
            task.cancel(true);
        super.onDestroy();
    }

}
