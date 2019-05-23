package com.caucse.seatchecker.seatchecker;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.List;

public class CafeInfoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Button btnManager,btnSeatCheck;
    private TextView tvNameOfCafe;
    private Cafe curCafe;
    private ImageView ivCafeImage;
    private TextView tvCafeLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = getLayoutInflater().from(this).inflate(R.layout.activity_cafe_info,null);
        setContentView(view);

        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        btnManager= findViewById(R.id.btnManager);
        btnSeatCheck = findViewById(R.id.btnSeatCheck);

        tvNameOfCafe = findViewById(R.id.tvNameOfCafe);
        ivCafeImage = findViewById(R.id.ivCafeImage);
        tvCafeLocation = findViewById(R.id.tvCafeLocation);

        curCafe = (Cafe)intent.getSerializableExtra("CAFE");
        tvNameOfCafe.setText(curCafe.getName());
        tvCafeLocation.setText(curCafe.getLocation());


        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        setImage(curCafe);
        btnManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CustomDialog customDialog = new CustomDialog(v);

                customDialog.callManagerPasswordDialog(curCafe);
            }
        });


        btnSeatCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBController dbController = new DBController(view);
                dbController.getTableInfo(curCafe,DBController.MODE_CHECK_TABLE);
            }
        });
    }

    void setImage(Cafe curCafe) {
        FirebaseStorage fs = FirebaseStorage.getInstance();
        try {
            StorageReference reference = fs.getReference().child(curCafe.getImageURL());
            Glide.with(this).load(reference).into(ivCafeImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Geocoder geocoder = new Geocoder(this);
        String str = curCafe.getLocation();
        List<Address> addressList = null;
        try{
            addressList = geocoder.getFromLocationName(
                    str,
                    10);
        }catch(IOException e){
            e.printStackTrace();
        }
        assert addressList != null;
        String []splitStr = addressList.get(0).toString().split(",");
        Double latitue = addressList.get(0).getLatitude();
        Double longitude = addressList.get(0).getLongitude();

        //String latitude = splitStr[10].substring(splitStr[10].indexOf("=")+1);
        //String longitude = splitStr[12].substring(splitStr[12].indexOf("=")+1);

        //LatLng CAFE = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
        LatLng CAFE = new LatLng(latitue,longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(CAFE);
        markerOptions.title(curCafe.getName());
        googleMap.addMarker(markerOptions);


        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CAFE,15));

    }
}
