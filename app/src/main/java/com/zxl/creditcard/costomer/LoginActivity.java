package com.zxl.creditcard.costomer;

/**
 * @项目名称: CreditCard
 * @包名: com.zxl.creditcard
 * @作者: 祝学梁
 * @创建时间: 2020/3/3  10:08
 * @描述: ${TODO}
 **/

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zxl.creditcard.R;

public class LoginActivity extends Activity implements OnClickListener{
    protected static final String TAG = "LoginActivity";

    private Dialog mLoginingDlg; // 显示正在登录的Dialog
    private EditText mIdEditText; // 登录ID编辑框
    private EditText mPwdEditText; // 登录密码编辑框
    private Button mLoginButton; // 登录按钮
    private String mIdString;
    private String mPwdString;
    //private ArrayList<UserInfo> mUsers; // 用户列表


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);
        initView();
        setListener();
        /* 获取已经保存好的用户密码 */
/*        mUsers = UserUtils.getUserList(LoginActivity.this);
        if (mUsers.size() > 0) {
            *//* 将列表中的第一个user显示在编辑框 *//*
            mIdEditText.setText(mUsers.get(0).getId());
            mPwdEditText.setText(mUsers.get(0).getPwd());
        }*/
    }


    private void setListener() {
        mIdEditText.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                mIdString = s.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
        mPwdEditText.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                mPwdString = s.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
        mLoginButton.setOnClickListener(this);
    }

    private void initView() {
        mIdEditText = (EditText) findViewById(R.id.login_edtId);
        mPwdEditText = (EditText) findViewById(R.id.login_edtPwd);
        mLoginButton = (Button) findViewById(R.id.login_btnLogin);
        initLoginingDlg();
    }



    /* 初始化正在登录对话框 */
    private void initLoginingDlg() {

        mLoginingDlg = new Dialog(this, R.style.loginingDlg);
        mLoginingDlg.setContentView(R.layout.logining_dlg);

        Window window = mLoginingDlg.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        // 获取和mLoginingDlg关联的当前窗口的属性，从而设置它在屏幕中显示的位置

        // 获取屏幕的高宽
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int cxScreen = dm.widthPixels;
        int cyScreen = dm.heightPixels;

        int height = (int) getResources().getDimension(
                R.dimen.loginingdlg_height);// 高42dp
        int lrMargin = (int) getResources().getDimension(
                R.dimen.loginingdlg_lr_margin); // 左右边沿10dp
        int topMargin = (int) getResources().getDimension(
                R.dimen.loginingdlg_top_margin); // 上沿20dp

        params.y = (-(cyScreen - height) / 2) + topMargin; // -199
        /* 对话框默认位置在屏幕中心,所以x,y表示此控件到"屏幕中心"的偏移量 */

        params.width = cxScreen;
        params.height = height;
        // width,height表示mLoginingDlg的实际大小

        mLoginingDlg.setCanceledOnTouchOutside(true); // 设置点击Dialog外部任意区域关闭Dialog
    }

    /* 显示正在登录对话框 */
    private void showLoginingDlg() {
        if (mLoginingDlg != null)
            mLoginingDlg.show();
    }

    /* 关闭正在登录对话框 */
    private void closeLoginingDlg() {
        if (mLoginingDlg != null && mLoginingDlg.isShowing())
            mLoginingDlg.dismiss();
    }

    @Override
    public void onClick(View v) {
        // 启动登录
        if (v.getId() == R.id.login_btnLogin) {
            showLoginingDlg(); // 显示"正在登录"对话框,因为此Demo没有登录到web服务器,所以效果可能看不出.可以结合情况使用
            Log.i(TAG, mIdString + "  " + mPwdString);
            if (mIdString == null || mIdString.equals("")) { // 账号为空时
                Toast.makeText(LoginActivity.this, "请输入账号", Toast.LENGTH_SHORT)
                        .show();
            } else if (mPwdString == null || mPwdString.equals("")) {// 密码为空时
                Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT)
                        .show();
            } else {// 账号和密码都不为空时
                boolean mIsSave = true;
                try {
                    Log.i(TAG, "保存用户列表");
                    // 判断本地文档是否有此ID用户
                    /*for (UserInfo user : mUsers) {
                        if (user.getId().equals(mIdString)) {
                            mIsSave = false;
                            break;
                        }
                    }
                    if (mIsSave) { // 将新用户加入users
                        User user = new User(mIdString, mPwdString);
                        mUsers.add(user);
                    }*/

                } catch (Exception e) {
                    e.printStackTrace();
                }
                closeLoginingDlg();// 关闭对话框
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

    }

    /* 退出此Activity时保存users */
/*    @Override
    public void onPause() {
        super.onPause();
        try {
            UserUtils.saveUserList(LoginActivity.this, mUsers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

}
