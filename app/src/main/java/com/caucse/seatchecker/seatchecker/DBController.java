package com.caucse.seatchecker.seatchecker;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Objects;


class DBController {

    private ArrayList<Cafe> cafes = null;

    ArrayList<Cafe> getCafeList() {
        cafes = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference cafe = db.collection("cafeinfo");
        cafe.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult())){
                        String add_dong = Objects.requireNonNull(documentSnapshot.getData()).get("dong").toString();
                        String add_gu = documentSnapshot.getData().get("gu").toString();
                        String location = documentSnapshot.getData().get("location").toString();
                        int floor = Integer.parseInt(documentSnapshot.getData().get("floor").toString());
                        String name = documentSnapshot.getData().get("name").toString();
                        int numOfTables = Integer.parseInt(documentSnapshot.getData().get("tablenum").toString());

                        Cafe cafe = new Cafe(add_dong,add_gu);
                        cafes.add(cafe);
                    }
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });
        return cafes;
    }


}
