package com.caucse.seatchecker.seatchecker;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentAlarm extends Fragment {

    private View view;
    private ArrayList<AlarmRealm> array;
    private String androidId;
    protected static DBController dbController;
    protected static  Viewer alarmView;
    private boolean init = true;
    private ItemTouchHelper itemTouchHelper;
    AlarmListAdapter alarmListAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.alarm_fragment,container,false);

        androidId = "" + android.provider.Settings.Secure.getString(view.getContext().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        array = new ArrayList<>();

        //alarmView = new Viewer(view);
        //alarmView.AlarmListViewer(array);

        alarmListAdapter = new AlarmListAdapter(array, view.getContext());
        final RecyclerView recyclerView = view.findViewById(R.id.alarmListRecyclerVIew);
        recyclerView.setAdapter(alarmListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(alarmListAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        getAlarmList();

        return view;
    }

    private synchronized void getAlarmList() {

        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.show();

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        final String androidID = MainActivity.androidID;

        array.clear();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.child("push").child(androidID).exists()) {
                        final pushInformation curPush = snapshot.child("push").child(androidID).getValue(pushInformation.class);
                        final String cafeDid = snapshot.getKey();
                        String cafeName = (String)snapshot.child("cafeName").getValue();

                        assert curPush != null;
                        AlarmRealm curalarm = new AlarmRealm(cafeDid, cafeName,curPush.getNumOfTable(), curPush.isPlug());
                        array.add(curalarm);

                    }
                }
                alarmListAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        if(!init){
            getAlarmList();
        }
        init = false;

    }
}
