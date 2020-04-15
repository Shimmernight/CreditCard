package com.zxl.creditcard.costomer.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zxl.creditcard.costomer.entity.CouponInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @项目名称: DataBase_e
 * @包名: com.zxl.database_e
 * @作者: 祝学梁
 * @创建时间: 2020/1/31  21:19
 * @描述: 数据库操作类
 **/
public class CouponDao {
    private static final String TAG = "CouponDao";
    private final DbHelper dbHelper;
    private SQLiteDatabase db;

    public CouponDao(Context context) {
        dbHelper = new DbHelper(context);
        //获取权限
        db = dbHelper.getWritableDatabase();
    }

    //增加
    //sql:insert into 表名 + 行标 values(行标个?)
    public void insert(CouponInfo coupon) {

        String sql = "insert into " + Constants.TABLE_COUPON +
                "(cid,name,photo,_data,inscription,content,state) values(?,?,?,?,?,?,?)";

        db.execSQL(sql, new Object[]{coupon.cid, coupon.name, coupon.photo, coupon._data,
                coupon.inscription, coupon.content, coupon.state});
        db.close();
    }

    //删除
    //sql:delete from 表名 where 条件
    public void delete(CouponInfo coupon) {

        db.execSQL("delete from " + Constants.TABLE_COUPON + " where _id =" + coupon._id);

        db.close();
    }


    //更新
    //sql:update 表名 set 修改内容 where 条件
    public void update() {
        db.execSQL("update " + Constants.TABLE_USER + " set 名字 = '祝学梁'");
        db.execSQL("update " + Constants.TABLE_USER + " set 账号 = 2020");

        db.close();
    }

    //查询所有券
    //sql:select * from 表名
    public List<CouponInfo> query() {
        List<CouponInfo> maps = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from " + Constants.TABLE_COUPON,
                null);
        //通过游标扫描Table
        while (cursor.moveToNext()) {
            CouponInfo map = new CouponInfo();
            map._id = cursor.getInt(0);
            map.cid = cursor.getInt(1);
            map.name = cursor.getString(2);
            map.photo = cursor.getBlob(3);
            map._data = cursor.getString(4);
            map.inscription = cursor.getString(5);
            map.content = cursor.getString(6);
            map.state = cursor.getString(7);

            /*添加新的类型的数据*/
            maps.add(map);
        }
        cursor.close();
        db.close();
        return maps;
    }


    /**
     * 在一定的时间内需要重复的操作数据库，那么不要调用close()方法，关闭游标就可以了
     * 真正不再需要的时候调用数据库的colse()方法
     */
    public void close() {
        db.close();
    }
}
