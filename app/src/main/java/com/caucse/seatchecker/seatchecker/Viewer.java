package com.caucse.seatchecker.seatchecker;

import android.content.Context;
import android.nfc.Tag;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;


import java.util.ArrayList;

class Viewer {
    View view;

    Viewer(View view,ArrayList<Cafe> cafes){
        CafeListAdapter recyclerDataAdapter = new CafeListAdapter(cafes);
        RecyclerView recyclerView = view.findViewById(R.id.cafeList_recyclerView);
        recyclerView.setAdapter(recyclerDataAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

    }
}
