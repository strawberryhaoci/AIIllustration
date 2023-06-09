package com.home.fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.home.R;
import com.user.User;
import com.util.ClickEvent;
import com.util.DatabaseHelper;
import com.util.FragmentUtil;
import com.util.MD5Utils;
import com.util.ToastUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "REGISTER";
    public static final int PHONE_LENGTH = 11;
    public static final int PWD_LENGTH_LOW = 8;
    public static final int PWD_LENGTH_HIGH = 16;

    private boolean registerFlag = false;
    private boolean phoneFlag = false;
    private boolean pwd1Flag = false;
    private boolean pwd0Flag = false;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        EditText et_phone = v.findViewById(R.id.text_phone);
        EditText et_pwd0 = v.findViewById(R.id.text_password0);
        EditText et_pwd1 = v.findViewById(R.id.text_password1);
        Switch sw_pwd0 = v.findViewById(R.id.sw_pwd0);
        Switch sw_pwd1 = v.findViewById(R.id.sw_pwd1);

        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase sqliteDB = dbHelper.getWritableDatabase();
        User registerU = new User();
        sw_pwd0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ClickEvent.passwordVisible(et_pwd0,b);
            }
        });
        sw_pwd1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ClickEvent.passwordVisible(et_pwd1,b);
            }
        });
        TextView text_hint = v.findViewById(R.id.text_register_hint);
        //判断用户名与密码合法
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String input_phone = et_phone.getText().toString();
                if(input_phone.length()==PHONE_LENGTH){
                    if(dbHelper.queryUserByName(input_phone)==null){
                        text_hint.setText("");
                        phoneFlag = true;
                    }
                    else {
                        text_hint.setText("该用户已注册，点此返回登陆页面");
                        text_hint.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentUtil.replaceFragment(fragmentManager,new UserFragment());
                            }
                        });

                    }

                }else {
                    text_hint.setText("请输入正确的手机号码");
                }

            }
        });
        et_pwd0.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String input_pwd0 = et_pwd0.getText().toString();
                if(input_pwd0.length()>=PWD_LENGTH_LOW && input_pwd0.length()<=PWD_LENGTH_HIGH){
                    text_hint.setText("");
                    pwd0Flag = true;
                }else {
                    text_hint.setText("密码长度不符合要求");
                }

            }
        });
        et_pwd1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String input_pwd0 = et_pwd0.getText().toString();
                String input_pwd1 = et_pwd1.getText().toString();

                if(input_pwd0.equals(input_pwd1)){
                    pwd1Flag = true;
                    text_hint.setText("");
                }else {
                    text_hint.setText("两次输入密码不一致");
                }

            }
        });
        Button btn_register = v.findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phoneFlag&&pwd0Flag&&pwd1Flag){
                    String input_phone = et_phone.getText().toString();
                    String input_pwd0 = et_pwd0.getText().toString();
                    registerU.setUsername(input_phone);
                    String m5pwd = MD5Utils.md5Password(input_pwd0);
                    registerU.setPassword(m5pwd);
                    //创建数据库

                    long registerID = dbHelper.insertUser(registerU);
                    Log.d(TAG, "插入"+registerID);
                    ToastUtil.shortShow(getActivity(),"注册成功");
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentUtil.replaceFragment(fragmentManager,new UserFragment());
                }else {
                    ToastUtil.shortShow(getActivity(),"请正确填写信息");
                }
            }
        });
        // Inflate the layout for this fragment
        return v;
    }
}