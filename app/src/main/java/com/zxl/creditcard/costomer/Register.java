package com.zxl.creditcard.costomer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.zxl.creditcard.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static com.zxl.creditcard.utils.HttpRequest.postRequestWithAuth;

/**
 * @项目名称: CreditCard
 * @包名: com.zxl.creditcard.costomer
 * @作者: 祝学梁
 * @创建时间: 2020/3/2  15:03
 * @描述: ${注册}
 **/
public class Register extends Activity {
    EditText reg_name;
    EditText reg_key;
    EditText reg_reKey;
    EditText reg_phone_number;
    //UserDao mUserDao;
    String url = "http://101.201.143.123:8080/registerServlet";
    Map<String,Object> connect = new HashMap<>();
    JSONObject res;
    String newName;
    String password;
    String newPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //mUserDao = new UserDao(this);
        reg_name = (EditText) findViewById(R.id.reg_name);
        reg_key = (EditText) findViewById(R.id.reg_key);
        reg_phone_number = (EditText) findViewById(R.id.reg_phone_number);
        reg_reKey = (EditText) findViewById(R.id.reg_reKey);
        setOnFocusChangeErrMsg(reg_phone_number, "phone", "手机号不合法");
        setOnFocusChangeErrMsg(reg_key, "password", "密码小于6位数");
        setOnFocusChangeErrMsg(reg_reKey, "rePassword", "输入不一致");
    }

    //注册按钮
    public void register(View view) {
        newName = reg_name.getText().toString();
        newPhone = reg_phone_number.getText().toString();
        password = reg_key.getText().toString();
        //传入数据
        connect.put("userName",newName);
        connect.put("password",password);
        connect.put("phone",newPhone);

        if (registerIsOk()){
            test();
        }
    }

    //注册是否成功
    private boolean registerIsOk() {
        if (reg_name.getText().toString().equals("")){
            Toast.makeText(this,"用户名不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (reg_phone_number.getText().toString().equals("")){
            Toast.makeText(this,"手机号不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (reg_key.getText().toString().equals("")){
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (reg_reKey.getText().toString().equals("")){
            Toast.makeText(this,"确认密码不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }if (!isTelphoneValid(reg_phone_number.getText().toString())){
            Toast.makeText(this,"手机号不合法",Toast.LENGTH_SHORT).show();
            return false;
        }if (!isPasswordValid(reg_key.getText().toString())){
            Toast.makeText(this,"密码小于6位数",Toast.LENGTH_SHORT).show();
            return false;
        }if (!checkRePassword(reg_reKey.getText().toString())){
            Toast.makeText(this,"输入不一致",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void setOnFocusChangeErrMsg(final EditText editText, final String inputType, final String errMsg) {
        editText.setOnFocusChangeListener(
                new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        //输入内容
                        String inputStr = editText.getText().toString();
                        //正确icon
                        Drawable icon = getResources().getDrawable(R.drawable.input_ok);
                        icon.setBounds(0, 0, 72, 72);
                        if (!inputStr.equals("") &&!hasFocus) {
                            if (inputType.equals("name")) {
                                    reg_name.setError("输入正确",icon);
                                }
                            }
                            if (inputType.equals("phone")) {
                                if (isTelphoneValid(inputStr)) {
                                    reg_phone_number.setError("输入正确", icon);
                                } else {
                                    reg_phone_number.setError(errMsg);
                                    }
                            }
                            if (inputType.equals("password")) {
                                if (isPasswordValid(inputStr)) {
                                    reg_key.setError("输入正确",icon);
                                } else {
                                    reg_key.setError(errMsg);
                                }
                            }
                            if (inputType.equals("rePassword")) {
                                if (checkRePassword(inputStr)) {
                                    reg_reKey.setError("输入正确",icon);
                                } else {
                                    reg_reKey.setError(errMsg);
                                }
                            }
                        }

                }
        );
    }

    //检查二次输入是否相同
    private boolean checkRePassword(String rePassword) {
        return rePassword != null && rePassword.equals(reg_key.getText().toString());
    }

    // 校验账号不能为空且必须是中国大陆手机号（宽松模式匹配）
    private boolean isTelphoneValid(String account) {
        if (account == null) {
            return false;
        }
        // 首位为1, 第二位为3-9, 剩下九位为 0-9, 共11位数字
        String pattern = "^[1]([3-9])[0-9]{9}$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(account);
        return m.matches();
    }

    // 校验密码不少于6位
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    public void back(View view) {
        finish();
    }

    //定义一个进度条
    ProgressDialog pd;

    //1.设置一个进度条
    public void showProgressDialog() {
        pd = new ProgressDialog(Register.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle("正在注册");
        pd.setMessage("请稍后...");
        pd.show();
    }

    boolean registerOK = false;
    //2.不耗时操作放在这
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (registerOK){
                Toast.makeText(Register.this, "注册成功", Toast.LENGTH_SHORT).show();
                //跳回登录
                finish();
            } else {
                Toast.makeText(Register.this, "注册失败", Toast.LENGTH_SHORT).show();
            }
            pd.dismiss();
        }
    };

    //3.耗时操作放子线程进行
    public void test() {
        new Thread() {
            @Override
            public void run() {
                try {
                    //状态
                    res = postRequestWithAuth(url,connect);
                    if (res!=null) {
                        registerOK = res.optBoolean("OK");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Toast.makeText(Register.this, "连接超时,请检查url", Toast.LENGTH_SHORT).show();
                }
                handler.sendEmptyMessage(1);//完成后发送消息
            }
        }.start();//子线程
        showProgressDialog();//UI线程
    }

}