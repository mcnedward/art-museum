package com.mcnedward.museum.activity.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.mcnedward.museum.async.FolderLoader;
import com.mcnedward.museum.R;
import com.mcnedward.museum.adapter.FolderGridAdapter;
import com.mcnedward.museum.model.Directory;

import java.util.List;
import java.util.Random;

/**
 * Created by Edward on 3/20/2016.
 */
public class GalleryFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<List<Directory>> {
    private static final String TAG = "GalleryFragment";
    private final int LOADER_ID = new Random().nextInt(1000);
    public static int SIZE;

    private Context context;
    private FolderLoader loader;
    private FolderGridAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        initialize(view);
        return view;
    }

    protected void initialize(View view) {
        context = view.getContext();

        initializeWindow();
        initializeLoader();
        initializeGrid(view);
    }

    private void initializeGrid(View view) {
        GridView gridView = (GridView) view.findViewById(R.id.grid_images);
        adapter = new FolderGridAdapter(context);
        gridView.setAdapter(adapter);
        gridView.setGravity(Gravity.CENTER);
    }

    private void initializeLoader() {
        Log.d(TAG, "### Calling initLoader! ###");
        if (getActivity().getSupportLoaderManager().getLoader(LOADER_ID) == null)
            Log.d(TAG, "### Initializing a new Loader... ###");
        else
            Log.d(TAG, "### Reconnecting with existing Loader (id " + LOADER_ID + ")... ###");
        getLoaderManager().initLoader(LOADER_ID, null, this).forceLoad();
    }

    @Override
    public Loader<List<Directory>> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "### Creating loader ###");
        loader = new FolderLoader(context);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<Directory>> loader, List<Directory> data) {
        adapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Directory>> loader) {
        adapter.reset();
    }

    private void initializeWindow() {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        SIZE = (dm.widthPixels / 2);
    }
}
