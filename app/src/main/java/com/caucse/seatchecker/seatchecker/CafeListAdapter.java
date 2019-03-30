package com.caucse.seatchecker.seatchecker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CafeListAdapter extends RecyclerView.Adapter<CafeListAdapter.CustomViewHolder> {

    private ArrayList<Cafe> cafeList;

    class CustomViewHolder extends RecyclerView.ViewHolder{

        ImageView ivImageView;
        TextView tvNameOfCafe;

        CustomViewHolder(View view) {
            super(view);
            this.ivImageView = view.findViewById(R.id.imageview_recyclerview);
            this.tvNameOfCafe = view.findViewById(R.id.textview_recyclerview);

        }
    }

    public  CafeListAdapter(ArrayList<Cafe> list){
        this.cafeList = list;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cafelist_item, parent, false);

        return new CustomViewHolder(view);
    }

    //show data
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.tvNameOfCafe.setText(cafeList.get(position).getAddress_dong());
        //todo : show information of cafes
    }

    @Override
    public int getItemCount() {
        return (null != cafeList? cafeList.size() : 0);
    }


}
