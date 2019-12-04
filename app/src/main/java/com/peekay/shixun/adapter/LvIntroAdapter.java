package com.peekay.shixun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.peekay.shixun.R;
import com.peekay.shixun.bean.Lv_Intro;

import java.util.List;

public class LvIntroAdapter extends BaseAdapter {
    List<Lv_Intro> intros;
    Context context;
    int i;

    public LvIntroAdapter(List<Lv_Intro> intros, Context context, int i) {
        this.intros = intros;
        this.context = context;
        this.i = i;
    }

    @Override
    public int getCount() {
        return intros.size();
    }

    @Override
    public Object getItem(int position) {
        return intros.get(position);
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
            viewHolder.textView_title = view.findViewById(R.id.tv_intro_title);
            viewHolder.textView_content = view.findViewById(R.id.tv_intro_content);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textView_title.setText(intros.get(position).getTitle());
        viewHolder.textView_content.setText(intros.get(position).getContent());
        return view;
    }

    class ViewHolder {
        TextView textView_title, textView_content;
    }
}
