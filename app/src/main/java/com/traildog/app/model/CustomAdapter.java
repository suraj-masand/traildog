package com.traildog.app.model;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.traildog.app.R;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private Activity mContext;
    private List<Treats> mList;
    private LayoutInflater mLayoutInflater = null;
    public CustomAdapter(Activity context, List<Treats> list) {
        mContext = context;
        mList = list;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return mList.size();
    }
    @Override
    public Object getItem(int pos) {
        return mList.get(pos);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        CompleteListViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.program_list, null);
            viewHolder = new CompleteListViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (CompleteListViewHolder) v.getTag();
        }
        viewHolder.mTVItem.setText((CharSequence) mList.get(position));
        return v;
    }
}
class CompleteListViewHolder {
    public TextView mTVItem;
    public CompleteListViewHolder(View base) {
        mTVItem = (TextView) base.findViewById(R.id.listTV);
    }
}