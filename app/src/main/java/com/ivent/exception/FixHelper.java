package com.ivent.exception;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

//Fix exception
public class FixHelper {

    public void fixMissingInput(Context context) {
        Log.e("Output", "Input Missing! Please don't leave input blank.\n");

        //new alert dialog to alert users to correct input
        new AlertDialog.Builder(context)
                .setTitle("Input Missing")
                .setMessage("Please don't leave input blank.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void fixUserExist(Context context){
        Log.e("Output", "Input Missing! Please don't leave input blank.\n");

        //new alert dialog to alert users to correct input
        new AlertDialog.Builder(context)
                .setTitle("User Exist")
                .setMessage("Please input another user name.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    @Deprecated
    public void fixMissingEventImage(Context context) {
        Log.e("Output", "Input Missing! Please don't leave input blank.\n");

        //new alert dialog to alert users to correct input
        new AlertDialog.Builder(context)
                .setTitle("Event Image Missing")
                .setMessage("Please add event cover photo.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


}
