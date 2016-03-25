package com.mcnedward.museum.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.mcnedward.museum.R;
import com.mcnedward.museum.adapter.FolderGridAdapter;
import com.mcnedward.museum.adapter.ImageGridAdapter;
import com.mcnedward.museum.adapter.MediaGridAdapter;
import com.mcnedward.museum.model.Directory;
import com.mcnedward.museum.model.IMedia;
import com.mcnedward.museum.model.Image;
import com.mcnedward.museum.async.BitmapLoadTask;
import com.mcnedward.museum.model.Media;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edward on 3/20/2016.
 */
public class FolderActivity extends AppCompatActivity {
    private static final String TAG = "FolderActivity";

    private List<Image> images;
    private MediaGridAdapter adapter;
    private List<BitmapLoadTask> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Directory directory = (Directory) getIntent().getSerializableExtra("directory");
        setTitle(directory.getName());

        images = (ArrayList<Image>) getIntent().getSerializableExtra("images");

        GridView gridView = (GridView) findViewById(R.id.grid_images);
        adapter = new MediaGridAdapter(this);
        gridView.setAdapter(adapter);

        List<Media> directoryMediaList = new ArrayList<>();
        for (Directory d : directory.getChildDirectories())
            directoryMediaList.add(new Media(d));
        adapter.addAll(directoryMediaList);

        tasks = new ArrayList<>();
        for (Image image : images) {
            BitmapLoadTask task = new BitmapLoadTask(this, image, adapter);
            tasks.add(task);
            task.execute();

            adapter.add(new Media(image));
        }
    }

    @Override
    public void onDestroy() {
        for (BitmapLoadTask task : tasks)
            task.cancel(true);
        super.onDestroy();
    }

}
