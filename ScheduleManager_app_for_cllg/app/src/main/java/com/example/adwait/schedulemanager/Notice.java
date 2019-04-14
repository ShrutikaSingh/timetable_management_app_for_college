package com.example.adwait.schedulemanager;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Notice extends Activity {
    MessageAdaptor adaptor;
    RecyclerView recyclerView;
    DocumentReference myRef;
    List <Message> msgs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice);
        msgs = new ArrayList<Message>();
        adaptor = new MessageAdaptor(this,msgs);
        recyclerView = (RecyclerView)findViewById(R.id.notice_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CollectionReference myCol;

        myCol = Database.db.collection(Database.class_selected+"Notice");
        myCol.orderBy("Time",Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot document1: task.getResult())
                    {
                        Message sample = new Message(document1.get("Message").toString(),document1.get("Title").toString(),document1.get("Image").toString());
                        msgs.add(sample);
                    }
                    recyclerView.setAdapter(adaptor);
                }

            }
        });



    }
}
