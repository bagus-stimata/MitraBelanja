package com.desgreen.mitrabelanja.common_utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.desgreen.mitrabelanja.R;


public class SearchableDialogFMaterial {

    AlertDialog alertDialog;

    Button buttonClose;

    public SearchableDialogFMaterial(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.searchable_dialog_fmaterial, null);

        buttonClose = view.findViewById(R.id.alert_button_close);

        alertDialog = new AlertDialog.Builder(context)
                .setView(view)
                .create();


        alertDialog.setCancelable(true);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT)); //Supaya Transparant
    }

    public void showDialog(){
        alertDialog.show();
    }
    public void dismiss(){
        alertDialog.dismiss();
    }
    public void setCancelable(boolean isCancelable) {
        alertDialog.setCancelable(isCancelable);
    }

    public Button getButtonClose() {
        return buttonClose;
    }

    public void setButtonClose(Button buttonClose) {
        this.buttonClose = buttonClose;
    }

    public AlertDialog getAlertDialog() {
        return alertDialog;
    }

    public void setAlertDialog(AlertDialog alertDialog) {
        this.alertDialog = alertDialog;
    }


}
