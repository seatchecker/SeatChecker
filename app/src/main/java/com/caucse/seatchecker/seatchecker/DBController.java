package com.caucse.seatchecker.seatchecker;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

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
import java.util.List;
import java.util.Objects;

import static android.support.constraint.Constraints.TAG;


class DBController  {

    private final int SEND_INFORMATION = 1;
    private ProgressDialog p;
    private ArrayList<Cafe> cafes;
    private String databaseHash ;
    private String inputPassword;


    View view;

    DBController(View view) {

        cafes = new ArrayList<>();
        this.view = view;
    }


    /*
    void getCafeInfo() {
        final ProgressDialog progressDialog = new ProgressDialog(view.getContext());
        progressDialog.show();

        final FirebaseStorage fs = FirebaseStorage.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference cafe = db.collection("cafeinfo");
        cafe.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult())) {
                        String add_dong = Objects.requireNonNull(documentSnapshot.getData()).get("dong").toString();
                        String add_gu = documentSnapshot.getData().get("gu").toString();
                        String location = documentSnapshot.getData().get("location").toString();
                        int floor = Integer.parseInt(documentSnapshot.getData().get("floor").toString());
                        String name = documentSnapshot.getData().get("name").toString();
                        int numOfTables = Integer.parseInt(documentSnapshot.getData().get("tablenum").toString());
                        String hash = documentSnapshot.getData().get("hash").toString();

                        String url = documentSnapshot.getId()+".jpg";
                        Cafe cafe = new Cafe(add_dong, add_gu, location, floor, name, numOfTables,hash);
                        cafe.setImageURL(url);
                        cafes.add(cafe);
                    }

                    new Viewer(view, cafes);
                    progressDialog.dismiss();

                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });


    }

    */


    void getCafeInfo(){
        final ProgressDialog progressDialog = new ProgressDialog(view.getContext());
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

                    new Viewer(view, cafes);
                    progressDialog.dismiss();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }


}

