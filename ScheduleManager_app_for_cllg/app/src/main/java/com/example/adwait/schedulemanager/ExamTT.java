package com.example.adwait.schedulemanager;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExamTT extends Activity {

    DayAdaptor adaptor;
    RecyclerView recyclerView;
    List<CardData> days;
    DocumentReference myRef, myRef1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.examtt);

        days = new ArrayList<CardData>();
        recyclerView = (RecyclerView)findViewById(R.id.recyler_view);
        recyclerView.setHasFixedSize(true);
        adaptor = new DayAdaptor(this,days);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        myRef = Database.db.collection("Exam TT").document(Database.year_selected);
        myRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    int i;
                    String p;
                    String d;
                    String etime = documentSnapshot.get("Time").toString();
                    for(i=1;i<6;i++)
                    {

                        p="P"+i;
                        d="D"+i;
                        final String paper=new String(documentSnapshot.getString(p).toString());
                        final String date = documentSnapshot.get(d).toString();
                        CardData temp=new CardData(date,paper,etime);
                        addData(temp);

                    }

                    recyclerView.setAdapter(adaptor);

                }
            }
        });


    }
    public void addData(CardData a)
    {
        days.add(a);
    }


}

