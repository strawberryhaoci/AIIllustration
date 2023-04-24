package com.util;/*
 *name: AIIllustration
 *description:
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

/**
 * @program: AIIllustration
 * @description: imageview util
 */
public class ImageViewUtil {
    public static void setIVPath(ImageView iv, String path){
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        iv.setImageBitmap(bitmap);
    }
}
