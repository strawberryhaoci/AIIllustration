package com.util;/*
 *name: AIIllustration
 *description:
 */

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

/**
 * @program: AIIllustration
 * @description: click item event
 */
public class ClickEvent {
    public void toHome(View V){
        Log.d(TAG, "toHome: ");
    }

    /**
     * @param et 密码输入框
     * @param isChecked sw状态
     */
    public static void passwordVisible(EditText et,boolean isChecked){
        if(isChecked){
            et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        else {
            et.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
}
