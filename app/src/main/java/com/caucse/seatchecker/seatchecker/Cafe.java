package com.caucse.seatchecker.seatchecker;



import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Cafe implements Serializable {

    private Map<String, Object> counter = new HashMap<>();
    private Map<String,Object> door = new HashMap<>();
    private Map<String, Object> grid = new HashMap<>();

    private String dong = "";

    private int floor = 0;
    private String gu = "";
    private String hash = "";
    private String location = "";
    private String name = "";
    private int tablenum = 0;
    private String imageURL = "";


    Cafe() {
        counter.put("first",0);
        counter.put("second",0);

        door.put("first",0);
        door.put("second",0);

        grid.put("length",0);
        grid.put("width",0);
    }

    Cafe(String dong, String gu) {
        this.dong = dong;
        this.gu = gu;
    }

    Cafe(String dong, String gu, String add, int floor, String name, int table, String hash) {
        this.dong = dong;
        this.gu = gu;
        this.location = add;
        this.floor = floor;
        this.name = name;
        this.tablenum = table;
        this.hash = hash;
    }


    String getAddress_gu() {
        return gu;
    }

    String getAddress_dong() {
        return dong;
    }

    public void putAddress_dong(String dong) {
        this.dong = dong;
    }


    public String getName() {
        return this.name;
    }

    String getImageURL() {
        return this.imageURL;
    }

    public Map<String, Object> getCounter() {
        return counter;
    }

    public String getDong() {
        return dong;
    }

    public int getFloor() {
        return floor;
    }

    public String getGu() {
        return gu;
    }

    String getHash() {
        return hash;
    }

    public String getLocation() {
        return location;
    }

    public int getTablenum() {
        return tablenum;
    }

    public Map<String, Object> getDoor() {
        return door;
    }

    public Map<String, Object> getGrid() {
        return grid;
    }
}
