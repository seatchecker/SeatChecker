package com.caucse.seatchecker.seatchecker;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

class Viewer {
    private View view;
    private Context context;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private AlarmListAdapter alarmListAdapter;
    private GridAdapter adapter;
    private int startPoint = -1;
    private ItemTouchHelper itemTouchHelper;

    Viewer(View view){
        this.view = view;
        this.context = view.getContext();
    }

    Viewer (Context context){
        this.context = context;
        recyclerView = ((Activity)context).getWindow().getDecorView().findViewById(R.id.recyclerView);
    }

    void CafeListViewer(ArrayList<Cafe> cafes){
        CafeListAdapter recyclerDataAdapter = new CafeListAdapter(cafes, view.getContext());
        RecyclerView recyclerView = view.findViewById(R.id.cafeList_recyclerView);
        recyclerView.setAdapter(recyclerDataAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }


    void AlarmListViewer(final ArrayList<AlarmRealm> alarms){
        alarmListAdapter = new AlarmListAdapter(alarms, view.getContext());
        final RecyclerView recyclerView = view.findViewById(R.id.alarmListRecyclerVIew);
        recyclerView.setAdapter(alarmListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public synchronized void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int position  = viewHolder.getAdapterPosition();
                AlarmRealm alarm = alarms.get(position);
                final String alarmDid = alarm.getCafeDid();
                final String deviceID = "" + android.provider.Settings.Secure.getString(view.getContext().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
                FirebaseDatabase.getInstance().getReference().child(alarmDid).child("push").child(deviceID).removeValue();
                Toast.makeText(context, alarm.getCafeName()+"의 알림이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                alarms.remove(position);
                recyclerView.getAdapter().notifyItemChanged(position);

            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    void updateAlarmList(){
        alarmListAdapter.notifyDataSetChanged();
    }

    void TableGridViewer(final GridAdapter.GridItemListener listener, ArrayList<GridElement> arrays, int width, int length){
        layoutManager = new GridLayoutManager(context, width);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new GridAdapter(context, arrays ,listener,width,length);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                int action = e.getAction();
                switch(action ){
                    case MotionEvent.ACTION_DOWN :
                        View reView = rv.findChildViewUnder(e.getX(),e.getY());
                        startPoint = rv.getChildAdapterPosition(reView);
                        break;
                    case MotionEvent.ACTION_UP :
                        View reView2 = rv.findChildViewUnder(e.getX(),e.getY());
                        int ePosition = rv.getChildAdapterPosition(reView2);
                        listener.onItemDrag(startPoint,ePosition);
                        break;

                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT , 0) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Log.d(TAG,"ONMOVE CALLED!!!");
                final int fromPos = viewHolder.getAdapterPosition();
                final int toPos = target.getAdapterPosition();
                listener.onItemDrag(fromPos,toPos);
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }
        };
        ItemTouchHelper helper = new ItemTouchHelper(simpleCallback);
        helper.attachToRecyclerView(recyclerView);


    }


    void TablePlugGridViewer(final GridAdapter.GridItemListener listener, ArrayList<GridElement> arrays, int width, int length){
        recyclerView = ((Activity)context).getWindow().getDecorView().findViewById(R.id.plugRecyclerView);
        layoutManager = new GridLayoutManager(context, width);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new GridAdapter(context, arrays ,listener,width,length);
        recyclerView.setAdapter(adapter);
    }
    void updateGrid(int position){
        adapter.notifyItemChanged(position);
    }



}
