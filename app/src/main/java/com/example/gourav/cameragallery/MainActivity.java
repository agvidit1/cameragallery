package com.example.gourav.cameragallery;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import static android.location.LocationManager.GPS_PROVIDER;

public class MainActivity extends AppCompatActivity {

    protected LocationManager locationManager;
    protected LocationListener locationListener;
    private FloatingActionButton btn2;




    String txtLat;
    double lat;
    double lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





       final TextView txt=(TextView)findViewById(R.id.textView);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                txtLat=(location.getAltitude()+","+location.getLongitude());
                txt.setText(txtLat);
                lat=(location.getLatitude());
                lng=(location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates("gps", 100, 0, locationListener);





        FirebaseStorage storage = FirebaseStorage.getInstance();


        Button btn = (Button) findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);


            }
        });

        btn2 = findViewById(R.id.button2);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(MainActivity.this, MapsActivity2.class);
                startActivity(myintent);
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        ImageView img = (ImageView) findViewById(R.id.imageView);
        img.setImageBitmap(bitmap);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FirebaseStorage storage=FirebaseStorage.getInstance();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("message");
        DatabaseReference myRef = database.getReference();
        //myRef.setValue(2);
        StorageReference mStorage=FirebaseStorage.getInstance().getReference();
        StorageReference mountainsRef = mStorage.child(String.valueOf(lng)+String.valueOf(lat)+".jpg");
        StorageReference mountainImagesRef = mStorage.child("images/"+String.valueOf(lng)+String.valueOf(lat)+".jpg");


        mapdir var=new mapdir(lat,lng);
        DatabaseReference newref = myRef.push();
        newref.setValue(var);
        //myRef.setValue(var);
      //  myRef.child("0").child("lng").setValue(var.getLng());

        //myRef.child(String.valueOf(lastvalue)).child("lat").setValue(var.getLat());

       //myRef.child()


       /* myRef.child("no").child("lng").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  String data1=dataSnapshot.child("no").child("lat").getValue(String.class);
                     t.setText(data1);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
*/





        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data2 = baos.toByteArray();
        UploadTask uploadTask = mountainsRef.putBytes(data2);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(MainActivity.this, "fail to upload",
                        Toast.LENGTH_LONG).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                Toast.makeText(MainActivity.this, "succeeded to upload",
                        Toast.LENGTH_LONG).show();

            }
        });
    }
}
