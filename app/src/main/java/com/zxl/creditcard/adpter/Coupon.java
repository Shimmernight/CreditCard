package com.zxl.creditcard.adpter;

/**
 * @项目名称: CreditCard
 * @包名: com.zxl.creditcard.adpter
 * @作者: 祝学梁
 * @创建时间: 2020/4/16  17:45
 * @描述: 删除数据
 **/
public interface Coupon {
    //传入位置，实现删除
    void btn_delete(int pos);
    //传入位置和用户名，实现赠送
    void btn_update(int pos,String name);
}
