package com.caucse.seatchecker.seatchecker;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

class CustomDialog {
    private Context context;
    View view;
    private Dialog dialog;
    private Cafe cafe;

    private EditText edtPassword;
    private Button btnOK;
    private Button btnCancel;
    private CustomDialog customDialog;



    CustomDialog(View view){
        this.view = view;
        this.context = view.getContext();
        customDialog = this;
    }


    void callAlarmSettingDialog(final Cafe cafe, final String androidId, final Button button){
        this.cafe = cafe;
        dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alarm_dialog);
        dialog.show();
        final RadioGroup rbTable = dialog.findViewById(R.id.rbTableSelect);
        ((RadioButton) rbTable.getChildAt(0)).setChecked(true);

        final CheckBox cbPlug = dialog.findViewById(R.id.cbPlugSetting);
        final Button btnOK = dialog.findViewById(R.id.btnOK);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tablenum = 0;
                boolean isplug = false;
                DBController dbController = new DBController(btnOK.getRootView());
                int radioButtonID = rbTable.getCheckedRadioButtonId();
                View rbButton = rbTable.findViewById(radioButtonID);
                switch(rbTable.indexOfChild(rbButton)){
                    case 0 :
                        tablenum = 1;
                        break;
                    case 1 :
                        tablenum = 2;
                        break;
                    case 2 :
                        tablenum = 4;
                        break;
                }

                if(cbPlug.isChecked()){
                    isplug = true;
                }else{
                    isplug = false;
                }
                dbController.addAlarmSetting(cafe.getDid(), androidId, tablenum, isplug);
                button.setBackground(v.getResources().getDrawable(R.drawable.alarm_on));
                button.setTag("alarm_on.jpg");
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //todo : set buttons, and call LOCALDB & REALDB AND SAVE!!
    }

    void callManagerPasswordDialog(final Cafe cafe){
        this.cafe = cafe;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.hash_dialog);
        dialog.show();

        edtPassword = dialog.findViewById(R.id.edtPassword);
        btnOK = dialog.findViewById(R.id.btnOK);
        btnCancel = dialog.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SHA1Hash HashFunction = new SHA1Hash(edtPassword.getText().toString().trim());
                String inputHash = HashFunction.Encode();
                if(cafe.getHash().equalsIgnoreCase(inputHash)){
                    Intent intent = new Intent(context,ManagerActivity.class);
                    intent.putExtra("CAFE",cafe);
                    context.startActivity(intent);
                    dialog.dismiss();
                }else{
                    Toast.makeText(context,"비밀번호를 잘못 입력하셨습니다.",Toast.LENGTH_SHORT).show();
                    edtPassword.setText("");
                }
            }
        });
    }


    void callTableInfoResetDialog(final Cafe cafe){

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.table_reset_dialog);
        dialog.show();

        btnOK = dialog.findViewById(R.id.btnOK);
        btnCancel = dialog.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBController dbController = new DBController(view);
                dbController.getTableInfo(cafe,DBController.MODE_MOVE_TABLE);
                dialog.dismiss();
            }
        });
    }
}
