package com.caucse.seatchecker.seatchecker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChangeStatusActivity extends AppCompatActivity implements GridAdapter.GridItemListener{



    private Controller controller;
    private Button btnSaveChange;
    private TextView ivCafeName;
    private ArrayList<TableInfo> tableInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_status);

        Intent intent = getIntent();
        final Cafe cafe = (Cafe) intent.getSerializableExtra("CAFE");
        tableInfoList = (ArrayList<TableInfo>) intent.getSerializableExtra("TABLE");
        //get information of tables

        ivCafeName = findViewById(R.id.tvNameOfCafe);
        btnSaveChange = findViewById(R.id.btnSaveChange);
        ivCafeName.setText(cafe.getName());

        controller = new Controller(this, cafe);
        controller.initTableGridView(this, tableInfoList);


        btnSaveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo : save changes to FIREBASE(real time)
                controller.SaveChangedStatusToDB(tableInfoList);

            }
        });
    }

    @Override
    public synchronized void onItemClick(View view, int position) {

        //Toast.makeText(this, position+"눌림", Toast.LENGTH_SHORT).show();
        for(TableInfo table : tableInfoList){
            int pos1 = Integer.parseInt(table.getPosition().get("first").toString());
            int pos2 = Integer.parseInt(table.getPosition().get("second").toString());

            if(pos1 == position || pos2 == position){
                controller.inverseStatus(pos1,pos2);
                break;
            }
        }
    }

    @Override
    public void onItemDrag(int startPosition, int endPosition) {

    }
}
