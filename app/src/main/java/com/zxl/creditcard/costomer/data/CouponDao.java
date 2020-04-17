package com.zxl.creditcard.costomer.data;

import com.zxl.creditcard.costomer.entity.CouponInfo;
import com.zxl.creditcard.utils.CloseUtil;
import com.zxl.creditcard.utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    //增加 sql:insert into 表名 + 行标 values(行标个?)
    public boolean insert(CouponInfo coupon) {
        conn = DBUtils.getConnection();
        String sql;
        try {
            sql = "insert into " + Constants.TABLE_COUPON +
                    " (cid,name,photo,_data,inscription,content,state) values (?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, coupon.cid);
            ps.setString(2, coupon.name);
            ps.setBytes(3, coupon.photo);
            ps.setString(4, coupon._data);
            ps.setString(5, coupon.inscription);
            ps.setString(6, coupon.content);
            ps.setString(7, coupon.state);
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.closeAll(null, ps, conn);
        }
        return false;
    }

    //删除 sql:delete from 表名 where 条件
    public boolean delete(CouponInfo coupon) {
        conn = DBUtils.getConnection();
        String sql;
        try {
            sql = "delete from " + Constants.TABLE_COUPON + " where _id =" + coupon._id;
            ps = conn.prepareStatement(sql);
            return ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.closeAll(null, ps, conn);
        }
        return false;
    }

    //更新 sql:update 表名 set 修改内容 where 条件
    public void update() {
   /*     db.execSQL("update " + Constants.TABLE_USER + " set 名字 = '祝学梁'");
        db.execSQL("update " + Constants.TABLE_USER + " set 账号 = 2020");

        db.close();*/
    }

    //查询所有券 sql:select * from 表名
    public List<CouponInfo> query() {
        //加载驱动并创建连接对象
        conn = DBUtils.getConnection();
        List<CouponInfo> maps = new ArrayList<>();
        String sql;
        try {
            sql = "select * from " + Constants.TABLE_COUPON;
            ps = conn.prepareStatement(sql);
            // 执行sql查询语句并返回结果集
            rs = ps.executeQuery();
            if (rs != null) {
                //int count = rs.getMetaData().getColumnCount();
                while (rs.next()) {
                    CouponInfo map = new CouponInfo();
                    map._id = rs.getInt(1);
                    map.cid = rs.getInt(2);
                    map.name = rs.getString(3);
                    map.photo = rs.getBytes(4);
                    map._data = rs.getString(5);
                    map.inscription = rs.getString(6);
                    map.content = rs.getString(7);
                    map.state = rs.getString(8);
                    /*添加新的类型的数据*/
                    maps.add(map);
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.closeAll(rs, ps, conn);
        }
        return maps;
    }

}
