package com.caucse.seatchecker.seatchecker;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

class CustomDialog {
    private Context context;
    View view;

    CustomDialog(View view){
        this.view = view;
        this.context = view.getContext();
    }

    void callFunction(final String cafeName){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.show();

        final EditText edtPassword = dialog.findViewById(R.id.edtPassword);
        final Button btnOK = dialog.findViewById(R.id.btnOK);
        final Button btnCancel = dialog.findViewById(R.id.btnCancel);

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
                //if the password is correct
                if(db.checkManagerPassword(cafeName ,edtPassword.getText().toString())){
                    Intent intent = new Intent(context,ManagerActivity.class);
                    intent.putExtra("name",cafeName);
                    context.startActivity(intent);
                    dialog.dismiss();
                }else{
                    Toast.makeText(context,"비밀번호를 잘못 입력하셨습니다.",Toast.LENGTH_SHORT).show();
                    edtPassword.setText("");
                }
            }
        });
    }
}
