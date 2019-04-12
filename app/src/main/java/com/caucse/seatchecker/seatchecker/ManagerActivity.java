package com.caucse.seatchecker.seatchecker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ManagerActivity extends AppCompatActivity {

    private ArrayList<String> manager_list;
    private ListView listview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        Intent intent = getIntent();
        final Cafe cafe = (Cafe)intent.getSerializableExtra("CAFE");
        manager_list = new ArrayList<>();
        manager_list.add("좌석 상태 관리하기");
        manager_list.add("좌석 위치 변환하기");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,manager_list);
        listview = findViewById(R.id.lvManager);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        //todo : send intent
                    case 1:
                        CustomDialog dialog = new CustomDialog(view);
                        dialog.callTableInfoResetDialog(cafe);
                    default: break;
                }
            }
        });

    }
}
