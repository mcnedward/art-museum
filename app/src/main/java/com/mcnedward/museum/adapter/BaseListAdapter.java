package com.mcnedward.museum.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edward on 3/17/2016.
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {

    protected List<T> groups;
    protected Context context;
    protected LayoutInflater inflater;

    public BaseListAdapter(Context context) {
        this(new ArrayList<T>(), context);
    }

    public BaseListAdapter(List<T> groups, Context context) {
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

    protected abstract void setOnClickListener(T media, View view);

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
