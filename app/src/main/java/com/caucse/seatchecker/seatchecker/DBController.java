package com.caucse.seatchecker.seatchecker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.support.constraint.Constraints.TAG;


class DBController  {

    private final int SEND_INFORMATION = 1;
    private ProgressDialog p;
    private ArrayList<Cafe> cafes;
    private ArrayList<TableInfo> tables;
    private String databaseHash ;
    private String inputPassword;
    static final int MODE_MOVE_TABLE = 2;
    static final int MODE_CHECK_TABLE = 1;
    static final int MODE_CHANGE_STATUS = 3;


    View view;

    DBController(View view) {
        cafes = new ArrayList<>();
        tables = new ArrayList<>();
        this.view = view;
    }


    void getCafeInfo(){
        final ProgressDialog progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("카페의 정보를 불러오는중입니다. 잠시만 기다려주세요");
        progressDialog.show();

        final FirebaseStorage fs = FirebaseStorage.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference cafe = db.collection("cafeinfo");



        cafe.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if (queryDocumentSnapshots.isEmpty()) {
                    Log.d(TAG, "onSuccess: LIST EMPTY");
                } else {
                    List<Cafe> types = queryDocumentSnapshots.toObjects(Cafe.class);
                    cafes.addAll(types);
                    Log.d(TAG, "onSuccess: " + cafes);

                    Viewer list = new Viewer(view);
                    list.CafeListViewer(cafes);

                    progressDialog.dismiss();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    void getTableInfo(final Cafe cafe,final int mode){
        final ProgressDialog progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("좌석 정보를 가져오는 중입니다...");
        progressDialog.show();

        final FirebaseStorage fs = FirebaseStorage.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference talbes = db.collection("cafeinfo").document(cafe.getDid()).collection("table");

        talbes.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()) {
                    Log.d(TAG, "onSuccess: LIST EMPTY");
                } else {
                    List<TableInfo> types = queryDocumentSnapshots.toObjects(TableInfo.class);
                    tables.addAll(types);
                    Log.d(TAG, "onSuccess: " + cafes);


                    switch(mode){
                        case MODE_MOVE_TABLE :
                            Intent intent = new Intent(view.getContext(),MoveTableLocationActivity.class);
                            intent.putExtra("CAFE",cafe);
                            intent.putExtra("TABLE",tables);
                            view.getContext().startActivity(intent);
                            break;
                        case MODE_CHANGE_STATUS :
                            getSeatStatus(cafe,MODE_CHANGE_STATUS);
                            break;
                        case MODE_CHECK_TABLE :
                            getSeatStatus(cafe,MODE_CHECK_TABLE);
                            break;
                    }
                    progressDialog.dismiss();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    void saveChangedDataToFireStore(Cafe cafe, ArrayList<TableInfo> tables){
        final ProgressDialog progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("변경 사항을 저장하는 중입니다. 잠시만 기다려주세요");
        progressDialog.show();


        final FirebaseStorage fs = FirebaseStorage.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference dbTables = db.collection("cafeinfo").document(cafe.getDid()).collection("table");

        for(TableInfo table : tables){
            dbTables.document(table.getTableName()).set(table);
        }

        progressDialog.dismiss();

        Context context = view.getContext();
        ((Activity)context).finish();
        Toast.makeText(view.getContext(), "수정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
    }

    void getSeatStatus(final Cafe cafe,final int mode){

        final ProgressDialog progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("좌석 상태 정보를 가져오는 중입니다...");
        progressDialog.show();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(cafe.getDid());

       reference.addListenerForSingleValueEvent(new ValueEventListener() {

           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                  SeatStatus seat = snapshot.getValue(SeatStatus.class);
                  String key = snapshot.getKey();
                  assert seat != null;
                  for(TableInfo table : tables){
                      if(table.getTableName().equals(key)){
                          table.setTag( seat.getTag());
                          break;
                      }
                  }
              }
               Intent myIntent = null;
              if(mode == MODE_CHECK_TABLE){
                  myIntent = new Intent(view.getContext(),SeatCheckActivity.class);
              }else if(mode == MODE_CHANGE_STATUS){
                  myIntent = new Intent(view.getContext(),ChangeStatusActivity.class);
              }
              if(myIntent != null){
                  myIntent.putExtra("CAFE",cafe);
                  myIntent.putExtra("TABLE",tables);
                  view.getContext().startActivity(myIntent);
              }


              progressDialog.dismiss();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
    }

    void removeAlarmSetting(String cafeName, String deviceID){
        //remove firebase data
        FirebaseDatabase.getInstance().getReference().child(cafeName).child("push").child(deviceID).removeValue();

        //todo : remove firestore data
    }

    void addAlarmSetting(String cafeName, String deviceID, int tableNum, boolean isplug){

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(cafeName).child("push").child(deviceID);
        pushInformation my =new pushInformation(FirebaseInstanceId.getInstance().getToken(),tableNum,isplug);
        reference.setValue(my);
        //todo : save at realm database
    }
}

