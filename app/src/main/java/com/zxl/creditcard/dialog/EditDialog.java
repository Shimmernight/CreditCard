package com.zxl.creditcard.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zxl.creditcard.R;

/**
 * @项目名称: CreditCard
 * @包名: com.zxl.creditcard.dialog
 * @作者: 祝学梁
 * @创建时间: 2020/2/29  19:45
 * @描述: ${编辑弹出框}
 **/
public class EditDialog extends Dialog {
    // 上下文对象
    Activity context;
    // 三个控件
    private Button btn_save;
    private TextView text_desc;
    public EditText text_info;
    // 点击事件
    private View.OnClickListener mClickListener;
    // 构造方法

    public EditDialog(Activity context) {
        super(context);
        this.context = context;
    }


    public EditDialog(Activity context, int theme, View.OnClickListener clickListener) {
        super(context, theme);
        this.context = context;
        this.mClickListener = clickListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指定布局
        this.setContentView(R.layout.edit_dialog);

        text_desc = findViewById(R.id.text_desc);
        text_info = findViewById(R.id.text_info);

        // 获取窗口对象
        Window dialogWindow = this.getWindow();

        WindowManager m = context.getWindowManager();
        // 获取屏幕宽、高用
        Display d = m.getDefaultDisplay();
        // 获取对话框当前的参数值
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
        // 宽度设置为屏幕的0.8
        p.width = (int) (d.getWidth() * 0.8);
        dialogWindow.setAttributes(p);
        // 根据id在布局中找到控件对象
        btn_save = findViewById(R.id.btn_save);
        // 为按钮绑定点击事件监听器
        btn_save.setOnClickListener(mClickListener);

        this.setCancelable(true);

    }

}