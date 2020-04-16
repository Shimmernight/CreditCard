package com.zxl.creditcard.costomer.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zxl.creditcard.R;
import com.zxl.creditcard.costomer.data.CouponDao;
import com.zxl.creditcard.dialog.EditDialog;
import com.zxl.creditcard.costomer.entity.CouponInfo;
import com.zxl.creditcard.costomer.view.TransPage;
import com.zxl.creditcard.utils.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class TabCouponFragment extends Fragment implements View.OnClickListener {
    View view;

    //输入编辑框
    EditDialog mEditDialog;
    String info;

    //日期
    DateFormat format = DateFormat.getDateTimeInstance();
    Calendar calendar = Calendar.getInstance(Locale.CHINA);

    //储存结果
    CouponInfo couponInfo;

    TextView name;
    ImageView photo;
    TextView data;
    TextView inscription;
    View touch_inscription;
    TextView content;

    byte[] bytes;
    List<Integer> list;

    TransPage transPage;

    public TabCouponFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_coupon, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
      /*  //新页面接收数据
        Bundle bundle = getActivity().getIntent().getExtras();

        list = new ArrayList<>();
        for (int i = 0; i < bundle.size(); i++) {
            //接收id值
            list.add(bundle.getInt("/" + i));
        }*/

        //初始化
        init();

    }
/*

    //判断id是否重复
    public boolean isHave(int id) {
        for (int i = 0; i < list.size(); i++) {
            Log.e("sql", "compare:" + list.get(i));
            Log.e("sql", "compare2:" + id);
            if (list.get(i) == id) {
                return true;
            }
        }
        return false;
    }
*/

    /*键盘响应*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //修改标题
            case R.id.name:
                //Toast.makeText(getActivity(),"name",Toast.LENGTH_SHORT).show();
                showEditDialog(name);
                break;
            //选择图片
            case R.id.photo:
                //打开本地相册
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 2);
                break;
            //修改日期
            case R.id.data:
                showDatePickerDialog(getActivity(), 2, data, calendar);
                //data.setText("有效期: "+"2020.02.25 - 2020.02.25");
                break;
            //修改印章
            case R.id.touch_inscription:
                showEditDialog(inscription);
                break;
            //修改内容
            case R.id.content:
                showEditDialog(content);
                break;
            //保存按钮
            case R.id.btn_OK:
/*                if (name.getText().toString().equals("自定义") |
                        data.getText().toString().equals("有效期:") |
                        inscription.getText().toString().equals("某某某纪念章") |
                        content.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "内容不能为空,请填写完整",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else*/ {
                    couponInfo = new CouponInfo();
                    //获取内容
                    couponInfo.name = name.getText().toString();
                    couponInfo._data = data.getText().toString();
                    couponInfo.inscription = inscription.getText().toString();
                    couponInfo.content = content.getText().toString();
                    couponInfo.photo = bytes;

                    //可能变更的内容
                    couponInfo.cid = 1;
                    couponInfo.state = "未赠送";

                    //不能在主线程中请求网络操作
                    final Handler handler = new Handler() {
                        @Override
                        public void handleMessage(@NonNull Message msg) {
                                //清空还原
                                name.setText("自定义");
                                data.setText("有效期:");
                                inscription.setText("某某某纪念章");
                                content.setText("");
                                photo.setImageURI(null);
                                //跳转到下一页
                                transPage.setSwPage(1);
                                Toast.makeText(getActivity(), "创建成功", Toast.LENGTH_SHORT).show();
                            }
                    };
                    new Thread(){
                        @Override
                        public void run() {
                                Utils.putCouponInfo(couponInfo);
                                handler.sendEmptyMessage(1);//完成后发送消息
                            }
                    }.start();
                }
                break;
            //赠送按钮
            case R.id.btn_giveTo:
                Toast.makeText(getActivity(), "btn_giveTo", Toast.LENGTH_SHORT).show();
                break;
        }

    }


    /**
     * 日期选择
     *
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     */
    private static void showDatePickerDialog(Activity activity, int themeResId, final TextView tv, final Calendar calendar) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        final int nMonth = calendar.get(Calendar.MONTH);
        final int nYear = calendar.get(Calendar.YEAR);
        final int nDay = calendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(activity, themeResId, new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间
                tv.setText("有效期：" + nYear + "." + (nMonth + 1) + "." + nDay + " - "
                        + year + "." + (monthOfYear + 1) + "." + dayOfMonth);
            }
        }
                // 设置初始日期
                , nYear
                , nMonth
                , nDay).show();
    }


    //修改窗口
    TextView byView;

    //显示编辑框
    private void showEditDialog(TextView view) {
        byView = view;
        mEditDialog = new EditDialog(getActivity(), R.style.loading_dialog, onClickListener);
        mEditDialog.show();
    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_save) {
                info = mEditDialog.text_info.getText().toString().trim();
                byView.setText(info);
                mEditDialog.dismiss();
            }
        }
    };

    //返回时
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (data != null) {
                if (data.getData() != null) {
                    // 得到图片的全路径
                    Uri uri = data.getData();
                    photo.setImageURI(uri);
                    if (uri != null) {
                        try {
                            Bitmap bitmap = getBitmapFormUri(getActivity(), uri);
                            bytes = bitmabToBytes(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Log.e("sql", "data");
                    // Toast.makeText(AddActivity.this, "logo不能为空,请填写完整",Toast.LENGTH_SHORT).show();
                }
            }

        }


    }


    /**
     * 通过uri获取图片并进行压缩
     *
     * @param uri
     */
    public static Bitmap getBitmapFormUri(Activity ac, Uri
            uri) throws FileNotFoundException, IOException {
        InputStream input = ac.getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1))
            return null;
        //图片分辨率以480x800为标准
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (originalWidth / ww);
        } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (originalHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //比例压缩
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = be;//设置缩放比例
        bitmapOptions.inDither = true;//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        input = ac.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();

        return compressImage(bitmap);//再进行质量压缩
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }


    //图片转为二进制数据
    public byte[] bitmabToBytes(Bitmap bitmap) {
        //将图片转化为位图
        int size = bitmap.getWidth() * bitmap.getHeight() * 4;
        //创建一个字节数组输出流,流的大小为size
        ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
        try {
            //设置位图的压缩格式，质量为100%，并放入字节数组输出流中
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            //将字节数组输出流转化为字节数组byte[]
            byte[] imagedata = baos.toByteArray();
            return imagedata;
        } catch (Exception e) {
        } finally {
            try {
                bitmap.recycle();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new byte[0];
    }


    /**
     * 初始化
     */
    private void init() {
        //找到对象
        name = view.findViewById(R.id.name);
        photo = view.findViewById(R.id.photo);
        data = view.findViewById(R.id.data);
        touch_inscription = view.findViewById(R.id.touch_inscription);
        inscription = view.findViewById(R.id.inscription);
        content = view.findViewById(R.id.content);

        Button btn_ok = view.findViewById(R.id.btn_OK);
        Button btn_giveTo = view.findViewById(R.id.btn_giveTo);

        name.setOnClickListener(this);
        photo.setOnClickListener(this);
        data.setOnClickListener(this);
        touch_inscription.setOnClickListener(this);
        content.setOnClickListener(this);

        //设置按钮监听器
        btn_ok.setOnClickListener(this);
        btn_giveTo.setOnClickListener(this);
    }

    public void setTransPage(TransPage transPage) {
        this.transPage = transPage;
    }
}
