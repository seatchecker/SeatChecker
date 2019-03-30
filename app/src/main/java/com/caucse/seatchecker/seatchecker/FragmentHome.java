package com.caucse.seatchecker.seatchecker;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class FragmentHome extends Fragment {

    private DBController dbController = new DBController();
    private RecyclerView recyclerView = null;
    private LinearLayoutManager mLinearLayoutManager;
    private CafeListAdapter mAdapter ;
    protected ArrayList<Cafe> cafes;

    private final Handler myHandler = new Handler();
    final Runnable updateUI = new Runnable(){

        @Override
        public void run() {
            //call the activity method that updates the  UI
            setList();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        //get data from firestore
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"백그라운드 동작시작");
                cafes = dbController.getCafeList();
                myHandler.post(updateUI);   //update UI
            }
        }).start();

        /*
        recyclerView = (RecyclerView)getView().findViewById(R.id.cafeList_recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(getContext());

        ArrayList<Cafe> cafes = new ArrayList<>();
        mAdapter = new CafeListAdapter(cafes);
        recyclerView.setAdapter(mAdapter);

        //recyclerview row 사이 수평선
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

*/
        return inflater.inflate(R.layout.home_fragment,container,false);
    }

    public void setList(){

        for(int i = 0; i<cafes.size();i++){
            Log.d(TAG,"결과:"+cafes.get(i).getAddress_gu());
        }

    }
}
