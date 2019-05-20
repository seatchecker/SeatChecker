package com.caucse.seatchecker.seatchecker;

import io.realm.RealmObject;

public class AlarmRealm extends RealmObject {
    private String cafeName;
    private boolean plug;
    private int tableNum;
    private String cafeDid;

    public AlarmRealm(){

    }

    AlarmRealm(String cafeDid, String name, int tablenum, boolean isPlug){
        this.cafeDid = cafeDid;
        this.cafeName = name;
        this.tableNum = tablenum;
        this.plug = isPlug;


    }
    AlarmRealm(String cafeDid, int tablenum, boolean isPlug){
        this.cafeDid = cafeDid;
        this.tableNum = tablenum;
        this.plug = isPlug;
        this.cafeName = "";

    }
    String getCafeDid() {
        return cafeDid;
    }

    String getCafeName() {
        return cafeName;
    }

    public boolean isPlug() {
        return plug;
    }

    int getTableNum() {
        return tableNum;
    }
}
