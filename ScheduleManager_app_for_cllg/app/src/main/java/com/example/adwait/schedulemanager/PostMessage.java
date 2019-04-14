package com.example.adwait.schedulemanager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.collect.BinaryTreeTraverser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class PostMessage extends Activity {
    Spinner spinner;
    EditText message;
    Button Post,Camera;
    EditText image_name;
    StorageReference storageReference;
    Uri uri=null;
    Uri uri1=null;
    ProgressDialog progressDialog;
    private static final int GALLERY_INTENT = 2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postmessage);
        message = (EditText)findViewById(R.id.yourmsg);
        Post = (Button)findViewById(R.id.post);
        Camera = (Button)findViewById(R.id.camera);
        spinner = (Spinner)findViewById(R.id.select_class);
        image_name = (EditText)findViewById(R.id.image_name);
        String recipient[] = {"TE1","TE2","Common"};
        ArrayAdapter<String> a = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,recipient);
        a.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(a);
        storageReference = FirebaseStorage.getInstance().getReference();
        progressDialog = new ProgressDialog(this);
        Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:MM:SS");
                SimpleDateFormat inPost = new SimpleDateFormat("dd/MM/yyyy");
                String now = df.format(calendar.getTime()).toString();
                String postDate = inPost.format(calendar.getTime());

                String sendto = spinner.getSelectedItem().toString();
                String imageURI = "none";

                switch(sendto)
                {
                    case "Common":
                        HashMap<String,String> msg1 = new HashMap<String,String>();
                        msg1.put("Title",Database.LoggedInName+" - "+postDate);
                        msg1.put("Message",message.getText().toString());
                        msg1.put("Time",now);
                        if(image_name.getText().toString().length()==0)
                        {
                            msg1.put("Image","none");
                            Database.db.collection("TE1Notice").document(now).set(msg1);
                            Database.db.collection("TE2Notice").document(now).set(msg1);
                            Toast.makeText(PostMessage.this, "Posted", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            msg1.put("Image",image_name.getText().toString());
                            Database.db.collection("TE1Notice").document(now).set(msg1);
                            Database.db.collection("TE2Notice").document(now).set(msg1);
                            Toast.makeText(PostMessage.this, "Posted", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    default:
                        Toast.makeText(getApplicationContext(),now,Toast.LENGTH_SHORT).show();
                        HashMap<String,String> msg = new HashMap<String,String>();
                        msg.put("Title",Database.LoggedInName+" - "+postDate);
                        msg.put("Message",message.getText().toString());
                        msg.put("Time",now);
                        if(image_name.getText().toString().length()==0)
                        {
                            msg.put("Image","none");
                            Database.db.collection(sendto +"Notice").document(now).set(msg);
                            Toast.makeText(PostMessage.this, "Posted", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            msg.put("Image",image_name.getText().toString());
                            Database.db.collection(sendto + "Notice").document(now).set(msg);
                            Toast.makeText(PostMessage.this, "Posted", Toast.LENGTH_SHORT).show();
                        }
                }

            }
        });

        Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");

                startActivityForResult(intent,GALLERY_INTENT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_INTENT && resultCode == RESULT_OK)
        {
            progressDialog.setMessage("Uploading image");
            progressDialog.show();
          uri = data.getData();
            final StorageReference filepath = storageReference.child(image_name.getText().toString());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Image uploaded",Toast.LENGTH_SHORT).show();


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Error while uploading image",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
