package com.home.fragments;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.home.HomeActivity;
import com.home.R;
import com.user.User;
import com.util.DatabaseHelper;
import com.util.FragmentUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {
    private static final String TAG = "USERFRAG";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
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
        View v = inflater.inflate(R.layout.fragment_user, container, false);
        EditText et_phone = v.findViewById(R.id.text_phone);
        EditText et_pwd = v.findViewById(R.id.text_password);
        Button btn_login = v.findViewById(R.id.btn_login);

        //创建数据库
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase sqliteDB = dbHelper.getWritableDatabase();

        User testU = new User("0000","12345678");
        long testInsert = dbHelper.insertUser(testU);
        Log.d(TAG, Long.toString(testInsert));
        User testQ = dbHelper.queryUserByName("test");
        Log.d(TAG, "onCreateView: "+testQ.getPassword());
        //点击登录
        btn_login.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {

                String input_phone = et_phone.getText().toString();
                String input_pwd = et_pwd.getText().toString();
                User loginu = dbHelper.queryUserByName(input_phone);
                if(loginu == null){
                    Toast.makeText(getContext(), "未注册", Toast.LENGTH_SHORT).show();
                }
                else if(loginu.getPassword() .equals(input_pwd)){
                    Toast.makeText(getContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onClick: login");
                    //切换界面
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.nav_host_fragment,new HomeFragment());
                    transaction.commit();
                }
                else{
                    Log.d(TAG, "onClick:"+loginu.getPassword()+" input:"+input_pwd);
                    Toast.makeText(getContext(), "密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //监听
        TextView text_register = v.findViewById(R.id.text_register);
        TextView text_find_pwd = v.findViewById(R.id.text_find_pwd);
        text_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentUtil.replaceFragment(fragmentManager,new RegisterFragment());
            }
        });
        text_find_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                //TODO: replace fragment
                FragmentUtil.replaceFragment(fragmentManager,new RegisterFragment());
            }
        });
        Switch sw_pwd = v.findViewById(R.id.sw_pwd);
        sw_pwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    et_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    et_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        return v;
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment,fragment);
        transaction.commit();
    }
}