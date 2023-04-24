package com.home.fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.home.R;
import com.user.Pic;
import com.user.PicAdapter;
import com.util.DatabaseHelper;
import com.util.FragmentUtil;
import com.util.SPUtils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserInfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "USERINFO";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserInfoFragment newInstance(String param1, String param2) {
        UserInfoFragment fragment = new UserInfoFragment();
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
        View v = inflater.inflate(R.layout.fragment_user_info, container, false);
        String username = "";
        username = (String) SPUtils.get(getContext(),"username",username);
        //TODO:从数据库中获取图片列表并显示
        //创建数据库
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
//        //TODO:delete the test
//        String testU = "18746385503";
//        Pic pic = new Pic(testU,"/storage/emulated/0/DCIM/4L/18746385503/00000-685654.png",
//                "((masterpiece)),CG, girl, ANIME, LoRA, STUDIO GHIBLI, GHIBLI, best quality, sunshine",
//                60,7786676);
//        Pic pic1 = new Pic(testU,"/storage/emulated/0/DCIM/4L/18746385503/00026-766.png",
//                "light blue dress, girl, beautiful face, beautiful detailed background, white hair, awesome face, zoom in on eyes, gradient hair, by alphonse mucha",
//                50,1234);
//        Pic pic2 = new Pic(testU,"/storage/emulated/0/DCIM/4L/18746385503/00020-3089249475.png",
//                "((masterpiece)), girl, ANIME, (best quality), one girl, light, beautiful eyes",
//                70,23432313);
//        Log.d(TAG, "pic "+pic1.getRedrawFactor());
//        dbHelper.deleteAllPic();
//        dbHelper.insertPic(pic);
//        dbHelper.insertPic(pic1);
//        dbHelper.insertPic(pic2);
//        long id = dbHelper.insertPic(pic);
//        dbHelper.insertPic(pic1);
//        dbHelper.insertPic(pic2);
//        Log.d(TAG, "onCreateView: "+Long.toString(id));
        ArrayList<Pic> picArrayList= dbHelper.queyPicByUsername(username);
        ListView lv_pic = v.findViewById(R.id.picList);
        PicAdapter adapter = new PicAdapter(getContext(),R.layout.list_item,picArrayList);
        lv_pic.setAdapter(adapter);
//        Log.d(TAG, "onCreateView: "+ picArrayList.get(0).getGenPath());
        TextView text_username = v.findViewById(R.id.user_name);
        Button btn_logout = v.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentUtil.replaceFragment(fragmentManager,new UserFragment());
                SPUtils.remove(getContext(),"username");
            }
        });
        text_username.setText("用户"+username);
        // Inflate the layout for this fragment
        return v;
    }
}