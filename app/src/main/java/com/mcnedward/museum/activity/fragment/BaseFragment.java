package com.mcnedward.museum.activity.fragment;

import android.support.v4.app.Fragment;

import com.mcnedward.museum.enums.FragmentCode;

/**
 * Created by Edward on 3/20/2016.
 */
public class BaseFragment extends Fragment {

    public static BaseFragment newInstance(FragmentCode code) {
        switch (code) {
            case GALLERY:
                return new GalleryFragment();
            case FOLDER:
                return new FolderFragment();
        }
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
