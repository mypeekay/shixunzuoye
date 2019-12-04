package com.peekay.shixun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.peekay.shixun.R;
import com.peekay.shixun.bean.Lv_Home;

import java.util.List;

public class LvHomeAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<Lv_Home> homes;

    public LvHomeAdapter(Context context, int layout, List<Lv_Home> homes) {
        this.context = context;
        this.layout = layout;
        this.homes = homes;
    }

    @Override
    public int getCount() {
        return homes.size();
    }

    @Override
    public Object getItem(int i) {
        return homes.get(i);
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
            viewHolder.ratingBar_star = view1.findViewById(R.id.rb_star);
            viewHolder.imageView_pic = view1.findViewById(R.id.image_home);
            viewHolder.textView_name = view1.findViewById(R.id.tv_home_name);
            viewHolder.textView_english = view1.findViewById(R.id.tv_home_english);
            viewHolder.textView_add = view1.findViewById(R.id.tv_home_address);
            view1.setTag(viewHolder);
        } else {
            view1 = view;
            viewHolder = (ViewHolder) view1.getTag();
        }
        viewHolder.ratingBar_star.setRating(homes.get(i).getCommentUserRated());
        Glide.with(view1).load(homes.get(i).getPicture()).into(viewHolder.imageView_pic);
        viewHolder.textView_name.setText(homes.get(i).getName());
        viewHolder.textView_english.setText(homes.get(i).getEname());
        viewHolder.textView_add.setText(homes.get(i).getAddress());
        return view1;
    }

    class ViewHolder {
        RatingBar ratingBar_star;
        TextView textView_name, textView_english, textView_add;
        ImageView imageView_pic;
    }
}
