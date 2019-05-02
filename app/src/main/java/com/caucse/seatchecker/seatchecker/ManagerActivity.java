package com.caucse.seatchecker.seatchecker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ManagerActivity extends AppCompatActivity {

    private ArrayList<String> manager_list;
    private ListView listview;
    private TextView tvCafeName;
    private ImageView ivChangeStatus, ivChangeSeat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        Intent intent = getIntent();
        final Cafe cafe = (Cafe)intent.getSerializableExtra("CAFE");
        tvCafeName = findViewById(R.id.tvCafeName);
        ivChangeSeat = findViewById(R.id.ivChangePosition);
        ivChangeStatus = findViewById(R.id.ivChangeStatus);

        tvCafeName.setText(cafe.getName());
        manager_list = new ArrayList<>();
        manager_list.add("좌석 상태 관리하기");
        manager_list.add("좌석 위치 변환하기");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,manager_list);

        ivChangeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo: changeStatus
            }
        });

        ivChangeSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog dialog = new CustomDialog(v);
                dialog.callTableInfoResetDialog(cafe);
            }
        });

    }
}
