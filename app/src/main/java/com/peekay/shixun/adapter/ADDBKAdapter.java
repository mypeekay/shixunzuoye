package com.peekay.shixun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.peekay.shixun.R;
import com.peekay.shixun.bean.ADDBK;

import java.util.List;

public class ADDBKAdapter extends BaseAdapter {
    Context context;
    int res;
    List<ADDBK> addbks;

    public ADDBKAdapter(Context context, int i, List<ADDBK> addbks) {
        this.context = context;
        res = i;
        this.addbks = addbks;
    }

    @Override
    public int getCount() {
        return addbks.size();
    }

    @Override
    public Object getItem(int i) {
        return addbks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1;
        ViewHolder viewHolder;
        if (view == null) {
            view1 = LayoutInflater.from(context).inflate(res, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView_icon = view1.findViewById(R.id.image_addbk);
            viewHolder.textView_item = view1.findViewById(R.id.tv_addbk);
            view1.setTag(viewHolder);
        } else {
            view1 = view;
            viewHolder = (ViewHolder) view1.getTag();
        }
        viewHolder.imageView_icon.setImageResource(addbks.get(i).getPic());
        viewHolder.textView_item.setText(addbks.get(i).getItem());
        return view1;
    }

    class ViewHolder {
        ImageView imageView_icon;
        TextView textView_item;
    }
}
