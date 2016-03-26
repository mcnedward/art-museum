package com.mcnedward.museum.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.mcnedward.museum.R;
import com.mcnedward.museum.adapter.FolderGridAdapter;
import com.mcnedward.museum.adapter.ImageGridAdapter;
import com.mcnedward.museum.async.BitmapLoadTask;
import com.mcnedward.museum.model.Directory;
import com.mcnedward.museum.model.Folder;
import com.mcnedward.museum.model.Image;
import com.mcnedward.museum.utils.DirectoryUtil;
import com.mcnedward.museum.view.FolderCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Edward on 3/20/2016.
 */
public class FolderFragment extends BaseFragment {
    private static final String TAG = "FolderActivity";

    private Context context;
    private Directory directory;
    private List<Image> images;
    private GridView gridFolders;
    private GridView gridImages;
    private FolderGridAdapter folderGridAdapter;
    private ImageGridAdapter imageGridAdapter;
    private List<BitmapLoadTask> tasks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_folder, container, false);

        directory = (Directory) getArguments().getSerializable("directory");
//        setTitle(directory.getName());
        images = (ArrayList<Image>) getArguments().getSerializable("images");

        initialize(view);
        return view;
    }

    protected void initialize(View view) {
        context = view.getContext();
        initializeGrids(view);

        loadFolders();
        loadImages();
    }

    private void initializeGrids(View view) {
        gridFolders = (GridView) view.findViewById(R.id.grid_folders);
        gridImages = (GridView) view.findViewById(R.id.grid_images);
    }

    private void loadFolders() {
        folderGridAdapter = new FolderGridAdapter(context);
        gridFolders.setAdapter(folderGridAdapter);

        folderGridAdapter.addAll(directory.getChildDirectories());
    }

    private void loadImages() {
        imageGridAdapter = new ImageGridAdapter(context, images);
        gridImages.setAdapter(imageGridAdapter);
    }

    @Override
    public void onDestroy() {
        for (BitmapLoadTask task : tasks)
            task.cancel(true);
        super.onDestroy();
    }

}
