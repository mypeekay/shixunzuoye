package com.peekay.shixun.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.peekay.shixun.R;
import com.peekay.shixun.bean.BookKeepBean;

import java.text.DecimalFormat;
import java.util.List;

public class BookKeepLVAdapter extends BaseAdapter {
    List<BookKeepBean> bookKeepBeans;
    Context context;
    int i;

    public BookKeepLVAdapter(List<BookKeepBean> bookKeepBeans, Context context, int i) {
        this.bookKeepBeans = bookKeepBeans;
        this.context = context;
        this.i = i;
    }

    @Override
    public int getCount() {
        return bookKeepBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return bookKeepBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(i, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView_type = view.findViewById(R.id.img_bookkeep);
            viewHolder.textView_title = view.findViewById(R.id.tv_title_bookkeep);
            viewHolder.textView_money = view.findViewById(R.id.tv_money_bookkeep);
            viewHolder.textView_remark = view.findViewById(R.id.tv_remark_bookkeep);
            viewHolder.textView_time = view.findViewById(R.id.tv_bk_time);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textView_title.setText(bookKeepBeans.get(position).getTitle());
        //判断时间是否相同
        viewHolder.textView_time.setText(bookKeepBeans.get(position).getTime());
        if (position > 0) {
            if (bookKeepBeans.get(position).getTime().equals(bookKeepBeans.get(position - 1).getTime())) {
                viewHolder.textView_time.setVisibility(View.GONE);
            } else {
                viewHolder.textView_time.setVisibility(View.VISIBLE);
            }
        }else {
            viewHolder.textView_time.setText(bookKeepBeans.get(position).getTime());
            viewHolder.textView_time.setVisibility(View.VISIBLE);
        }
        //判断是否有备注
        if (!bookKeepBeans.get(position).getRemark().isEmpty()) {
            viewHolder.textView_remark.setText(bookKeepBeans.get(position).getRemark());
            viewHolder.textView_remark.setVisibility(View.VISIBLE);
        } else {
            viewHolder.textView_remark.setVisibility(View.GONE);
        }
        //格式化金额并设置不同类型
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        if (bookKeepBeans.get(position).getType() == 0) {
            viewHolder.imageView_type.setImageResource(R.drawable.bookkeep_red);//支出
            viewHolder.textView_money.setText("￥-" + decimalFormat.format(bookKeepBeans.get(position).getMoney()));
            viewHolder.textView_money.setTextColor(Color.parseColor("#F44336"));
        } else {
            viewHolder.imageView_type.setImageResource(R.drawable.bookkeep_green);//收入
            viewHolder.textView_money.setText("￥" + decimalFormat.format(bookKeepBeans.get(position).getMoney()));
            viewHolder.textView_money.setTextColor(Color.parseColor("#4CAF50"));
        }
        return view;
    }

    class ViewHolder {
        ImageView imageView_type;
        TextView textView_title, textView_money, textView_remark, textView_time;
    }
}
