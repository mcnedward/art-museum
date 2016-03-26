package com.mcnedward.museum.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mcnedward.museum.listener.BitmapListener;
import com.mcnedward.museum.model.IMedia;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edward on 3/17/2016.
 */
public abstract class BaseListAdapter<T> extends BaseAdapter implements BitmapListener {

    protected List<T> groups;
    protected Context context;
    protected LayoutInflater inflater;

    public BaseListAdapter(Context context) {
        this(context, new ArrayList<T>());
    }

    public BaseListAdapter(Context context, List<T> groups) {
        this.groups = groups;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void add(T item) {
        groups.add(item);
        notifyDataSetChanged();
    }

    public void addAll(List<T> items) {
        groups.addAll(items);
        notifyDataSetChanged();
    }

    public void setGroups(List<T> groups) {
        this.groups = groups;
        notifyDataSetChanged();
    }

    public void reset() {
        groups = new ArrayList<>();
        notifyDataSetChanged();
    }

    protected abstract View getCustomView(T item);

    protected abstract void setViewContent(T item, View view);

    protected abstract View.OnClickListener getOnClickListener(T item);

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = getCustomView(getItem(position));
        }
        setViewContent(getItem(position), convertView);
        convertView.setOnClickListener(getOnClickListener(getItem(position)));
        return convertView;
    }
    @Override
    public int getCount() {
        return groups.size();
    }

    @Override
    public T getItem(int position) {
        return groups.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
