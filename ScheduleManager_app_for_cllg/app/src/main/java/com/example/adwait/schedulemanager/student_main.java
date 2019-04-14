package com.example.adwait.schedulemanager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;

public class student_main extends Activity {

    RadioButton t1,t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_main);

        t1 = (RadioButton)findViewById(R.id.te1);
        t2 = (RadioButton)findViewById(R.id.te2);

        t1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Database.class_selected="TE1";
                Database.year_selected="TE";
                Intent intent = new Intent(getApplicationContext(),View_Timetable.class);
                startActivity(intent);
                t1.setChecked(false);
            }
        });

        t2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Database.class_selected="TE2";
                Database.year_selected="TE";
                Intent intent = new Intent(getApplicationContext(),View_Timetable.class);
                startActivity(intent);
                t2.setChecked(false);
            }
        });

    }
}
