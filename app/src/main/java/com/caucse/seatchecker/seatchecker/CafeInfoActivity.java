package com.caucse.seatchecker.seatchecker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CafeInfoActivity extends AppCompatActivity {

    private Button btnManager;
    private TextView tvNameOfCafe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_info);

        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        btnManager= findViewById(R.id.btnManager);
        tvNameOfCafe = findViewById(R.id.tvNameOfCafe);
        final Cafe curCafe = (Cafe)intent.getSerializableExtra("CAFE");
        tvNameOfCafe.setText(curCafe.getName());
        btnManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                assert inflater != null;
                View view;
                view = inflater.inflate(R.layout.activity_cafe_info, null);
                CustomDialog customDialog = new CustomDialog(view);

                customDialog.callManagerPasswordDialog(curCafe);
            }
        });

    }
}
