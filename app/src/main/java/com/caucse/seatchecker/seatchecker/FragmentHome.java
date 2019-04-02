package com.caucse.seatchecker.seatchecker;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class FragmentHome extends Fragment{


    private RecyclerView recyclerView = null;
    private LinearLayoutManager mLinearLayoutManager;
    private CafeListAdapter mAdapter ;
    private ArrayList<Cafe> cafes;
    private View view = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment,container,false);
        cafes = new ArrayList<>();

        DBController DB = new DBController(view);
        DB.getCafeInfo();
        return view;
    }

    public void setList(View view){

        if(cafes.size() == 0){
            Log.d(TAG,"LENGTH IS 0!!");
        }
        recyclerView = view.findViewById(R.id.cafeList_recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(getContext());

        mAdapter = new CafeListAdapter(cafes);
        recyclerView.setAdapter(mAdapter);

        //recyclerview row 사이 수평선
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);


    }

}
