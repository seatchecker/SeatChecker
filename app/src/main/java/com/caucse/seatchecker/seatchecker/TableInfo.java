package com.caucse.seatchecker.seatchecker;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TableInfo implements Serializable {

    final static int NONE = -1;
    final static int TWOTABLE = 2;
    final static int FOURTABLE = 4;
    final static int DOOR = 150;
    final static int COUNTER = 170;
    final static int ONETABLE = 1;

    private String tableName="";
    private Map<String, Object> position = new HashMap<>();
    private boolean plug =false;
    private int capacity = 0;
    private String orientation = "";
    private boolean isEmptySeat;
    private String tag = "";



    TableInfo(){
        tag="empty";
        position.put("first",0);
        position.put("second",0);
        isEmptySeat = true;
    }

    private boolean isEmptySeat(){
        return this.isEmptySeat;
    }

    String getTableName() {
        return tableName;
    }

    String getTag(){
        return tag;
    }
    Map<String, Object> getPosition() {
        return position;
    }

    boolean isPlug() {
        return plug;
    }

    void setPlug(boolean plug) {
        this.plug = plug;
    }

    void setTag(String tag){
        this.tag = tag;
    }
    void setPosition(int pos1, int pos2){
        position.put("first",pos1);
        position.put("second",pos2);
    }
    int getCapacity(){
        return capacity;
    }
    public String getOrientation(){
        return orientation;
    }
    public void setOrientation(String str){
        this.orientation = str;
    }
}

class GridElement {

    private String orientation;
    private int status;
    private boolean isEmptySeat;
    private boolean plug;
    private String name;


    GridElement(){
        this.status = TableInfo.NONE;
        this.plug = false;
        this.name = null;
        this.isEmptySeat = true;
    }

    void setInformation(int status, boolean plug, String name, String orientation){
        this.orientation = orientation;
        this.status = status;
        this.plug = plug;
        this.name = name;
    }

    void changeStatusOfSeat(boolean isEmptySeat){
        this.isEmptySeat = isEmptySeat;
    }
    boolean isEmptySeat(){
        return this.isEmptySeat;
    }
    int getStatus() {
        return status;
    }

    void setStatus(int status) {
        this.status = status;
    }
    public void setName(String name){this.name = name;}
    public String getName(){return this.name;}

    public String getOrientation(){
        return orientation;
    }

    public void setOrientation(String str){
        this.orientation = str;
    }
    boolean isPlug() {
        return plug;
    }

    void setPlug(boolean plug) {
        this.plug = plug;
    }

}

@IgnoreExtraProperties
class SeatStatus{

    private String seatName = "";
    private int tableno = 0;
    private String tag = "";

    //default cons
    SeatStatus(){}

    public String getSeatName() {
        return seatName;
    }
    public int getTableno() {
        return tableno;
    }

    public String getTag() {
        return tag;
    }
    public void setName(String seatName){
        this.seatName = seatName;
    }
}

@IgnoreExtraProperties
class pushInformation{
    String token = "";
    int numOfTable = 0;
    boolean isPlug = false;
    String location ="";

    pushInformation(String token, int numOfTable, boolean isPlug, String location){
        this.token = token;
        this.numOfTable = numOfTable;
        this.isPlug = isPlug;
        this.location = location;
    }
    public String getToken() {
        return token;
    }

    public int getNumOfTable() {
        return numOfTable;
    }

    public boolean isPlug() {
        return isPlug;
    }

    public String getLocation() {
        return location;
    }
}