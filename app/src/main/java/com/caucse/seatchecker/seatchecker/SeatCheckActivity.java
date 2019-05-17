package com.caucse.seatchecker.seatchecker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SeatCheckActivity extends AppCompatActivity implements GridAdapter.GridItemListener{

    private Controller controller;
    private TextView ivCafeName;
    private ArrayList<TableInfo> tableInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().from(this).inflate(R.layout.activity_seat_check, null);
        setContentView(view);

        Intent intent = getIntent();
        final Cafe cafe = (Cafe) intent.getSerializableExtra("CAFE");
        tableInfoList = (ArrayList<TableInfo>) intent.getSerializableExtra("TABLE");
        //get information of tables

        ivCafeName = findViewById(R.id.tvNameOfCafe);
        ivCafeName.setText(cafe.getName());

        controller = new Controller(this, cafe);
        controller.initTableGridView(this, tableInfoList);
        controller.setPlugInformation(tableInfoList);


        //get data from firebase(real time)
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference firebaseReference = firebaseDatabase.getReference().child(cafe.getDid());


        firebaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Toast.makeText(SeatCheckActivity.this, "좌석 정보가 변경되었습니다. 좌석을 확인해주세요", Toast.LENGTH_SHORT).show();
                for (TableInfo tableInfo : tableInfoList) {
                    String key = dataSnapshot.getKey();
                    if (tableInfo.getTableName().equals(key)) {
                        int pos1 = Integer.parseInt(tableInfo.getPosition().get("first").toString());
                        int pos2 = Integer.parseInt(tableInfo.getPosition().get("second").toString());

                        SeatStatus seat = dataSnapshot.getValue(SeatStatus.class);
                        if (seat.getTag().equals("empty")) {
                            controller.updateStatusOfSeats(pos1, pos2, true);
                        } else {
                            controller.updateStatusOfSeats(pos1, pos2, false);
                        }
                        ;
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /*

        firebaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(SeatCheckActivity.this, "좌석변동발생!!!", Toast.LENGTH_SHORT).show();
                for(TableInfo tableInfo : tableInfoList){
                    String key = dataSnapshot.getKey();
                    if(tableInfo.getTableName().equals(key)){
                        int pos1 = Integer.parseInt(tableInfo.getPosition().get("first").toString());
                        int pos2 = Integer.parseInt(tableInfo.getPosition().get("second").toString());

                        SeatStatus seat = dataSnapshot.getValue(SeatStatus.class);
                        if(seat.getTag().equals("empty")){
                            controller.updateStatusOfSeats(pos1, pos2, true);
                        }else{
                            controller.updateStatusOfSeats(pos1,pos2,false);
                        }
;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


*/
    }
    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onItemDrag(int startPosition, int endPosition) {

    }
}
