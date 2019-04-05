package com.caucse.seatchecker.seatchecker;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Cafe {
    private String dong;
    private String gu;
    private String location;
    private int floor;
    private String name;
    private int numOfTables;
    private StorageReference imageRef;
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

    public StorageReference getStorage(){
        return this.imageRef;
    }

    void setStorageRef() {
        setStorageRef();
    }

    void setStorageRef(StorageReference r){
        this.imageRef =r;
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
}
