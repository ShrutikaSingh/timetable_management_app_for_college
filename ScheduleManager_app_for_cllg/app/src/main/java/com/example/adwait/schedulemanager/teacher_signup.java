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

import java.util.HashMap;

public class teacher_signup extends Activity {

    EditText uname,pass,vid,conpass;
    Button signup,validate;
    DocumentReference myRef;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_signup);

        uname = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        vid = (EditText) findViewById(R.id.vid);
        conpass = (EditText) findViewById(R.id.conpass);
        signup = (Button) findViewById(R.id.signup);
        validate = (Button) findViewById(R.id.validate);
        pass.setVisibility(View.INVISIBLE);
        conpass.setVisibility(View.INVISIBLE);
        signup.setVisibility(View.INVISIBLE);

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String user,vid1;
                user = uname.getText().toString();
                vid1 = vid.getText().toString();

                myRef = Database.db.collection("Verification").document(user);
                myRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists())
                        {
                            if(documentSnapshot.get("VID").toString().equals(vid1))
                            {
                                validate.setVisibility(View.INVISIBLE);
                                pass.setVisibility(View.VISIBLE);
                                conpass.setVisibility(View.VISIBLE);
                                signup.setVisibility(View.VISIBLE);
                                vid.setEnabled(false);
                                name = documentSnapshot.get("Name").toString();
                            }
                            else
                            {
                                vid.setText("");
                                Toast.makeText(getApplicationContext(),"Invalid Verification ID",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"email does not exist",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password, conpasswd;
                password = pass.getText().toString();
                conpasswd = conpass.getText().toString();

                if(password.equals(conpasswd))
                {
                    HashMap<String,String> user=new HashMap<String,String>();
                    user.put("Password",pass.getText().toString());
                    user.put("Name",name);
                    Database.db.collection("Login_Details").document(uname.getText().toString()).set(user);
                    Intent intent = new Intent(getApplicationContext(),teacher_login.class);
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Passwords do not match",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
