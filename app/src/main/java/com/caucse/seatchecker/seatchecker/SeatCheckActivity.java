package com.caucse.seatchecker.seatchecker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SeatCheckActivity extends AppCompatActivity {

    private Button refresh;
    private TextView ivCafeName;
    private List<TableInfo> tableInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().from(this).inflate(R.layout.activity_seat_check,null);
        setContentView(view);

        Intent intent = getIntent();
        final Cafe cafe = (Cafe)intent.getSerializableExtra("CAFE");
        tableInfoList = (List<TableInfo>)intent.getSerializableExtra("TABLE");    //get information of tables

        refresh = findViewById(R.id.btnRefresh);
        ivCafeName = findViewById(R.id.tvNameOfCafe);
        ivCafeName.setText(cafe.getName());

        //set grid view
        DBController dbController = new DBController(view);
        dbController.getSeatStatus(cafe);



        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo :refresh the information of seats
            }
        });
    }
}
