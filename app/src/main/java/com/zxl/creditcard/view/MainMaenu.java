package com.zxl.creditcard.view;

/**
 * @项目名称: CreditCard
 * @包名: com.zxl.creditcard
 * @作者: 祝学梁
 * @创建时间: 2020/2/27  22:08
 * @描述: ${TODO}
 **/

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.zxl.creditcard.R;
import com.zxl.creditcard.fragment.TabAccountFragment;
import com.zxl.creditcard.fragment.TabAssetsFragment;
import com.zxl.creditcard.fragment.TabCouponFragment;
import com.zxl.creditcard.fragment.TabReceiveFragment;

public class MainMaenu extends AppCompatActivity implements View.OnClickListener, TabCouponFragment.OnFragmentInteractionListener, TabAssetsFragment.OnFragmentInteractionListener, TabReceiveFragment.OnFragmentInteractionListener, TabAccountFragment.OnFragmentInteractionListener {

    private LinearLayout mOneLin, mTwoLin, mThreeLin,mFourLin;
    private ImageView mOneImg,mTwoImg,mThreeImg, mFourImg;

    private FrameLayout mFrameLayout;
    private TabCouponFragment mOneFragment;
    private TabAssetsFragment mTwoFragment;
    private TabReceiveFragment mThreeFragment;
    private TabAccountFragment mFourFragment;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
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
                    transaction.add(R.id.fl_container, mTwoFragment);
                } else {
                    //如果对应的Fragment已经实例化，则直接显示出来
                    transaction.show(mTwoFragment);
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}