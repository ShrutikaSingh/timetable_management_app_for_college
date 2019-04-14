package com.example.adwait.schedulemanager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class Holidays extends Activity{
    FirebaseStorage storage = FirebaseStorage.getInstance();
    //StorageReference img = storage.getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/schedule-manager-c8fbe.appspot.com/o/Screenshot%20(19).png?alt=media&token=4802b3b1-f07b-41d2-8363-f5576541f859");
    StorageReference s1 = storage.getReference();
    StorageReference img = s1.child("holidays.png");
    File tempfile;
    String path;
    ImageView imageView;
    PhotoView photoView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.holidays);
        path = Environment.getExternalStorageDirectory().getPath();
        tempfile = new File(path,"holidays.png");

        if(!tempfile.exists())
        {
            try {
                tempfile.createNewFile();
                img.getFile(tempfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        {
                            Toast.makeText(getApplicationContext(),"Download complete",Toast.LENGTH_SHORT).show();
                            Intent intent = getIntent();
                            startActivity(intent);
                        }

                    }
                });
            }
            catch(Exception e)
            {
                Toast.makeText(getApplicationContext(),"Error creating file",Toast.LENGTH_SHORT).show();
            }
        }

        photoView = new PhotoView(this);
        photoView = (PhotoView)findViewById(R.id.photo_view);
        photoView.setImageBitmap(BitmapFactory.decodeFile(tempfile.getPath()));
    }
}
