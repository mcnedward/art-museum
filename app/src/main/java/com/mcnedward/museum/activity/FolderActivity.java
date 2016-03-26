package com.mcnedward.museum.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.mcnedward.museum.R;
import com.mcnedward.museum.adapter.FolderGridAdapter;
import com.mcnedward.museum.adapter.ImageGridAdapter;
import com.mcnedward.museum.async.BitmapLoadTask;
import com.mcnedward.museum.model.Directory;
import com.mcnedward.museum.model.Image;

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
        tasks = new ArrayList<>();

        initializeGrids();
        loadFolders();
        loadImages();
    }

    private void initializeGrids() {
        gridFolders = (GridView) findViewById(R.id.grid_folders);
        gridImages = (GridView) findViewById(R.id.grid_images);
        if (images.size() > 0 && directory.getChildDirectories().size() > 0) {
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int height = (int) (dm.heightPixels / 2.8);
            RelativeLayout.LayoutParams folderLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, height);
            gridFolders.setLayoutParams(folderLayoutParams);

            RelativeLayout.LayoutParams imageLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            imageLayoutParams.addRule(RelativeLayout.BELOW, R.id.grid_folders);
            gridImages.setLayoutParams(imageLayoutParams);
        }
        if (images.size() == 0) {
            gridImages.setVisibility(View.GONE);
        }
    }

    private void loadFolders() {
        folderGridAdapter = new FolderGridAdapter(this);
        gridFolders.setAdapter(folderGridAdapter);
    }

    private void loadImages() {
        imageGridAdapter = new ImageGridAdapter(this, images);
        gridImages.setAdapter(imageGridAdapter);
    }


    @Override
    public void onDestroy() {
        for (BitmapLoadTask task : tasks)
            task.cancel(true);
        super.onDestroy();
    }

}
