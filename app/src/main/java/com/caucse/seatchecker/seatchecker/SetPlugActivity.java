package com.caucse.seatchecker.seatchecker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class SetPlugActivity extends AppCompatActivity implements GridAdapter.GridItemListener {

    private Button btnSaveChange;
    private Controller controller;
    ArrayList<TableInfo> tables;
    private DBController dbController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_plug);


        btnSaveChange = findViewById(R.id.btnSaveChange);
        Intent intent = getIntent();
        final Cafe cafe = (Cafe)intent.getSerializableExtra("CAFE");
        tables = (ArrayList<TableInfo>)intent.getSerializableExtra("TABLES");

        controller = new Controller(this, cafe);
        controller.initTablePlugView(this,tables);

        btnSaveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbController = new DBController(v);
                dbController.saveChangedDataToFireStore(cafe,tables);
            }
        });

    }

    @Override
    public void onItemClick(View view, int position) {
        controller.addPlug(position);
    }

    @Override
    public void onItemDrag(int startPosition, int endPosition) {

    }
}
