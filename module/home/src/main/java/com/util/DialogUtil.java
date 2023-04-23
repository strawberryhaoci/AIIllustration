package com.util;/*
 *name: AIIllustration
 *description:
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;

import com.home.R;

/**
 * @program: AIIllustration
 * @description: dialog
 */
public class DialogUtil {
    public static void showDialog(Activity activity,int messageID,String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                .setTitle(title)
                .setIcon(R.drawable.ic_icon)
                .setMessage(messageID);
        builder.create().show();
    }

}
