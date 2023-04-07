package com.util;

import android.Manifest;
import android.app.Activity;

import androidx.core.app.ActivityCompat;

/**
 * @program:
 * @description: permission
 */
public class PermissionUtil {
    public PermissionUtil(){

    }
    public static void askPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        },0);
    }
}
