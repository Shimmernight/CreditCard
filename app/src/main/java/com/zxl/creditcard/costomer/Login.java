package com.zxl.creditcard.costomer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.zxl.creditcard.R;
import com.zxl.creditcard.costomer.data.UserDao;
import com.zxl.creditcard.costomer.view.MainMenu;

import java.util.HashMap;
import java.util.Map;

import static com.zxl.creditcard.utils.HttpRequest.postRequestWithAuth;

/**
 * @项目名称: CreditCard
 * @包名: com.zxl.creditcard
 * @作者: 祝学梁
 * @创建时间: 2020/3/2  15:01
 * @描述: ${登录}
 **/
public class Login extends Activity {
    String TAG = "Login";
    EditText log_name;
    EditText log_key;
    UserDao mUserDao;
    private String userName;
    private String password;
    private boolean LoginOk;

    String url = "http://101.201.143.123:8080/loginServlet";
    Map<String,Object> connect = new HashMap<>();
    String state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        log_name = findViewById(R.id.log_name);
        log_key = findViewById(R.id.log_key);
        mUserDao = new UserDao(this);

    }

    //登录按钮
    public void login(View view) {
        userName = log_name.getText().toString();
        password = log_key.getText().toString();
        if (userName.equals("")) { // 账号为空时
            Toast.makeText(Login.this, "请输入账号", Toast.LENGTH_SHORT)
                    .show();
        } else if (password.equals("")) {// 密码为空时
            Toast.makeText(Login.this, "请输入密码", Toast.LENGTH_SHORT)
                    .show();
        } else {// 账号和密码都不为空时
            Logining();
        }
    }

    //跳转注册
    public void toRegister(View view) {
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
    }

    //定义一个进度条
    ProgressDialog pd;

    //1.设置一个进度条
    public void showProgressDialog() {
        pd = new ProgressDialog(Login.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle("正在登录");
        pd.setMessage("请稍后...");
        pd.show();
    }

    //2.不耗时操作放在这
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (LoginOk) {
                Toast.makeText(Login.this, "登陆成功", Toast.LENGTH_SHORT).show();
                Toast.makeText(Login.this, "Hello " + userName, Toast.LENGTH_SHORT).show();
                //跳转
                Intent intent = new Intent(Login.this, MainMenu.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(Login.this, "用户名或密码输入错误", Toast.LENGTH_SHORT).show();
            }
            pd.dismiss();
        }
    };

    //3.耗时操作放子线程进行
    public void Logining() {
        new Thread() {
            @Override
            public void run() {
                //传入数据
                connect.put("userName",userName);
                connect.put("password",password);
                try {
                    //登录状态
                    state = postRequestWithAuth(url,connect);
                    switch (state){
                        case "登录成功":
                            LoginOk = true;
                            break;
                        case "登录失败":
                            LoginOk = false;
                            break;
                        default:
                            break;
                    }
                    //获取用户数据

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG,"链接失效");
                }
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


}
