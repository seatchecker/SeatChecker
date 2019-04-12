package com.caucse.seatchecker.seatchecker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MoveTableLocationActivity extends AppCompatActivity implements GridAdapter.GridItemListener {

    private Controller controller;
    private RadioGroup radioGroup;
    Cafe cafe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_table_location);

        Intent intent = getIntent();
        cafe = (Cafe)intent.getSerializableExtra("CAFE");

        radioGroup = findViewById(R.id.radio);
        ((RadioButton)radioGroup.getChildAt(0)).setChecked(true);

        controller = new Controller(this,cafe);
        controller.initTableGridView(this);

    }



    @Override
    public void onItemClick(View view, int position) {

        int name = radioGroup.getCheckedRadioButtonId();
        RadioButton button = findViewById(name);
        int idx = radioGroup.indexOfChild(button);
        switch(idx){
            case 0 :
                controller.addTwoTable(position);
                break;
            case 2:
                controller.deleteTable(position);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemDrag(int startPosition, int endPosition) {
        int name = radioGroup.getCheckedRadioButtonId();
        RadioButton button = findViewById(name);
        int idx = radioGroup.indexOfChild(button);
        int width = Integer.parseInt(cafe.getGrid().get("width").toString());
        if(idx != 1){
            return;
        }
        if(Math.abs(startPosition -endPosition) != 1 && Math.abs(startPosition-endPosition) != width){
            Toast.makeText(this,"연속된 두 자리를 드래그 하여 추가해주세요",Toast.LENGTH_SHORT).show();
            return;
        }
        controller.addFourTable(startPosition,endPosition);
    }

}
