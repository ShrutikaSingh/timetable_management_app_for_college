package com.example.adwait.schedulemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class View_Timetable extends Activity {
    NavigationView nav;
    DayAdaptor adaptor;
    RecyclerView recyclerView;
    List <CardData> days;
    DocumentReference myRef, myRef1;
    String daySelected;
    HashMap<String,String>staff;
    DrawerLayout drawerLayout;
    Button mon,tues,wed,thurs,fri;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_timetable);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mon = (Button)findViewById(R.id.mon);
        tues = (Button)findViewById(R.id.tues);
        wed = (Button)findViewById(R.id.wed);
        thurs = (Button)findViewById(R.id.thurs);
        fri = (Button)findViewById(R.id.fri);
        nav = (NavigationView)findViewById(R.id.student_navigation);
        nav.bringToFront();
        nav.requestLayout();
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int ID = item.getItemId();
                if(ID == R.id.view_tt)
                {
                    Toast.makeText(getApplicationContext(),"Already there",Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawers();
                }
                else if(ID == R.id.holidays)
                {
                    Intent intent = new Intent(getApplicationContext(),Holidays.class);
                    startActivity(intent);
                    drawerLayout.closeDrawers();
                }
                else if(ID == R.id.exam_tt)
                {
                    Intent intent = new Intent(getApplicationContext(),ExamTT.class);
                    startActivity(intent);
                    drawerLayout.closeDrawers();
                }
                else if(ID == R.id.notice_board)
                {
                    Intent intent = new Intent(getApplicationContext(),Notice.class);
                    startActivity(intent);
                    drawerLayout.closeDrawers();
                }
                else if(ID == R.id.view_agenda)
                {
                    Intent intent = new Intent(getApplicationContext(),View_Agenda.class);
                    startActivity(intent);
                    drawerLayout.closeDrawers();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No match",Toast.LENGTH_SHORT).show();
                    return false;
                }
                return true;
            }
        });
        days = new ArrayList<CardData>();
        recyclerView = (RecyclerView)findViewById(R.id.recyler_view);
        recyclerView.setHasFixedSize(true);
        adaptor = new DayAdaptor(this,days);
        staff =  new HashMap<String,String>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daySelected="Monday";
                getTimetable();

            }
        });
        tues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daySelected="Tuesday";
                getTimetable();
            }
        });
        wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daySelected="Wednesday";
                getTimetable();
            }
        });
        thurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daySelected="Thrusday";
                getTimetable();
            }
        });
        fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daySelected="Friday";
                getTimetable();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        getToday();
    }

    public void addData(CardData a)
    {
        days.add(a);
    }

    public  void getToday()
    {
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DAY_OF_WEEK);

        switch (today)
        {
            case Calendar.MONDAY:
                mon.performClick();
                break;
            case Calendar.TUESDAY:
                tues.performClick();
                break;
            case Calendar.WEDNESDAY:
                wed.performClick();
                break;
            case Calendar.THURSDAY:
                thurs.performClick();
                break;
            case Calendar.FRIDAY:
                fri.performClick();
                break;
        }
    }

    public void getTimetable()
    {
        days.clear();
        myRef = Database.db.collection(Database.class_selected).document(daySelected);
        myRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    int i;
                    String s;
                    String t;
                    final StringBuilder ss;
                    final StringBuilder tfac;
                    final StringBuilder tslot;
                    for(i=1;i<6;i++)
                    {

                        s="S"+i;
                        t="T"+i;
                        final String subject = documentSnapshot.get(s).toString();
                        CardData temp=new CardData(documentSnapshot.get(t).toString(),subject,"");
                        addData(temp);

                    }

                    recyclerView.setAdapter(adaptor);

                }
            }
        });

    }

}
