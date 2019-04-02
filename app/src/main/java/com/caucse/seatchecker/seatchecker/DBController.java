package com.caucse.seatchecker.seatchecker;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Objects;

import static android.content.ContentValues.TAG;


class DBController  {

    private ProgressDialog p;
    private ArrayList<Cafe> cafes;

    View view;

    DBController(View view) {

        cafes = new ArrayList<>();
        this.view = view;
    }

    void getCafeInfo() {
        final ProgressDialog progressDialog = new ProgressDialog(view.getContext());
        progressDialog.show();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference cafe = db.collection("cafeinfo");
        cafe.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult())) {
                        /*********add cafe info to arrayList*****************/
                        String add_dong = Objects.requireNonNull(documentSnapshot.getData()).get("dong").toString();
                        String add_gu = documentSnapshot.getData().get("gu").toString();
                        String location = documentSnapshot.getData().get("location").toString();
                        int floor = Integer.parseInt(documentSnapshot.getData().get("floor").toString());
                        String name = documentSnapshot.getData().get("name").toString();
                        int numOfTables = Integer.parseInt(documentSnapshot.getData().get("tablenum").toString());

                        Cafe cafe = new Cafe(add_dong, add_gu, location, floor, name, numOfTables);
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


    boolean checkManagerPassword(String cafeName, String password){
        //todo
        return true;
    }
}
