package com.user;/*
 *name: AIIllustration
 *description:
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.R;
import com.util.ImageViewUtil;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @program: AIIllustration
 * @description: pic list item
 */
public class PicAdapter extends ArrayAdapter<Pic> {
    public PicAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
    public PicAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Pic> objects) {
        super(context, resource,objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Pic pic = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        ImageView img1 = view.findViewById(R.id.lv_item_img1);
        ImageViewUtil.setIVPath(img1,pic.getGenPath());
        TextView redraw = view.findViewById(R.id.redraw);
        TextView seed = view.findViewById(R.id.seed);
        TextView des = view.findViewById(R.id.des);
        redraw.setText(Integer.toString(pic.getRedrawFactor()));
        seed.setText(Integer.toString(pic.getSeed()));
        des.setText(pic.getDes());

        return view;
    }
}
