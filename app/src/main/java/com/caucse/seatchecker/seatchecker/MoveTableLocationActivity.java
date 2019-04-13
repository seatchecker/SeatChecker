package com.caucse.seatchecker.seatchecker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MoveTableLocationActivity extends AppCompatActivity implements GridAdapter.GridItemListener {

    private Controller controller;
    private RadioGroup radioGroup;
    private Button btnNextPage;
    private ArrayList<TableInfo> tables;
    Cafe cafe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_table_location);

        Intent intent = getIntent();
        cafe = (Cafe)intent.getSerializableExtra("CAFE");
        tables = (ArrayList<TableInfo>)intent.getSerializableExtra("TABLE");

        radioGroup = findViewById(R.id.radio);
        btnNextPage = findViewById(R.id.btnNextPage);
        ((RadioButton)radioGroup.getChildAt(0)).setChecked(true);

        controller = new Controller(this,cafe);
        controller.initTableGridView(this,tables);

        btnNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.isAllTableSettingComplete();
            }
        });

    }



    @Override
    public void onItemClick(View view, int position) {

        int name = radioGroup.getCheckedRadioButtonId();
        RadioButton button = findViewById(name);
        int idx = radioGroup.indexOfChild(button);
        switch(idx){
            case 0 :
                switch(controller.addTwoTable(position)){
                    case -1 : Toast.makeText(this,"해당 좌석에 테이블을 추가할 수 없습니다.",Toast.LENGTH_SHORT).show();
                    break;
                    case 0 :
                        radioGroup.getChildAt(0).setClickable(false);
                        if(radioGroup.getChildAt(1).isClickable()) {
                            ((RadioButton) radioGroup.getChildAt(1)).setChecked(true);
                        }else{
                            ((RadioButton) radioGroup.getChildAt(2)).setChecked(true);
                        }
                    break;
                }
                break;
            case 2:
                switch(controller.deleteTable(position)){
                    case -1 :
                        Toast.makeText(this, "삭제할 테이블이 없습니다", Toast.LENGTH_SHORT).show();
                        break;
                    case 1 : radioGroup.getChildAt(0).setClickable(true);
                        break;
                    case 2 : radioGroup.getChildAt(1).setClickable(true);
                }
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
        switch(controller.addFourTable(startPosition,endPosition)){
            case -1 :
                Toast.makeText(this,"해당 좌석에 테이블을 추가할 수 없습니다.",Toast.LENGTH_SHORT).show();
                break;
            case 0 :
                radioGroup.getChildAt(1).setClickable(false);
                if(radioGroup.getChildAt(0).isClickable()) {
                    ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);
                }else{
                    ((RadioButton) radioGroup.getChildAt(2)).setChecked(true);
                }
                break;
        }
    }

}
