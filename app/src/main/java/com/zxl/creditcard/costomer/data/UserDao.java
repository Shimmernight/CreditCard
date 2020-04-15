package com.zxl.creditcard.costomer.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @项目名称: CreditCard
 * @包名: com.zxl.creditcard.costomer.data
 * @作者: 祝学梁
 * @创建时间: 2020/3/2  15:26
 * @描述: ${TODO}
 **/
public class UserDao {

    DbHelper mDbHelper;
    SQLiteDatabase db;

    public UserDao(Context context) {
        mDbHelper = new DbHelper(context);
        //获取权限
        db = mDbHelper.getWritableDatabase();
    }


    //向数据库插入数据
    public boolean register(String username,String phone, String password) {
        db.execSQL("insert into " + Constants.TABLE_USER + " (name,phone,password) values (?,?,?)",
                new String[]{username,phone, password});
        db.close();
        return true;
    }

    //检验用户名是否已存在
    public boolean CheckIsDataAlreadyInDBorNot(String value) {
        String Query = "Select * from " + Constants.TABLE_USER + " where name =?";
        Cursor cursor = db.rawQuery(Query, new String[]{value});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }
    //检验手机号是否已存在
    public boolean CheckIsPhoneAlreadyInDBorNot(String value) {
        String Query = "Select * from " + Constants.TABLE_USER + " where phone =?";
        Cursor cursor = db.rawQuery(Query, new String[]{value});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }


    //登录
    public boolean checkLogin(String name, String passWord) {

        String sql = "select * from "+Constants.TABLE_USER+" where name = ? and passWord = ?";
        Cursor cursor = db.rawQuery(sql,new String[]{name,passWord});
        if (cursor.getCount()>0){
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }
}
