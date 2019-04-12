package com.caucse.seatchecker.seatchecker;

import android.content.Context;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.ArrayList;

class Controller {

    private Viewer viewer;
    private Context context;
    private ArrayList<TableInfo> tables;
    private Cafe cafe;

    Controller(Context context, Cafe cafe){
        this.context = context;
        tables = new ArrayList<>();
        this.cafe = cafe;
    }

    void initTableGridView(GridAdapter.GridItemListener listener){

        int width = Integer.parseInt(cafe.getGrid().get("width").toString());
        int length =  Integer.parseInt(cafe.getGrid().get("length").toString());

        tables = new ArrayList<>();
        for(int i = 0; i<width*length; i++){
            tables.add(new TableInfo());
        }

        int counterFirst = Integer.parseInt(cafe.getCounter().get("first").toString());
        int counterSecond = Integer.parseInt(cafe.getCounter().get("second").toString());

        tables.get(counterFirst).setKindTable(TableInfo.COUNTER);
        if(counterSecond != -1){
            tables.get(counterSecond).setKindTable(TableInfo.COUNTER);
        }

        int doorFirst = Integer.parseInt(cafe.getDoor().get("first").toString());
        int doorSecond = Integer.parseInt(cafe.getDoor().get("second").toString());

        tables.get(doorFirst).setKindTable(TableInfo.DOOR);
        if(doorSecond != -1){
            tables.get(doorSecond).setKindTable(TableInfo.DOOR);
        }

        viewer = new Viewer(context);
        viewer.TableGridViewer(listener,tables,width,length);
    }


    void addTwoTable(int position){

    }

    void addFourTable(int startPosition, int endPosition){

    }
    void deleteTable(int position){

    }
}







