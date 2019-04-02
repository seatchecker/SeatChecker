package com.caucse.seatchecker.seatchecker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class CafeListAdapter extends RecyclerView.Adapter<CafeListAdapter.ViewHolder> {


    private ArrayList<Cafe> list;

    CafeListAdapter(ArrayList<Cafe> cafe) {
        this.list = cafe;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        //RecyclerView에 들어갈 Data(Student로 이루어진 ArrayList 배열인 arrayListOfStudent)를 기반으로 Row를 생성할 때
        //해당 row의 위치에 해당하는 Student를 가져와서
        final Cafe cafe = list.get(position);

        //넘겨받은 ViewHolder의 Layout에 있는 View들을 어떻게 다룰지 설정
        //ex. TextView의 text를 어떻게 설정할지, Button을 어떻게 설정할지 등등...
        TextView txtName = holder.txtName;
        txtName.setText(cafe.getAddress_dong());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),CafeInfoActivity.class);
                intent.putExtra("name",cafe.getName());
                v.getContext().startActivity(intent);
            }
        });

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        //받은 Context를 기반으로 LayoutInflater를 생성
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        //생성된 LayoutInflater로 어떤 Layout을 가져와서 어떻게 View를 그릴지 결정
        View cafeView = layoutInflater.inflate(R.layout.cafelist_item, parent, false);

        //생성된 ViewHolder를 OnBindViewHolder로 넘겨줌
        return new ViewHolder(cafeView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //RecyclerView에 들어갈 Data(Student로 이루어진 ArrayList 배열인 arrayListOfStudent)를 기반으로 Row를 생성할 때
        //해당 row의 위치에 해당하는 Student를 가져와서
        Cafe cafe = list.get(position);

        //넘겨받은 ViewHolder의 Layout에 있는 View들을 어떻게 다룰지 설정
        //ex. TextView의 text를 어떻게 설정할지, Button을 어떻게 설정할지 등등...
        TextView txtName = holder.txtName;
        txtName.setText(cafe.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;
        public TextView txtAddress;

        //ViewHolder 생성
        ViewHolder(View itemView) {
            super(itemView);

            //Complete recycler view
            txtName = itemView.findViewById(R.id.textview_recyclerview);
            //txtAddress = (TextView) itemView.findViewById(R.id.txtAddress);

        }
    }
}

