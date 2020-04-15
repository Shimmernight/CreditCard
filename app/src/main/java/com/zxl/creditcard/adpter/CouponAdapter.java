package com.zxl.creditcard.adpter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxl.creditcard.R;
import com.zxl.creditcard.costomer.entity.CouponInfo;

import java.util.List;
/**
 * assets页面适配器
 */
public class CouponAdapter extends BaseAdapter {
    List<CouponInfo> list;
    LayoutInflater inflater;
    Context context;

    public CouponAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setList(List<CouponInfo> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        /*
         *当数据还没加载完时,可能引起空指针错误
         * 通过加入判断解决
         * */
        return (list == null) ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.coupon_item, null);
            holder = new ViewHolder();
            holder.photo =  convertView.findViewById(R.id.item_photo);
            holder.name =  convertView.findViewById(R.id.item_name);
            holder._data = convertView.findViewById(R.id.item_data);
            holder.inscription =  convertView.findViewById(R.id.item_inscription);
            holder.content =  convertView.findViewById(R.id.item_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //设置数据
        CouponInfo coupon = list.get(position);

        //byte转bitmap
        if (coupon.photo!=null) {
            holder.photo.setImageBitmap(Bytes2Bimap(coupon.photo));
        }
        holder.name.setText(coupon.name);
        holder._data.setText(coupon._data);
        holder.inscription.setText(coupon.inscription);
        holder.content.setText(coupon.content);
        return convertView;
    }

    private Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    public class ViewHolder {
        ImageView photo;
        TextView name;
        TextView _data;
        TextView inscription;
        TextView content;
    }
}
