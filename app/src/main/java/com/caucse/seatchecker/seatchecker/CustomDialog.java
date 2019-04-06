package com.caucse.seatchecker.seatchecker;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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


    void callFunction(final Cafe cafe){
        this.cafe = cafe;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
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
                DBController db = new DBController(view);
                db.checkManagerPassword(cafe.getName() ,edtPassword.getText().toString(),customDialog );
            }
        });
    }

    void sendWrongMessage(){
        Toast.makeText(context,"비밀번호를 잘못 입력하셨습니다.",Toast.LENGTH_SHORT).show();
        edtPassword.setText("");

    }

    void sendActivity(){
        Intent intent = new Intent(context,ManagerActivity.class);
        intent.putExtra("CAFE",cafe);
        context.startActivity(intent);
        dialog.dismiss();
    }
}
