package com.example.adwait.schedulemanager;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class View_image extends Activity {
    PhotoView photoView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_activity);
        photoView = new PhotoView(this);
        photoView = (PhotoView)findViewById(R.id.photo_view);
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference imagefile = storage.getReference().child(Database.post_image);
        String path = Environment.getExternalStorageDirectory().getPath();
        final File tempfile = new File(path,"temp.jpg");
        try {
            tempfile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        imagefile.getFile(tempfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(),"Image ready",Toast.LENGTH_SHORT).show();
                photoView.setImageBitmap(BitmapFactory.decodeFile(tempfile.getPath()));
            }
        });


    }
}
