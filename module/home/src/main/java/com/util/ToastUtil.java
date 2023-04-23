package com.util;/*
 *name: AIIllustration
 *description:
 */

import android.app.Activity;
import android.widget.Toast;

/**
 * @program: AIIllustration
 * @description: toast util
 */
public class ToastUtil {
    public static void shortShow(Activity activity,String text){
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
    }
    public static void longShow(Activity activity,String text){
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show();
    }
}
