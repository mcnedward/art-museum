package com.mcnedward.museum.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mcnedward.museum.FolderLoader;
import com.mcnedward.museum.R;
import com.mcnedward.museum.adapter.ImageMediaGridAdapter;

/**
 * Created by Edward on 3/20/2016.
 */
public class FolderViewActivity extends AppCompatActivity {
    private static final String TAG = "FolderViewActivity";

    private FolderLoader loader;
    private ImageMediaGridAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}
