package com.caucse.seatchecker.seatchecker;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class AlarmListAdapter  extends RecyclerView.Adapter<AlarmListAdapter.ViewHolder>{

    private ArrayList<AlarmRealm> list;
    private Context context;

    AlarmListAdapter(ArrayList<AlarmRealm> alarms, Context context) {
        this.list = alarms;
        this.context = context;
    }

    @NonNull
    @Override
    public AlarmListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View alarmView = layoutInflater.inflate(R.layout.alarm_list_item, parent, false);

        return new ViewHolder(alarmView);

    }

    @Override
    public void onBindViewHolder(@NonNull AlarmListAdapter.ViewHolder holder, int position) {
        final AlarmRealm alarm = list.get(position);

        TextView txtName = holder.tvCafeName;
        TextView tvTableCount = holder.tvTableCount;
        TextView tvPlug = holder.tvIsPlug;

        txtName.setText(alarm.getCafeName());
        //txtName.setText("");
        switch(alarm.getTableNum()){
            case 1: tvTableCount.setText("1인 좌석");
            break;
            case 2 : tvTableCount.setText("2인 좌석");
                break;
            case 4: tvTableCount.setText("4인 좌석");
                break;
        }

        if(alarm.isPlug()){
            tvPlug.setText("플러그 있는 좌석");
        }else{
            tvPlug.setText("플러그 없는 좌석");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Intent intent = new Intent(v.getContext(),CafeInfoActivity.class);
                intent.putExtra("name",alarm.getCafeName());

                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference rf = db.collection("cafeinfo").document(alarm.getCafeDid());
                rf.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        assert documentSnapshot != null;
                        Cafe cafe = documentSnapshot.toObject(Cafe.class);
                        intent.putExtra("CAFE",cafe);
                        v.getContext().startActivity(intent);
                    }

                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCafeName;
        TextView tvTableCount;
        TextView tvIsPlug;

        //ViewHolder 생성
        ViewHolder(View itemView) {
            super(itemView);

            tvCafeName = itemView.findViewById(R.id.tvCafeName);
            tvTableCount = itemView.findViewById(R.id.tvTableNum);
            tvIsPlug = itemView.findViewById(R.id.tvPlug);
        }
    }
}
