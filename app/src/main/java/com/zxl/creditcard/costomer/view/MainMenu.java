package com.zxl.creditcard.costomer.view;

/**
 * @项目名称: CreditCard
 * @包名: com.zxl.creditcard
 * @作者: 祝学梁
 * @创建时间: 2020/2/27  22:08
 * @描述: ${TODO}
 **/

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.zxl.creditcard.R;
import com.zxl.creditcard.adpter.CouponAdapter;
import com.zxl.creditcard.adpter.DeleteCoupon;
import com.zxl.creditcard.costomer.entity.CouponInfo;
import com.zxl.creditcard.costomer.fragment.TabAccountFragment;
import com.zxl.creditcard.costomer.fragment.TabAssetsFragment;
import com.zxl.creditcard.costomer.fragment.TabCouponFragment;
import com.zxl.creditcard.costomer.fragment.TabReceiveFragment;
import com.zxl.creditcard.utils.Utils;

import java.util.List;

public class MainMenu extends AppCompatActivity implements View.OnClickListener, DeleteCoupon,TransPage{

    String TAG = "MainMenu";
    private LinearLayout mOneLin, mTwoLin, mThreeLin,mFourLin;
    private ImageView mOneImg,mTwoImg,mThreeImg, mFourImg;
    private FrameLayout mFrameLayout;
    private TabCouponFragment mOneFragment;
    private TabAssetsFragment mTwoFragment;
    private TabReceiveFragment mThreeFragment;
    private TabAccountFragment mFourFragment;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    //将经常用的三个对象定义在上面
    List<CouponInfo> list;
    public CouponAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initView();
        //获取FragmentManager对象
        manager = getSupportFragmentManager();
        //获取FragmentTransaction对象
        transaction = manager.beginTransaction();
        setSwPage(0);
    }

    private void initView() {
        mFrameLayout = findViewById(R.id.fl_container);
        mOneLin = findViewById(R.id.one_lin);
        mTwoLin = findViewById(R.id.two_lin);
        mThreeLin = findViewById(R.id.three_lin);
        mFourLin = findViewById(R.id.four_lin);
        mOneImg=findViewById(R.id.one_img);
        mTwoImg=findViewById(R.id.two_img);
        mThreeImg=findViewById(R.id.three_img);
        mFourImg =findViewById(R.id.four_img);
        mOneLin.setOnClickListener(this);
        mTwoLin.setOnClickListener(this);
        mThreeLin.setOnClickListener(this);
        mFourLin.setOnClickListener(this);
        //适配器
        adapter = new CouponAdapter(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.one_lin:
                setSwPage(0);
                break;
            case R.id.two_lin:
                setSwPage(1);
                break;
            case R.id.three_lin:
                setSwPage(2);
                break;
            case R.id.four_lin:
                setSwPage(3);
                break;
        }
    }

    //页面跳转
    public void setSwPage(int i) {
        //获取FragmentManager对象
        manager = getSupportFragmentManager();
        //获取FragmentTransaction对象
        transaction = manager.beginTransaction();
        //先隐藏所有的Fragment
        hideFragments(transaction);
        switch (i) {
            case 0:
                reLinSelect();
                mOneLin.setSelected(true);
                if (mOneFragment == null) {
                    mOneFragment = new TabCouponFragment();
                    mOneFragment.setTransPage(this);
                    transaction.add(R.id.fl_container, mOneFragment);
                } else {
                    //如果对应的Fragment已经实例化，则直接显示出来
                    transaction.show(mOneFragment);
                }
                break;
            case 1:
            reLinSelect();
            mTwoLin.setSelected(true);
            if (mTwoFragment == null) {
                mTwoFragment = new TabAssetsFragment();
                mTwoFragment.setDeletCoupon(this);
                mTwoFragment.setActivity(this);
                transaction.add(R.id.fl_container, mTwoFragment);
                updataData();
            } else {
                //如果对应的Fragment已经实例化，则直接显示出来
                transaction.show(mTwoFragment);
                updataData();
            }
            break;
            case 2:
                reLinSelect();
                mThreeLin.setSelected(true);
                if (mThreeFragment == null) {
                    mThreeFragment = new TabReceiveFragment();
                    transaction.add(R.id.fl_container, mThreeFragment);
                } else {
                    //如果对应的Fragment已经实例化，则直接显示出来
                    transaction.show(mThreeFragment);
                }
                break;
            case 3:
                reLinSelect();
                mFourLin.setSelected(true);
                if (mFourFragment == null) {
                    mFourFragment = new TabAccountFragment();
                    transaction.add(R.id.fl_container, mFourFragment);
                } else {
                    //如果对应的Fragment已经实例化，则直接显示出来
                    transaction.show(mFourFragment);
                }
                break;
        }
        transaction.commit();
    }

    //将四个的Fragment隐藏
    private void hideFragments(FragmentTransaction transaction) {
        if (mOneFragment != null) {
            transaction.hide(mOneFragment);
        }
        if (mTwoFragment != null) {
            transaction.hide(mTwoFragment);
        }
        if (mThreeFragment != null) {
            transaction.hide(mThreeFragment);
        }
        if (mFourFragment != null) {
            transaction.hide(mFourFragment);
        }

    }

    //初始化底部菜单选择状态
    private void reLinSelect(){
        mOneLin.setSelected(false);
        mTwoLin.setSelected(false);
        mThreeLin.setSelected(false);
        mFourLin.setSelected(false);
    }



    //定义一个进度条
    ProgressDialog pd;

    //1.设置一个进度条
    public void showProgressDialog() {
        pd = new ProgressDialog(MainMenu.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle("正在获取数据中");
        pd.setMessage("请稍后...");
        pd.show();
    }

    //2.不耗时操作放在这
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (list.size()!=0) {
               adapter.setList(list);
               adapter.notifyDataSetChanged();
            }else {
                Log.e(TAG,"数据库为空!");
            }
            pd.dismiss();
        }
    };

    //3.耗时操作放子线程进行
    public void updataData() {
        new Thread() {
            @Override
            public void run() {
                //传入数据
                //数据源
                list = Utils.getCouponInfo(MainMenu.this);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(1);//完成后发送消息
            }
        }.start();//子线程
        showProgressDialog();//UI线程
    }

    //实现删除按钮
    @Override
    public void btn_onclick(int pos) {
        //调用接口方法,在Activity实现
        Utils.deleteCouponInfo(list.get(pos));
        updataData();
    }

}