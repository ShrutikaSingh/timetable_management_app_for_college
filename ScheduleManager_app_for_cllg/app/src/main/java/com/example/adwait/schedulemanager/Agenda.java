package com.example.adwait.schedulemanager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Agenda extends Activity {
    Spinner spinner;
    Button post, pick_date;
    EditText agenda;
    String date;
    TextView selected_date;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agenda);
        spinner = (Spinner)findViewById(R.id.class_select);
        post = (Button)findViewById(R.id.post1);
        pick_date = (Button)findViewById(R.id.pickdate);
        agenda = (EditText)findViewById(R.id.agendamsg);
        selected_date = (TextView)findViewById(R.id.selected_date);
        String recipient[] = {"TE1","TE2"};
        ArrayAdapter<String> a = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,recipient);
        a.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(a);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        selected_date.setText(df.format(Calendar.getInstance().getTime()).toString());
        pick_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Agenda.this,
                        android.R.style.Theme_Black,
                        dateSetListener,
                        year, month ,day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Integer y,m,d;
                month+=1;
                y = new Integer(year); m = new Integer(month); d = new Integer(day);
                date = d.toString()+"/"+m.toString()+"/"+y.toString();
                selected_date.setText(date);
            }
        };

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Database.class_selected = spinner.getSelectedItem().toString();
                HashMap<String,String> agd = new HashMap<String,String>();
                agd.put("Agenda",agenda.getText().toString());
                agd.put("Date",date);
                agd.put("By",Database.LoggedInName);
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:MM:SS");
                String now = df.format(Calendar.getInstance().getTime()).toString();
                Database.db.collection(Database.class_selected+"Agenda").document(now).set(agd);
                Toast.makeText(getApplicationContext(),"Agenda posted",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
