package com.util;/*
 *name: AIIllustration
 *description:
 */

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.user.User;
import com.user.UserTable;

import androidx.annotation.Nullable;

/**
 * @program: AIIllustration
 * @description: sqlite database
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static Integer Version = 1;
    private static final String DBNAME = "db4L";
    private static String TAG = "DATABASE";

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context, String name) {
        super(context, name, null, Version);
    }

    public DatabaseHelper(Context context) {
        super(context,DBNAME,null,Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表
        String sql = UserTable.CREATE_TABLE;
        //直到getWritableDatabase()/getReadableDatabase()第一次被调用
        db.execSQL(sql);
        Log.d(TAG, "onCreate: dbCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
//        String sql = "alter table person add picNum integer";
//        db.execSQL(sql);
//        db.execSQL(UserTable.UPGRADE_TABLE);
//        onCreate(db);
        if(oldVer == 1){
            db.execSQL(UserTable.UPGRADE_TABLE);
        }
        Log.d(TAG, "new version" + newVer);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        if (oldVersion > 2){
            db.execSQL(UserTable.DOWNGRADE_TABLE);
        }
    }

    public long insertUser(User user){
        Log.d(TAG,"insert");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserTable.COL_USERNAME,user.getUsername());
        values.put(UserTable.COL_PASSWORD,user.getPassword());
        values.put(UserTable.COL_PICNUM,user.getPicNum());
        long id = db.insert(UserTable.TABLE_NAME,null,values);
        db.close();
        return id;
    }

    public User queryUserByName(String name){
        User u = new User();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] colArray = new String[]{
             UserTable.COL_ID,
             UserTable.COL_USERNAME,
             UserTable.COL_PASSWORD,
             UserTable.COL_PICNUM
        };
        Cursor cursor = db.query(UserTable.TABLE_NAME,colArray,
                UserTable.COL_USERNAME+"=? ",
                new String[]{name},
                null,null,null);
        if (cursor != null && cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(UserTable.COL_ID));
            @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(UserTable.COL_USERNAME));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(UserTable.COL_PASSWORD));
            @SuppressLint("Range") int picNum = cursor.getInt(cursor.getColumnIndex(UserTable.COL_PICNUM));
            u.setUserid(id);
            u.setUsername(username);
            u.setPassword(password);
            u.setPicNum(picNum);
            cursor.close();
            return u;
        }
        return null;
    }
    public User queryUserById(int id){
        User u = new User();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] colArray = new String[]{
                UserTable.COL_ID,
                UserTable.COL_USERNAME,
                UserTable.COL_PASSWORD,
                UserTable.COL_PICNUM
        };

        Cursor cursor = db.query(UserTable.TABLE_NAME,colArray,
                UserTable.COL_ID+"=? ",
                new String[]{Integer.toString(id)},
                null,null,null);
        if (cursor != null && cursor.moveToNext()) {
            @SuppressLint("Range") int uid = cursor.getInt(cursor.getColumnIndex(UserTable.COL_ID));
            @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(UserTable.COL_USERNAME));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(UserTable.COL_PASSWORD));
            @SuppressLint("Range") int picNum = cursor.getInt(cursor.getColumnIndex(UserTable.COL_PICNUM));
            u.setUserid(uid);
            u.setUsername(username);
            u.setPassword(password);
            u.setPicNum(picNum);
            cursor.close();
            return u;
        }
        return null;
    }
    //修改密码
    public int updateUserPwd(String username,String password){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserTable.COL_PASSWORD,password);
        int idReturn = db.update(UserTable.TABLE_NAME,values,UserTable.COL_USERNAME+" =? ",
                new String[]{username});
        db.close();
        return idReturn;
    }
    public int getUserCount() {
        String countQuery = "select * from "+UserTable.TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    public  int deleteUser(int id){
        SQLiteDatabase db = getWritableDatabase();
        int idReturnByDelete = db.delete(UserTable.TABLE_NAME,UserTable.COL_ID+"=? ",
                new String[]{String.valueOf(id)});
        db.close();
        return idReturnByDelete;
    }
    public int deleteAllUser(){
        SQLiteDatabase db = getWritableDatabase();
        int idReturnByDelete = db.delete(UserTable.TABLE_NAME,String.valueOf(1),null);
        db.close();
        return idReturnByDelete;
    }
}
