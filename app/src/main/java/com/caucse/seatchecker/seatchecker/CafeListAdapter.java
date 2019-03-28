package com.caucse.seatchecker.seatchecker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class CafeListAdapter extends BaseAdapter {

    private ArrayList<Cafe> cafeList = new ArrayList<>();
    @Override
    public int getCount() {
        return cafeList.size();
    }

    @Override
    public Object getItem(int position) {
        return cafeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cafelist_item,parent,false);
        }

        //findviewbyid

        Cafe curCafe = cafeList.get(position);

        //set

        return convertView;

    }
}
