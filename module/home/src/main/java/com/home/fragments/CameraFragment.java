package com.home.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.home.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CameraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CameraFragment extends Fragment {
    private static String[] items = new String[]{
            "拍照",
            "从相册中选择",
    };
    private static String TAG = "CAMERA";
    private static final int TAKE_PHOTO = 1;//声明一个请求码，用于识别返回的结果
    private static final int SCAN_OPEN_PHONE = 2;// 相册
    private Uri imageUri;
    private String picPath;
    Bitmap bitmap;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CameraFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CameraFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CameraFragment newInstance(String param1, String param2) {
        CameraFragment fragment = new CameraFragment();
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
        View v = inflater.inflate(R.layout.fragment_camera, container, false);
        choosePic();

        return v;
    }

    public void choosePic() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("请选择图片")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            openCamera();
                            Log.d(TAG, "openCamera");
                        } else {
                            openGallery();
                            Log.d(TAG, "openGallery");

                        }
                        return;
                    }
                });
        builder.create().show();

    }

    private void openCamera() {
        String imageName = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());
//        File outputImage = new File(getActivity().getExternalCacheDir(), imageName + ".jpg");
        File outputImage = new File(Environment.getExternalStorageDirectory() + "/DCIM/4L", imageName + ".jpg");
        Objects.requireNonNull(outputImage.getParentFile()).mkdirs();
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            boolean a = outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(getActivity(), "com.aiillustration.fileprovider", outputImage);
//            picPath = imageUri.getPath();
            picPath = Environment.getExternalStorageDirectory().getPath() + "/DCIM/4L/" + imageName + ".jpg";
            Log.d(TAG, ">=24" + picPath);
        } else {
            imageUri = Uri.fromFile(outputImage);
            picPath = imageUri.getPath();
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SCAN_OPEN_PHONE);
    }

    @SuppressLint("SetTextI18n")
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Log.d(TAG,getActivity().getLocalClassName());
        ImageView img = getView().findViewById(R.id.pic_test);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageUri));

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
//                    picPath = imageUri.getPath().toString();
//                    picPath = getActivity().getExternalCacheDir().getPath()+imageUri.getPath();
                    Log.d(TAG, picPath);

                    img.setImageBitmap(bitmap);
                    img.invalidate();
                }
                break;
            case SCAN_OPEN_PHONE:
                if (resultCode == RESULT_OK) {
                    Uri selectImage = data.getData();
                    String[] FilePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContext().getContentResolver().query(selectImage,
                            FilePathColumn, null, null, null);
                    cursor.moveToFirst();
                    //从数据视图中获取已选择图片的路径
                    int columnIndex = cursor.getColumnIndex(FilePathColumn[0]);
                    picPath = cursor.getString(columnIndex);
                    Log.d(TAG, picPath);
                    cursor.close();
                    Bitmap bitmap = BitmapFactory.decodeFile(picPath);
                    img.setImageBitmap(bitmap);
                    img.invalidate();
                }
                break;
            default:
                break;
        }
        //照片路径传给fragment1
        Bundle bundle = new Bundle();
        bundle.putString("picPath", picPath);
        NavController controller = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        controller.navigate(R.id.action_nav_2_to_nav_1, bundle);

    }
}