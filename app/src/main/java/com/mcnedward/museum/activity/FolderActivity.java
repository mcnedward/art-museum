package com.mcnedward.museum.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.mcnedward.museum.R;
import com.mcnedward.museum.adapter.ImageGridAdapter;
import com.mcnedward.museum.model.Image;
import com.mcnedward.museum.async.BitmapLoadTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edward on 3/20/2016.
 */
public class FolderActivity extends AppCompatActivity {
    private static final String TAG = "FolderActivity";

    private List<Image> images;
    private ImageGridAdapter adapter;
    private List<BitmapLoadTask> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String folderName = getIntent().getStringExtra("folderName");
        setTitle(folderName);

        images = (List<Image>) getIntent().getSerializableExtra("images");

        GridView gridView = (GridView) findViewById(R.id.grid_images);
        adapter = new ImageGridAdapter(this, images);
        gridView.setAdapter(adapter);

        tasks = new ArrayList<>();
        for (Image image : images) {
            BitmapLoadTask task = new BitmapLoadTask(this, image, adapter);
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
