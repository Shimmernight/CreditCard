package com.zxl.creditcard.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.zxl.creditcard.costomer.data.CouponDao;
import com.zxl.creditcard.costomer.entity.CouponInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @项目名称: Library
 * @包名: com.zxl.library.util
 * @作者: 祝学梁
 * @创建时间: 2020/2/6  15:03
 * @描述: ${工具类}
 **/
public class Utils {
    /**
     * 获取数据
     */
    public static List<CouponInfo> getCouponInfo(int cid, int page) {
        List<CouponInfo> list = null;
        CouponDao couponDao = new CouponDao();
        switch (page){
            case 2:
                list = couponDao.query(cid,"");
                break;
            case 3:
                list = couponDao.query(cid,"not");
                break;
        }
        return list;
    }
    /**
     * 获取头像
     */
    public static byte[] getLogo(int id) {
        CouponDao couponDao = new CouponDao();
        byte[] bytes = couponDao.query(id);
        return bytes;
    }

    /**
     * 上传数据
     */
    public static boolean putCouponInfo(CouponInfo couponInfo) {
        CouponDao couponDao = new CouponDao();
        return couponDao.insert(couponInfo);
    }

    /**
     * 修改头像
     */
    public static boolean setLogo(int id,byte[] bytes) {
        CouponDao couponDao = new CouponDao();
        return couponDao.update(id,bytes);
    }

    /**
     * 删除数据
     */
    public static boolean deleteCouponInfo(CouponInfo couponInfo) {
        CouponDao couponDao = new CouponDao();
        return couponDao.delete(couponInfo);
    }

    /**
     * 修改数据
     */
    public static boolean updateCouponInfo(CouponInfo couponInfo,int id,String userName) {
        CouponDao couponDao = new CouponDao();
        if (id>0){
            return couponDao.update(couponInfo,id,userName);
        }else return false;
    }

    /**
     * 查询数据
     */
    public static int queryID(String name) {
        CouponDao couponDao = new CouponDao();
        return couponDao.query(name);
    }

    /**
     * 搜索
     *
     * @param list 数据
     * @param key  关键词
     * @return
     */
    public static List<CouponInfo> getSearchResult(List<CouponInfo> list, String key) {
        List<CouponInfo> result = new ArrayList<>();
        //用for循环扫描list
        for (int i = 0; i < list.size(); i++) {
            CouponInfo couponInfo = list.get(i);
            //用contains方法搜索关键词
            if (couponInfo.name.toLowerCase().contains(key.toLowerCase())) {
                result.add(couponInfo);
            }
        }
        return result;
    }


}
