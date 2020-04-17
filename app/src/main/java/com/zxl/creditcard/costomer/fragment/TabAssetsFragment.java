package com.zxl.creditcard.costomer.fragment;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zxl.creditcard.R;
import com.zxl.creditcard.adpter.Coupon;
import com.zxl.creditcard.costomer.view.MainMenu;


public class TabAssetsFragment extends Fragment {
    private static final String TAG = "TabAssetsFragment";
    //将经常用的三个对象定义在上面
    ListView lv;
    private View mview;
    Coupon mCoupon;
    private MainMenu mActivity;

    int pos;

    public void setDeletCoupon(Coupon Coupon) {
        this.mCoupon = Coupon;
    }

    public TabAssetsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.fragment_tab_assets, container, false);
        return mview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //拿到ListView
        lv = (ListView) mview.findViewById(R.id.lv_assets);
        //adapter = new CouponAdapter(mActivity);
        //传入deleteBook对象
        //adapter.setDeleteBook(this);
        lv.setAdapter(mActivity.adapter);
        //按item时
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;//保存位置
                showAlertDialog();
            }
        });
    }

    //定义一个提示框
    AlertDialog.Builder ad;

    //1.设置一个提示框
    private void showAlertDialog() {
        ad = new AlertDialog.Builder(mActivity)
        .setTitle("请选择")
        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Thread() {
                    @Override
                    public void run() {
                        //删除
                        mCoupon.btn_delete(pos);
                    }
                }.start();//子线程
            }
        })
        .setNegativeButton("赠送", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //修改所属人cid和票券状态state
                new Thread() {
                    @Override
                    public void run() {
                        //修改
                        mCoupon.btn_update(pos,"祝学梁");
                    }
                }.start();//子线程
            }
        });
        ad.show();
    }

    public void setActivity(MainMenu mainMenu) {
        this.mActivity = mainMenu;
    }


}