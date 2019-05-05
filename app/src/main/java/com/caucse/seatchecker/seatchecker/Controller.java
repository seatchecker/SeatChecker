package com.caucse.seatchecker.seatchecker;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.transform.Result;

class Controller {

    private Viewer viewer;
    private Context context;
    private ArrayList<TableInfo> TwoTables;
    private ArrayList<TableInfo> FourTables;
    private ArrayList<TableInfo> OneTables;
    private ArrayList<TableInfo> ResultTables;
    private ArrayList<GridElement> arrays;
    private Cafe cafe;


    Controller(Context context, Cafe cafe){
        this.context = context;
        arrays = new ArrayList<>();
        OneTables = new ArrayList<>();
        TwoTables = new ArrayList<>();
        FourTables = new ArrayList<>();
        ResultTables = new ArrayList<>();
        this.cafe = cafe;
    }

    void initTableGridView(GridAdapter.GridItemListener listener,ArrayList<TableInfo> tables){

        int width = Integer.parseInt(cafe.getGrid().get("width").toString());
        int length =  Integer.parseInt(cafe.getGrid().get("length").toString());

        ResultTables.addAll(tables);
        //arrays = new ArrayList<>();

        for(int i = 0; i<width*length; i++){
            arrays.add(new GridElement());
        }

        for(int i = 0; i<tables.size(); i++){
            TableInfo table = tables.get(i);
            //if(Integer.parseInt(table.getPosition().get("second").toString())== -1 ){
            if(table.getCapacity() == 1){
                int first = Integer.parseInt(table.getPosition().get("first").toString());
                arrays.get(first).setInformation(TableInfo.ONETABLE,table.isPlug(),table.getTableName(),table.getOrientation());
            }else if(table.getCapacity() == 2){
                int first = Integer.parseInt(table.getPosition().get("first").toString());
                arrays.get(first).setInformation(TableInfo.TWOTABLE,table.isPlug(),table.getTableName(),table.getOrientation());
            }
            else{
                int first = Integer.parseInt(table.getPosition().get("first").toString());
                int second = Integer.parseInt(table.getPosition().get("second").toString());
                arrays.get(first).setInformation(TableInfo.FOURTABLE,table.isPlug(),table.getTableName(),table.getOrientation());
                arrays.get(second).setInformation(TableInfo.FOURTABLE,table.isPlug(),table.getTableName(),table.getOrientation());
            }
        }
        int counterFirst = Integer.parseInt(cafe.getCounter().get("first").toString());
        int counterSecond = Integer.parseInt(cafe.getCounter().get("second").toString());

        arrays.get(counterFirst).setStatus(TableInfo.COUNTER);
        arrays.get(counterFirst).setName("카운터");
        if(counterSecond != -1){
            arrays.get(counterSecond).setStatus(TableInfo.COUNTER);
            arrays.get(counterSecond).setName("카운터");
        }

        int doorFirst = Integer.parseInt(cafe.getDoor().get("first").toString());
        int doorSecond = Integer.parseInt(cafe.getDoor().get("second").toString());

        arrays.get(doorFirst).setStatus(TableInfo.DOOR);
        arrays.get(doorFirst).setName("출입문");
        if(doorSecond != -1){
            arrays.get(doorSecond).setStatus(TableInfo.DOOR);
            arrays.get(doorSecond).setName("출입문");
        }

        viewer = new Viewer(context);
        viewer.TableGridViewer(listener,arrays,width,length);
    }

    void initTablePlugView(GridAdapter.GridItemListener listener,ArrayList<TableInfo> tables){

        ResultTables = tables;
        int width = Integer.parseInt(cafe.getGrid().get("width").toString());
        int length =  Integer.parseInt(cafe.getGrid().get("length").toString());
        arrays = new ArrayList<>();
        for(int i = 0; i<width*length; i++){
            arrays.add(new GridElement());
            arrays.get(i).setName("");
            arrays.get(i).setPlug(false);
        }
        for(int i = 0; i<tables.size();i++){
            TableInfo curTable = tables.get(i);

            int pos1 = Integer.parseInt(curTable.getPosition().get("first").toString());
            int pos2 = Integer.parseInt(curTable.getPosition().get("second").toString());

            if(curTable.getCapacity() == 1){
                arrays.get(pos1).setInformation(TableInfo.ONETABLE,curTable.isPlug(),"",curTable.getOrientation());
                if(curTable.isPlug()){
                    arrays.get(pos1).setName("P");
                }
            }
            else if(curTable.getCapacity() == 2){
                arrays.get(pos1).setInformation(TableInfo.TWOTABLE,curTable.isPlug(),"",curTable.getOrientation());
                if(curTable.isPlug()){
                    arrays.get(pos1).setName("P");
                }
            }else{
                arrays.get(pos1).setInformation(TableInfo.FOURTABLE,curTable.isPlug(),"",curTable.getOrientation());
                arrays.get(pos2).setInformation(TableInfo.FOURTABLE,curTable.isPlug(),"",curTable.getOrientation());
                if(curTable.isPlug()){
                    arrays.get(pos1).setName("P");
                    arrays.get(pos2).setName("P");
                }
            }

        }
        int counterFirst = Integer.parseInt(cafe.getCounter().get("first").toString());
        int counterSecond = Integer.parseInt(cafe.getCounter().get("second").toString());

        arrays.get(counterFirst).setStatus(TableInfo.COUNTER);
        arrays.get(counterFirst).setName("카운터");
        if(counterSecond != -1){
            arrays.get(counterSecond).setStatus(TableInfo.COUNTER);
            arrays.get(counterSecond).setName("카운터");
        }

        int doorFirst = Integer.parseInt(cafe.getDoor().get("first").toString());
        int doorSecond = Integer.parseInt(cafe.getDoor().get("second").toString());

        arrays.get(doorFirst).setStatus(TableInfo.DOOR);
        arrays.get(doorFirst).setName("출입문");
        if(doorSecond != -1){
            arrays.get(doorSecond).setStatus(TableInfo.DOOR);
            arrays.get(doorSecond).setName("출입문");
        }

        viewer = new Viewer(context);
        viewer.TablePlugGridViewer(listener,arrays,width,length);
    }

    int addOneTable(int position){
        if(arrays.get(position).getStatus() != TableInfo.NONE){
            return -1;
        }
        TableInfo curTable = OneTables.get(0);
        curTable.setPosition(position,-1);
        ResultTables.add(curTable);

        arrays.get(position).setInformation(TableInfo.ONETABLE,curTable.isPlug(),curTable.getTableName(),curTable.getOrientation());
        viewer.updateGrid(position);

        OneTables.remove(curTable);
        if(OneTables.isEmpty()){
            return 0;
        }
        return 1;
    }
    int addTwoTable(int position){
        if(arrays.get(position).getStatus() != TableInfo.NONE) {
            return -1;
        }
        TableInfo curTable= TwoTables.get(0);
        curTable.setPosition(position,-1);
        ResultTables.add(curTable);

        arrays.get(position).setInformation(TableInfo.TWOTABLE,curTable.isPlug(),curTable.getTableName(),curTable.getOrientation());
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

            arrays.get(startPosition).setInformation(TableInfo.FOURTABLE,curTable.isPlug(),curTable.getTableName(),curTable.getOrientation());
            arrays.get(endPosition).setInformation(TableInfo.FOURTABLE,curTable.isPlug(),curTable.getTableName(),curTable.getOrientation());

            viewer.updateGrid(startPosition);
            viewer.updateGrid(endPosition);

            FourTables.remove(curTable);

            if(FourTables.isEmpty()){
                return 0;
            }

            return 1;

    }
    synchronized int deleteTable(int position){
        if(arrays.get(position).getStatus() != TableInfo.TWOTABLE
                && arrays.get(position).getStatus() != TableInfo.FOURTABLE
                    && arrays.get(position).getStatus() != TableInfo.ONETABLE)
            return -1;

        for(int i = 0; i< ResultTables.size(); i++){
            TableInfo result = ResultTables.get(i);
            int first =Integer.parseInt(result.getPosition().get("first").toString());
            int second = Integer.parseInt(result.getPosition().get("second").toString());

            if( first== position || second == position){
                if(result.getCapacity() == 2){//if two tables
                    addTable(result,TwoTables);
                    arrays.get(first).setInformation(TableInfo.NONE,false,"","");
                    ResultTables.remove(i);
                    viewer.updateGrid(first);
                    return 1;
                }else if(result.getCapacity() == 1){
                    addTable(result, OneTables);
                    arrays.get(first).setInformation(TableInfo.NONE,false,"","");
                    ResultTables.remove(i);
                    viewer.updateGrid(first);
                    return 0;
                }
                else {
                    addTable(result, FourTables);
                    arrays.get(first).setInformation(TableInfo.NONE,false,"","");
                    arrays.get(second).setInformation(TableInfo.NONE,false,"","");

                    ResultTables.remove(i);
                    viewer.updateGrid(first);
                    viewer.updateGrid(second);
                    return 2;

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
                return;
            }
        }
        list.add(info);
    }


    boolean isAllTableSettingComplete(){
        return TwoTables.isEmpty() && FourTables.isEmpty() && OneTables.isEmpty();
    }
    ArrayList<TableInfo> getResultTable(){
        return this.ResultTables;
    }


    synchronized void addPlug(int position){

        if(!arrays.get(position).isPlug()) {

            if (arrays.get(position).getStatus() == TableInfo.TWOTABLE
                    || arrays.get(position).getStatus() == TableInfo.ONETABLE) {
                arrays.get(position).setPlug(true);
                arrays.get(position).setName("P");
                for (TableInfo table : ResultTables) {
                    int pos1 = Integer.parseInt(table.getPosition().get("first").toString());
                    if (pos1 == position) {
                        table.setPlug(true);
                        viewer.updateGrid(pos1);
                        break;
                    }
                }
            } else if (arrays.get(position).getStatus() == TableInfo.FOURTABLE) {
                for (TableInfo table : ResultTables) {
                    int pos1 = Integer.parseInt(table.getPosition().get("first").toString());
                    int pos2 = Integer.parseInt(table.getPosition().get("second").toString());
                    if (pos1 == position || pos2 == position) {
                        arrays.get(pos1).setPlug(true);
                        arrays.get(pos2).setPlug(true);
                        arrays.get(pos1).setName("P");
                        arrays.get(pos2).setName("P");

                        table.setPlug(true);
                        viewer.updateGrid(pos1);
                        viewer.updateGrid(pos2);
                        break;
                    }
                }
            }
        }else{//if plugged
            if (arrays.get(position).getStatus() == TableInfo.TWOTABLE
                    ||arrays.get(position).getStatus() == TableInfo.ONETABLE) {
                arrays.get(position).setPlug(false);
                arrays.get(position).setName("");
                for (TableInfo table : ResultTables) {
                    int pos1 = Integer.parseInt(table.getPosition().get("first").toString());
                    if (pos1 == position) {
                        table.setPlug(false);
                        viewer.updateGrid(pos1);
                        break;
                    }
                }
            } else if (arrays.get(position).getStatus() == TableInfo.FOURTABLE) {
                for (TableInfo table : ResultTables) {
                    int pos1 = Integer.parseInt(table.getPosition().get("first").toString());
                    int pos2 = Integer.parseInt(table.getPosition().get("second").toString());
                    if (pos1 == position || pos2 == position) {
                        arrays.get(pos1).setPlug(false);
                        arrays.get(pos2).setPlug(false);
                        arrays.get(pos1).setName("");
                        arrays.get(pos2).setName("");

                        table.setPlug(false);
                        viewer.updateGrid(pos1);
                        viewer.updateGrid(pos2);
                        break;
                    }
                }
            }
        }
    }





}







