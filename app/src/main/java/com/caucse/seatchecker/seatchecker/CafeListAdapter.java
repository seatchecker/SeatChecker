package com.caucse.seatchecker.seatchecker;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class CafeListAdapter extends RecyclerView.Adapter<CafeListAdapter.ViewHolder> {


    private ArrayList<Cafe> list;
    private Context context;

    CafeListAdapter(ArrayList<Cafe> cafe, Context context) {
        this.list = cafe;
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();


        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View cafeView = layoutInflater.inflate(R.layout.cafelist_item, parent, false);

        return new ViewHolder(cafeView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Cafe cafe = list.get(position);

        FirebaseStorage fs = FirebaseStorage.getInstance();
        TextView txtName = holder.txtName;
        txtName.setText(cafe.getName() + "("+cafe.getAddress_gu()+" "+cafe.getAddress_dong()+")");
        ImageView ivPicture = holder.ivPicture;
        //Glide.with(context).load(cafe.getStorage()).into(ivPicture);

        try {
            StorageReference reference = fs.getReference().child(cafe.getImageURL());
            Glide.with(context).load(reference).into(ivPicture);
        }catch(Exception e){
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),CafeInfoActivity.class);
                intent.putExtra("name",cafe.getName());
                intent.putExtra("CAFE",cafe);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;
        TextView txtAddress;
        ImageView ivPicture;

        //ViewHolder 생성
        ViewHolder(View itemView) {
            super(itemView);


            txtName = itemView.findViewById(R.id.textview_recyclerview);
            ivPicture = itemView.findViewById(R.id.imageview_recyclerview);

        }
    }
}

