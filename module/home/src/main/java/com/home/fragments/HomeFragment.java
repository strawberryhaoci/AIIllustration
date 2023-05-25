package com.home.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.home.R;
import com.util.ToastUtil;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private SeekBar sb_redraw_factor;
    private ImageView pic;
    private EditText input_des;
    private EditText input_seed;
    private Button btn_draw;
    public static String TAG = "HomeFrag";
    private int desProcess;
    private int seed;
    private String des;
    private String picPath = "";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        TextView loading = v.findViewById(R.id.loading);
        loading.setVisibility(View.GONE);
        sb_redraw_factor = v.findViewById(R.id.sb_redraw_factor);
        input_des = v.findViewById(R.id.input_des);
        input_seed = v.findViewById(R.id.input_seed);
        btn_draw = v.findViewById(R.id.home_btn_draw);
        pic = v.findViewById(R.id.pic);
        desProcess = 0;
        sb_redraw_factor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                desProcess = seekBar.getProgress();
            }
        });
        btn_draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String seedStr = input_seed.getText().toString();
                if (seedStr != null && (!seedStr.isEmpty())) {
                    seed = Integer.parseInt(seedStr);
                } else {
                    Random r = new Random();
                    seed = r.nextInt(1000000);
                }
                des = input_des.getText().toString();
                Bundle bundle = getArguments();
                if (bundle == null){
                    ToastUtil.shortShow(getActivity(),"请先选择图片");
                }else {

                    Bitmap bitmap = BitmapFactory.decodeFile(picPath);
                    ImageView imageView = v.findViewById(R.id.pic);
                    bitmap = setGaussblur(20,bitmap);
                    imageView.setImageBitmap(bitmap);
                    loading.setVisibility(View.VISIBLE);
                }

                Log.d(TAG, "click draw");
            }
        });

        return v;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d(TAG, "hidden change");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "resume");
        Bundle bundle = getArguments();
        if (bundle != null) {
            picPath = bundle.getString("picPath");
            Log.d(TAG, picPath);
            Bitmap bitmap = BitmapFactory.decodeFile(picPath);
            pic.setImageBitmap(bitmap);
            pic.invalidate();
        }
    }
    public Bitmap setGaussblur(int radius, Bitmap source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            RenderScript renderScript = RenderScript.create(getContext());
            final Allocation input = Allocation.createFromBitmap(renderScript, source);
            final Allocation output = Allocation.createTyped(renderScript, input.getType());
            ScriptIntrinsicBlur scriptIntrinsicBlur;
            scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
            scriptIntrinsicBlur.setInput(input);
            scriptIntrinsicBlur.setRadius(radius);
            scriptIntrinsicBlur.forEach(output);
            output.copyTo(source);
            renderScript.destroy();
            return source;
        } else {
            return source;
        }
    }
}