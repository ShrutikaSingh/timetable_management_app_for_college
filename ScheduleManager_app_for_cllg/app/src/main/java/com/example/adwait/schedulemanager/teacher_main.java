package com.example.adwait.schedulemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class teacher_main extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_main);
        final ImageButton agenda,notice;
        agenda = (ImageButton)findViewById(R.id.agenda);
        notice = (ImageButton)findViewById(R.id.notice);

        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),PostMessage.class);
                startActivity(intent);

            }
        });

        agenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(),Agenda.class);
                startActivity(intent1);
            }
        });

    }
}
