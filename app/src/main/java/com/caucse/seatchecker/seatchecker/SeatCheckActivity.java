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

    private Button btnAlarmSet;
    private Controller controller;
    private TextView ivCafeName;
    private ArrayList<TableInfo> tableInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = getLayoutInflater().from(this).inflate(R.layout.activity_seat_check, null);
        setContentView(view);

        final String androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        Intent intent = getIntent();
        final Cafe cafe = (Cafe) intent.getSerializableExtra("CAFE");
        tableInfoList = (ArrayList<TableInfo>) intent.getSerializableExtra("TABLE");
        //get information of tables


        btnAlarmSet = findViewById(R.id.btnSetAlarm);
        ivCafeName = findViewById(R.id.tvNameOfCafe);
        ivCafeName.setText(cafe.getName());

        DBController dbController = new DBController(btnAlarmSet.getRootView());
        dbController.isThisCafeSetAlarm(cafe.getDid(),androidId,btnAlarmSet);
        controller = new Controller(this, cafe);
        controller.initTableGridView(this, tableInfoList);
        controller.setPlugInformation(tableInfoList);

        //todo : get ralm data . if alarm was set, then change th btnAlarmSet to alarm_on.jpg

        btnAlarmSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = (String)btnAlarmSet.getTag();
                if(tag.equals("alarm_off.jpg")){

                    CustomDialog alarmdialog = new CustomDialog(v);
                    alarmdialog.callAlarmSettingDialog(cafe, androidId, btnAlarmSet);
                }else{
                    btnAlarmSet.setBackground(view.getResources().getDrawable(R.drawable.alarm_off));
                    btnAlarmSet.setTag("alarm_off.jpg");
                    DBController dbController = new DBController(v);
                    dbController.removeAlarmSetting(cafe.getDid());
                    Toast.makeText(SeatCheckActivity.this, "알림을 해제하였습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //get data from firebase(real time)
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference firebaseReference = firebaseDatabase.getReference().child(cafe.getDid()).child("tableList");


        firebaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for (TableInfo tableInfo : tableInfoList) {
                    String key = dataSnapshot.getKey();
                    if (tableInfo.getTableName().equals(key)) {
                        int pos1 = Integer.parseInt(tableInfo.getPosition().get("first").toString());
                        int pos2 = Integer.parseInt(tableInfo.getPosition().get("second").toString());

                        SeatStatus seat = dataSnapshot.getValue(SeatStatus.class);
                        assert seat != null;
                        if (seat.getTag().equals("empty")) {
                            controller.updateStatusOfSeats(pos1, pos2, true);
                        } else {
                            controller.updateStatusOfSeats(pos1, pos2, false);
                        }

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
