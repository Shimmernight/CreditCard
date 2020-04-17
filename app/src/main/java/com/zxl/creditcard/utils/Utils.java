package com.zxl.creditcard.utils;

import android.content.Context;

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
    public static List<CouponInfo> getCouponInfo(int cid) {
        List<CouponInfo> list;
        CouponDao couponDao = new CouponDao();
        list = couponDao.query(cid);
        return list;
    }

    /**
     * 上传数据
     */
    public static boolean putCouponInfo(CouponInfo couponInfo) {
        CouponDao couponDao = new CouponDao();
        return couponDao.insert(couponInfo);
    }

    /**
     * 删除数据
     */
    public static boolean deleteCouponInfo(CouponInfo couponInfo) {
        CouponDao couponDao = new CouponDao();
        return couponDao.delete(couponInfo);
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
