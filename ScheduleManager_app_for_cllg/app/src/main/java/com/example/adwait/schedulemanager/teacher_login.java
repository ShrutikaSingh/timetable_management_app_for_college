package com.example.adwait.schedulemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class teacher_login extends Activity {

    EditText uname, passd;
    Button login, signup;
    DocumentReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_login);

        uname = (EditText)findViewById(R.id.email);
        passd = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);
        signup = (Button)findViewById(R.id.signup);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user, pass;

                user = uname.getText().toString();
                pass = passd.getText().toString();

                myRef = Database.db.collection("Login_Details").document(user);

                myRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists())
                        {
                            if(documentSnapshot.get("Password").toString().equals(pass))
                            {
                                Database.LoggedInName=documentSnapshot.get("Name").toString();
                                Intent intent = new Intent(getApplicationContext(),teacher_main.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Invalid password",Toast.LENGTH_SHORT).show();
                                passd.setText("");
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"email doesn't exist",Toast.LENGTH_SHORT).show();
                            uname.setText("");
                            passd.setText("");
                        }
                    }
                });

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),teacher_signup.class);
                startActivity(intent);
            }
        });
    }
}
