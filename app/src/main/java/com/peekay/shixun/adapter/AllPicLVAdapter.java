package com.peekay.shixun.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.peekay.shixun.R;
import com.peekay.shixun.bean.ImageBean;

import java.util.List;

public class AllPicLVAdapter extends ArrayAdapter<ImageBean> {
    int i;

    public AllPicLVAdapter(Context context, int resource, List<ImageBean> imageBeans) {
        super(context, resource, imageBeans);
        i = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        ImageBean imageBean = getItem(position);
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(i, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = view.findViewById(R.id.image_allpic_item);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        Glide.with(view).load(imageBean.getUrl()).into(viewHolder.imageView);
        viewHolder.imageView.setMaxHeight(imageBean.getImgh() * imageBean.getW() / imageBean.getImgw());
        return view;
    }

    class ViewHolder {
        ImageView imageView;
    }
}
