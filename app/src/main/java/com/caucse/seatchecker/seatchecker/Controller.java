package com.caucse.seatchecker.seatchecker;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.transform.Result;

class Controller {

    private Viewer viewer;
    private Context context;
    private ArrayList<TableInfo> TwoTables;
    private ArrayList<TableInfo> FourTables;
    private ArrayList<TableInfo> ResultTables;
    private ArrayList<GridElement> arrays;
    private Cafe cafe;


    Controller(Context context, Cafe cafe){
        this.context = context;
        arrays = new ArrayList<>();
        TwoTables = new ArrayList<>();
        FourTables = new ArrayList<>();
        ResultTables = new ArrayList<>();
        this.cafe = cafe;
    }

    void initTableGridView(GridAdapter.GridItemListener listener,ArrayList<TableInfo> tables){

        int width = Integer.parseInt(cafe.getGrid().get("width").toString());
        int length =  Integer.parseInt(cafe.getGrid().get("length").toString());

        arrays = new ArrayList<>();
        for(int i = 0; i<width*length; i++){
            arrays.add(new GridElement());
        }

        for(int i = 0; i<tables.size(); i++){
            TableInfo table = tables.get(i);
            if(Integer.parseInt(table.getPosition().get("second").toString())== -1 ){
                TwoTables.add(table);
            }else{
                FourTables.add(table);
            }
        }
        int counterFirst = Integer.parseInt(cafe.getCounter().get("first").toString());
        int counterSecond = Integer.parseInt(cafe.getCounter().get("second").toString());

        arrays.get(counterFirst).setStatus(TableInfo.COUNTER);
        if(counterSecond != -1){
            arrays.get(counterSecond).setStatus(TableInfo.COUNTER);
        }

        int doorFirst = Integer.parseInt(cafe.getDoor().get("first").toString());
        int doorSecond = Integer.parseInt(cafe.getDoor().get("second").toString());

        arrays.get(doorFirst).setStatus(TableInfo.DOOR);
        if(doorSecond != -1){
            arrays.get(doorSecond).setStatus(TableInfo.DOOR);
        }

        viewer = new Viewer(context);
        viewer.TableGridViewer(listener,arrays,width,length);
    }


    int addTwoTable(int position){
        if(arrays.get(position).getStatus() != TableInfo.NONE) {
            return -1;
        }
        TableInfo curTable= TwoTables.get(0);
        curTable.setPosition(position,-1);
        ResultTables.add(curTable);

        arrays.get(position).setStatus(TableInfo.TWOTABLE);
        arrays.get(position).setName(curTable.getTableName());
        viewer.updateGrid(position);

        TwoTables.remove(curTable);
        if(TwoTables.isEmpty() )
            return 0;
        return 1;
    }

    int addFourTable(int startPosition, int endPosition){
        if(arrays.get(startPosition).getStatus() != TableInfo.NONE
                || arrays.get(endPosition).getStatus() != TableInfo.NONE)  return -1;

            TableInfo curTable = FourTables.get(0);
            curTable.setPosition(startPosition,endPosition);
            ResultTables.add(curTable);

            arrays.get(startPosition).setStatus(TableInfo.FOURTABLE);
            arrays.get(endPosition).setStatus(TableInfo.FOURTABLE);
            arrays.get(startPosition).setName(curTable.getTableName());
            arrays.get(endPosition).setName(curTable.getTableName());
            viewer.updateGrid(startPosition);
            viewer.updateGrid(endPosition);

            FourTables.remove(curTable);

            if(FourTables.isEmpty()){
                return 0;
            }

            return 1;

    }
    int deleteTable(int position){

        if(arrays.get(position).getStatus() != TableInfo.TWOTABLE
                && arrays.get(position).getStatus() != TableInfo.FOURTABLE)
            return -1;

        for(int i = 0; i< ResultTables.size(); i++){
            TableInfo result = ResultTables.get(i);
            int first =Integer.parseInt(result.getPosition().get("first").toString());
            int second = Integer.parseInt(result.getPosition().get("second").toString());
            if( first== position || second == position){
                if(second == -1){//if two tables
                    addTable(result,TwoTables);
                    arrays.get(first).setStatus(TableInfo.NONE);
                    arrays.get(first).setName("");
                    ResultTables.remove(i);
                    viewer.updateGrid(first);
                    if(TwoTables.size() == 1){
                        return 1;
                    }
                }else {
                    addTable(result, FourTables);
                    arrays.get(first).setStatus(TableInfo.NONE);
                    arrays.get(first).setName("");
                    arrays.get(second).setStatus(TableInfo.NONE);
                    arrays.get(second).setName("");
                    ResultTables.remove(i);
                    viewer.updateGrid(first);
                    viewer.updateGrid(second);

                    if(FourTables.size() == 1){
                        return 2;
                    }

                }

            }
        }
        return 0;
    }

    private void addTable(TableInfo info,ArrayList<TableInfo> list){
        for(int i = 0; i < list.size() ; i++){
            String tableName = list.get(i).getTableName();
            if(info.getTableName().compareTo(tableName) < 0){
                list.add(i,info);
            }
        }
    }


    boolean isAllTableSettingComplete(){
        return TwoTables.isEmpty() && FourTables.isEmpty();
    }
}







