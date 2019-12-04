package com.peekay.shixun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.peekay.shixun.R;
import com.peekay.shixun.bean.Lv_Info;

import java.util.List;

public class LvInfoAdapter extends BaseAdapter {
    List<Lv_Info> lv_infos;
    Context context;
    int layout;

    public LvInfoAdapter(List<Lv_Info> lv_infos, Context context, int layout) {
        this.lv_infos = lv_infos;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return lv_infos.size();
    }

    @Override
    public Object getItem(int i) {
        return lv_infos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = null;
        ViewHolder viewHolder;
        if (view == null) {
            view1 = LayoutInflater.from(context).inflate(layout, null);
            viewHolder = new ViewHolder();
            viewHolder.textView_title = view1.findViewById(R.id.tv_info_item);
            view1.setTag(viewHolder);
        } else {
            view1 = view;
            viewHolder = (ViewHolder) view1.getTag();
        }
        viewHolder.textView_title.setText(lv_infos.get(i).getTitle());
        return view1;
    }

    class ViewHolder {
        TextView textView_title;
    }
}
