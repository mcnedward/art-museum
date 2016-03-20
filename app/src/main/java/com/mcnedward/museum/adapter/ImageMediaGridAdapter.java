package com.mcnedward.museum.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.mcnedward.museum.model.Image;

/**
 * Created by Edward on 3/17/2016.
 */
public class ImageMediaGridAdapter extends BaseListAdapter<Image> {
    private static final String TAG = "ImageMediaGridAdapter";

    public ImageMediaGridAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return convertView;
    }

    @Override
    protected void setOnClickListener(Image media, View view) {

    }

}
