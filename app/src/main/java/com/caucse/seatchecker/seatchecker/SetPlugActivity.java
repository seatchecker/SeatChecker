package com.caucse.seatchecker.seatchecker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class SetPlugActivity extends AppCompatActivity implements GridAdapter.GridItemListener {


    private Controller controller;
    ArrayList<TableInfo> tables;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_plug);

        Intent intent = getIntent();
        Cafe cafe = (Cafe)intent.getSerializableExtra("CAFE");
        tables = (ArrayList<TableInfo>)intent.getSerializableExtra("TABLES");

        controller = new Controller(this, cafe);
        controller.initTablePlugView(this,tables);


    }

    @Override
    public void onItemClick(View view, int position) {
        controller.addPlug(position);
    }

    @Override
    public void onItemDrag(int startPosition, int endPosition) {

    }
}
