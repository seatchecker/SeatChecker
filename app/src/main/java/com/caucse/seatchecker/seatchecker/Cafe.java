package com.caucse.seatchecker.seatchecker;


import java.io.Serializable;

public class Cafe implements Serializable {
    private String dong;
    private String gu;
    private String location;
    private int floor;
    private String name;
    private int numOfTables;
    private String imageURL;

    Cafe(String dong, String gu){
        this.dong = dong;
        this.gu = gu;
    }
    Cafe(String dong, String gu, String add, int floor, String name, int table){
        this.dong = dong;
        this.gu = gu;
        this.location = add;
        this.floor = floor;
        this.name = name;
        this.numOfTables = table;
    }


    String getAddress_gu(){
        return gu;
    }
    String getAddress_dong(){
        return dong;
    }
    public void putAddress_dong(String dong){
        this.dong = dong;
    }


    public String getName(){
        return this.name;
    }

    String getImageURL(){
        return this.imageURL;
    }
    void setImageURL(String url){
        this.imageURL = url;
    }
}
