package com.zxl.creditcard.costomer.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @项目名称: Library
 * @包名: com.zxl.library.data
 * @作者: 祝学梁
 * @创建时间: 2020/2/6  13:54
 * @描述: ${TODO}
 **/
public class DbHelper extends SQLiteOpenHelper {


    public static final String USER_DATABASE = "CREATE TABLE user (" +
            "_id integer PRIMARY KEY," +
            "  name varchar(255) ," +
            "  phone varchar(255) ," +
            "  password varchar(255) ," +
            "  photo Blob)";


    public static final String COUPON_DATABASE = "CREATE TABLE coupon (" +
            "  _id integer PRIMARY KEY," +
            "  cid int," +
            "  name varchar(255) ," +
            "   photo Blob," +
            "   _data varchar(255)," +
            "   inscription varchar(255)," +
            "   content varchar(255)," +
            "   state varchar(255)," +
            "   CONSTRAINT `fk_coupon_user` FOREIGN KEY (`cid`) REFERENCES `user` (`_id`))";

    /*"create table " + Constants.TABLE_USER
            + "(_id int) AUTO_INCREMENT,name varchar(255) ,_key varchar(255) ,photo Blob,PRIMARY KEY (_id))  DEFAULT CHARSET=utf8";
*/
    public DbHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据表
        db.execSQL(USER_DATABASE);
        db.execSQL(COUPON_DATABASE);

        //初始化数据
        String sql = "insert into " + Constants.TABLE_USER +
                "(name,password) values(?,?)";

        db.execSQL(sql, new Object[]{"zxl", "123456"});

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
