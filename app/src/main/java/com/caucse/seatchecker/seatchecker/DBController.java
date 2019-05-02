package com.caucse.seatchecker.seatchecker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
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

    void getTableInfo(final Cafe cafe){
        final ProgressDialog progressDialog = new ProgressDialog(view.getContext());
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
                    progressDialog.dismiss();


                    Intent intent = new Intent(view.getContext(),MoveTableLocationActivity.class);
                    intent.putExtra("CAFE",cafe);
                    intent.putExtra("TABLE",tables);
                    view.getContext().startActivity(intent);
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
}

