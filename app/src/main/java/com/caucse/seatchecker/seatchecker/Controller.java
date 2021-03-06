package com.caucse.seatchecker.seatchecker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    void initTableGridView(GridAdapter.GridItemListener listener,ArrayList<TableInfo> tables,int mode){

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
                if(!table.getTag().equals("empty")){
                    arrays.get(first).changeStatusOfSeat(false);
                }else{
                    arrays.get(first).changeStatusOfSeat(true);
                }
            }else if(table.getCapacity() == 2){
                int first = Integer.parseInt(table.getPosition().get("first").toString());
                arrays.get(first).setInformation(TableInfo.TWOTABLE,table.isPlug(),table.getTableName(),table.getOrientation());
                if(!table.getTag().equals("empty")){
                    arrays.get(first).changeStatusOfSeat(false);
                }else{
                    arrays.get(first).changeStatusOfSeat(true);
                }
            }
            else{
                int first = Integer.parseInt(table.getPosition().get("first").toString());
                int second = Integer.parseInt(table.getPosition().get("second").toString());
                arrays.get(first).setInformation(TableInfo.FOURTABLE,table.isPlug(),table.getTableName(),table.getOrientation());
                arrays.get(second).setInformation(TableInfo.FOURTABLE, table.isPlug(), table.getTableName(), table.getOrientation());

                if(!table.getTag().equals("empty")){
                    arrays.get(first).changeStatusOfSeat(false);
                    arrays.get(second).changeStatusOfSeat(false);

                }else{
                    arrays.get(first).changeStatusOfSeat(true);
                    arrays.get(second).changeStatusOfSeat(true);

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
        viewer.TableGridViewer(listener,arrays,width,length,mode);
    }

    void initTablePlugView(GridAdapter.GridItemListener listener,ArrayList<TableInfo> tables,int mode){

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
        viewer.TablePlugGridViewer(listener,arrays,width,length,mode);
    }

    void setPlugInformation(ArrayList<TableInfo> tables){
        for(TableInfo table : tables){
            int first = Integer.parseInt(table.getPosition().get("first").toString());
            int second = Integer.parseInt(table.getPosition().get("second").toString());

            if(table.isPlug()){
                arrays.get(first).setName("P");
                if(second != -1){
                    arrays.get(second).setName("P");
                }
            }else{
                arrays.get(first).setName("");
                if(second != -1){
                    arrays.get(second).setName("");
                }
            }
        }
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




    boolean checkPositionChanged(int position){
        if(arrays.get(position).getStatus() == TableInfo.ONETABLE){
            for(TableInfo result : ResultTables){
                int first =Integer.parseInt(result.getPosition().get("first").toString());
                if(first == position){
                    String orientation = result.getOrientation();
                    String newori = "";
                    switch(orientation){
                        case "above" :
                            newori = "right";
                            break;
                        case "right":
                            newori = "below";
                            break;
                        case "below" :
                            newori = "left";
                            break;
                        case "left":
                            newori = "above";
                            break;
                    }
                    result.setOrientation(newori);
                    arrays.get(position).setOrientation(newori);
                    viewer.updateGrid(position);
                    return true;
                }
            }
        }else if(arrays.get(position).getStatus() == TableInfo.TWOTABLE){
            for(TableInfo result : ResultTables){
                int first =Integer.parseInt(result.getPosition().get("first").toString());
                if(first == position){
                    String orientation = result.getOrientation();
                    String newori = "";
                    switch(orientation){
                        case "above":
                            newori=  "right";
                            break;
                        case "right":
                            newori = "above";
                            break;
                    }
                    result.setOrientation(newori);
                    arrays.get(position).setOrientation(newori);
                    viewer.updateGrid(position);
                    return true;
                }
            }
        }
        return false;
    }


    void updateStatusOfSeats(int pos1, int pos2, boolean isEmptySeat){
        arrays.get(pos1).changeStatusOfSeat(isEmptySeat);
        viewer.updateGrid(pos1);
        if(pos2 != -1){
            arrays.get(pos2).changeStatusOfSeat(isEmptySeat);
            viewer.updateGrid(pos2);
        }
    }

    synchronized void inverseStatus(int pos1, int pos2){
        boolean current = arrays.get(pos1).isEmptySeat();
        arrays.get(pos1).changeStatusOfSeat(!current);
        viewer.updateGrid(pos1);
        if(pos2 != -1){
            arrays.get(pos2).changeStatusOfSeat(!current);
            viewer.updateGrid(pos2);
        }

    }

    void SaveChangedStatusToDB(ArrayList<TableInfo> tables){

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("변경사항을 저장중입니다... 잠시 기다려주세요");
        progressDialog.show();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(cafe.getDid()).child("tableList");
        for(TableInfo table : tables){
            int first = Integer.parseInt(table.getPosition().get("first").toString());

            boolean tableIsEmpty;
            tableIsEmpty = table.getTag().equals("empty");

            //if data was changed,
            if(arrays.get(first).isEmptySeat() != tableIsEmpty){
                if(arrays.get(first).isEmptySeat()){
                    reference.child(table.getTableName()).child("tag").setValue("empty");
                }else{
                    reference.child(table.getTableName()).child("tag").setValue("USEDTABLE");
                }
            }
        }

        progressDialog.dismiss();
        Toast.makeText(context, "변경사항이 저장되었습니다.", Toast.LENGTH_SHORT).show();
        ((Activity)context).finish();
    }


}







