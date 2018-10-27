package com.example.gourav.cameragallery;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;


public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    DatabaseReference myref;
    LatLng sydney = new LatLng(-34, 151);
    LatLng bdsm= new LatLng(0,0);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        myref = FirebaseDatabase.getInstance().getReference();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;


     //   mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));


        myref.addValueEventListener(new ValueEventListener() {
            @Override


            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    mapdir value = snap.getValue(mapdir.class);
                    mMap.addMarker(new MarkerOptions().position(new LatLng(value.getLat(), value.getLng()))
                            .snippet(String.valueOf(value.getLng())+String.valueOf(value.getLat()+".jpg")));
                   // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));


                    //   mapdir value = dataSnapshot.getValue(mapdir.class);


                    double k = value.getLng();
                    double l = value.getLat();

                    bdsm = new LatLng(l, k);
                    mMap.addMarker(new MarkerOptions().position(new LatLng(l, k)));

                    //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                    //mMap.addMarker(new MarkerOptions().position(new LatLng(value.getLat(),value.getLng())));
                   // Toast.makeText(MapsActivity2.this, String.valueOf(l), Toast.LENGTH_LONG).show();

                }


            }
            /*LatLng sydney = new LatLng(-34, 151);*/

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

        final Intent intent = new Intent(this, feed.class);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String s = marker.getSnippet();

             //   Toast.makeText(MapsActivity2.this,s, Toast.LENGTH_LONG).show();
                if(s!=null){
                    //Toast.makeText(MapsActivity2.this,s, Toast.LENGTH_SHORT).show();
                    intent.putExtra("abc",s);
                    startActivity(intent);


                }

                //startActivity(intent);
                //intent.putExtra("abc",s);
                return true;
            }
        });

    }
}
