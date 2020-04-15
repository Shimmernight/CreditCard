package com.zxl.creditcard.costomer.entity;

import java.util.Arrays;

/**
 * @项目名称: CreditCard
 * @包名: com.zxl.creditcard.costomer.entity
 * @作者: 祝学梁
 * @创建时间: 2020/2/29  9:10
 * @描述: ${TODO}
 **/
public class CouponInfo {
    //序号
    public int _id;
    //外键
    public int cid;
    //name
    public String name;
    //照片
    public byte[] photo;
    //日期
    public String _data;
    //描述
    public String inscription;
    //内容
    public String content;
    //状态
    public String state;

    @Override
    public String toString() {
        return "CouponInfo{" +
                "_id=" + _id +
                ", cid=" + cid +
                ", name='" + name + '\'' +
                ", photo=" + Arrays.toString(photo) +
                ", _data='" + _data + '\'' +
                ", inscription='" + inscription + '\'' +
                ", content='" + content + '\'' +
                ", state='" + state + '\'' +
                '}';
    }


}
