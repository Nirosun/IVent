package com.ivent.exception;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

/**
 * Created by Luciferre on 11/4/15.
 */

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


}
