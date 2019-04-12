package com.caucse.seatchecker.seatchecker;

import android.media.MediaCas;

public class TableInfo {

    final static int TWOTABLE = 100;
    final static int FOURTABLE = 200;
    final static int DOOR = 150;
    final static int COUNTER = 170;
    final static int PLUG = 300;
    final static int NON_PLUG = 400;

    private String tableName;
    private boolean plug;
    private int kindTable;

    public int getTWOTABLE() {
        return TWOTABLE;
    }

    public int getFOURTABLE() {
        return FOURTABLE;
    }

    public int getDOOR() {
        return DOOR;
    }

    public int getCOUNTER() {
        return COUNTER;
    }

    public int getPLUG() {
        return PLUG;
    }

    public int getNON_PLUG() {
        return NON_PLUG;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public boolean isPlug() {
        return plug;
    }

    public void setPlug(boolean plug) {
        this.plug = plug;
    }

    int getKindTable() {
        return kindTable;
    }

    void setKindTable(int kindTable) {
        this.kindTable = kindTable;
    }
}
