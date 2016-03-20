package com.mcnedward.museum.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcnedward.museum.R;
import com.mcnedward.museum.model.Folder;

import java.util.List;
import java.util.Random;

/**
 * Created by Edward on 3/20/2016.
 */
public class FolderFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Folder>> {
    private static final String TAG = "FolderFragment";
    private final int LOADER_ID = new Random().nextInt(1000);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        initialize(view);
        return view;
    }

    protected void initialize(View view) {
        initializeLoader();
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
    public Loader<List<Folder>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Folder>> loader, List<Folder> data) {

    }

    @Override
    public void onLoaderReset(Loader<List<Folder>> loader) {

    }

}
