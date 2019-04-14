package com.example.adwait.schedulemanager;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class View_Agenda extends Activity {
    DayAdaptor adaptor;
    RecyclerView recyclerView;
    List <CardData> days;
    DocumentReference myRef, myRef1;
    CollectionReference colRef;
    CardData sample;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_agenda);
        recyclerView = (RecyclerView)findViewById(R.id.agenda_list);
        days = new ArrayList<CardData>();
        adaptor = new DayAdaptor(this,days);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        colRef = Database.db.collection(Database.class_selected+"Agenda");
        colRef.orderBy("Date",Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot doc : task.getResult())
                    {
                        sample = new CardData(doc.get("Date").toString(),doc.get("Agenda").toString(),doc.get("By").toString());
                        days.add(sample);
                    }
                    recyclerView.setAdapter(adaptor);
                }
            }
        });


    }
}
