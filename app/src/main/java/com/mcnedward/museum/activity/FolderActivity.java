package com.mcnedward.museum.activity;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.mcnedward.museum.R;
import com.mcnedward.museum.adapter.FolderGridAdapter;
import com.mcnedward.museum.adapter.ImageGridAdapter;
import com.mcnedward.museum.async.BitmapLoadTask;
import com.mcnedward.museum.async.DirectoryLoader;
import com.mcnedward.museum.async.FolderLoader;
import com.mcnedward.museum.listener.BitmapListener;
import com.mcnedward.museum.model.Directory;
import com.mcnedward.museum.model.Image;
import com.mcnedward.museum.utils.DirectoryUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Edward on 3/20/2016.
 */
public class FolderActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Directory>> {
    private static final String TAG = "FolderActivity";
    private final int LOADER_ID = new Random().nextInt(1000);

    private DirectoryLoader loader;
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

        initializeLoader();
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

//        folderGridAdapter.addAll(directory.getChildDirectories());
//        for (Directory d : directory.getChildDirectories()) {
//            startThumbnailLoading(d, folderGridAdapter);
//        }
//        DirectoryUtil.startThumbnailLoading(this, directory.getChildDirectories());
    }

    private void startThumbnailLoading(Directory directory, BitmapListener listener) {
        if (directory.getImages().size() > 0) {
            Image image = directory.getImages().get(0);
            BitmapLoadTask task = new BitmapLoadTask(this, image, listener);
            tasks.add(task);
            task.execute();
        } else
            startThumbnailLoading(directory, listener);
    }

    private void loadImages() {
        imageGridAdapter = new ImageGridAdapter(this, images);
        gridImages.setAdapter(imageGridAdapter);
        for (Image image : images) {
            BitmapLoadTask task = new BitmapLoadTask(this, image, imageGridAdapter);
            tasks.add(task);
            task.execute();
        }
    }

    private void initializeLoader() {
        Log.d(TAG, "### Calling initLoader! ###");
        if (getSupportLoaderManager().getLoader(LOADER_ID) == null)
            Log.d(TAG, "### Initializing a new Loader... ###");
        else
            Log.d(TAG, "### Reconnecting with existing Loader (id " + LOADER_ID + ")... ###");
        getSupportLoaderManager().initLoader(LOADER_ID, null, this).forceLoad();
    }

    @Override
    public Loader<List<Directory>> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "### Creating loader ###");
        loader = new DirectoryLoader(this, directory.getChildDirectories());
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<Directory>> loader, List<Directory> data) {
        folderGridAdapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Directory>> loader) {
        folderGridAdapter.reset();
    }

    @Override
    public void onDestroy() {
        for (BitmapLoadTask task : tasks)
            task.cancel(true);
        super.onDestroy();
    }

}
