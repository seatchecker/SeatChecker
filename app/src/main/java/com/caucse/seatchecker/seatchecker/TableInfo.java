package com.caucse.seatchecker.seatchecker;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TableInfo implements Serializable {

    final static int NONE = -1;
    final static int TWOTABLE = 100;
    final static int FOURTABLE = 200;
    final static int DOOR = 150;
    final static int COUNTER = 170;

    private String tableName="";
    private Map<String, Object> position = new HashMap<>();
    private boolean plug =false;


    TableInfo(){
        position.put("first",0);
        position.put("second",0);
    }

    String getTableName() {
        return tableName;
    }

    Map<String, Object> getPosition() {
        return position;
    }

    public boolean isPlug() {
        return plug;
    }

    void setPlug(boolean plug) {
        this.plug = plug;
    }

    void setPosition(int pos1, int pos2){
        position.put("first",pos1);
        position.put("second",pos2);
    }
}

class GridElement {
    private int status;
    private boolean plug;
    private String name;


    GridElement(){
        this.status = TableInfo.NONE;
        this.plug = false;
        this.name = null;
    }
    int getStatus() {
        return status;
    }

    void setStatus(int status) {
        this.status = status;
    }
    public void setName(String name){this.name = name;}
    public String getName(){return this.name;}


    boolean isPlug() {
        return plug;
    }

    void setPlug(boolean plug) {
        this.plug = plug;
    }

}
