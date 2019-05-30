package com.caucse.seatchecker.seatchecker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

import static com.caucse.seatchecker.seatchecker.MainActivity.androidID;

public class AlarmListAdapter  extends RecyclerView.Adapter<AlarmListAdapter.ViewHolder>{

    private int deletePosition;
    private AlarmRealm deleteAlarm;
    private ArrayList<AlarmRealm> list;
    private Context context;

    AlarmListAdapter(ArrayList<AlarmRealm> alarms, Context context) {
        this.list = alarms;
        this.context = context;
    }

    public synchronized void addItem(AlarmRealm alarm){
        synchronized (this){
            int pos = list.size();
            list.add(alarm);
            notifyItemInserted(pos);
            final DatabaseReference reference = FirebaseDatabase.getInstance()
                    .getReference().child(alarm.getCafeDid()).child("push").child(androidID);
            pushInformation my = new pushInformation(FirebaseInstanceId.getInstance().getToken(), alarm.getTableNum(), alarm.isPlug());
            reference.setValue(my);
        }
    }

    synchronized void deleteItem(int position){
        synchronized (this){
            deleteAlarm = list.get(position);
            deletePosition = position;
            //remove data from db

            FirebaseDatabase.getInstance().getReference().child(deleteAlarm.getCafeDid()).child("push").child(androidID).removeValue();
            list.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(0,list.size());
            showUndoSnackBar();
        }

    }

    private void showUndoSnackBar(){
        View view = ((Activity)context).getWindow().getDecorView().findViewById(R.id.alarmListRecyclerVIew);
        Snackbar snackbar = Snackbar.make(view, "알림을 해제하셨습니다.",Snackbar.LENGTH_LONG);
        snackbar.setAction("실행취소", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add(deletePosition,deleteAlarm);

                final DatabaseReference reference = FirebaseDatabase.getInstance()
                        .getReference().child(deleteAlarm.getCafeDid()).child("push").child(androidID);
                pushInformation my = new pushInformation(FirebaseInstanceId.getInstance().getToken(), deleteAlarm.getTableNum(), deleteAlarm.isPlug());
                reference.setValue(my);
                notifyItemInserted(deletePosition);

            }
        });
        snackbar.show();

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
    public void onBindViewHolder(@NonNull final AlarmListAdapter.ViewHolder holder, int position) {
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
                ProgressBar bar = new ProgressBar(context);

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
