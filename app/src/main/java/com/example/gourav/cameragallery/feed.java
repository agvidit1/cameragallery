package com.example.gourav.cameragallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class feed extends AppCompatActivity {

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        FirebaseStorage storage=FirebaseStorage.getInstance();
       Intent intent=getIntent();


        String a=intent.getStringExtra("abc");
        Toast.makeText(feed.this,a, Toast.LENGTH_LONG).show();




        StorageReference storageRef = storage.getReference();
        StorageReference spaceRef = storageRef.child(a);

        final long ONE_MEGABYTE = 1024 * 1024;
        spaceRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {

                Bitmap bmp =BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                ImageView imageView=(ImageView)findViewById(R.id.img);
                imageView.setImageBitmap(Bitmap.createScaledBitmap(bmp,imageView.getWidth(),imageView.getHeight(),false));

                // Data for "images/island.jpg" is returns, use this as needed
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

    }
}
