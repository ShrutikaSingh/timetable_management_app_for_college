package com.example.adwait.schedulemanager;
import android.net.Uri;

import com.google.firebase.firestore.FirebaseFirestore;

public class Database {
    static FirebaseFirestore db;
    static String class_selected,year_selected;
    static String LoggedInName, Subject_Taught;
    static String post_image;
    static void init()
    {
        db = FirebaseFirestore.getInstance();
    }
}
