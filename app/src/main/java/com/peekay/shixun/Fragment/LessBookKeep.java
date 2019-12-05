package com.peekay.shixun.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.peekay.shixun.R;
import com.peekay.shixun.adapter.ADDBKAdapter;
import com.peekay.shixun.bean.ADDBK;

import java.util.ArrayList;
import java.util.List;

public class LessBookKeep extends Fragment {
    View view;
    String item = "三餐";
    GridView gridView;
    List<ADDBK> addbks = new ArrayList<>();
    ADDBKAdapter addbkAdapter;
    int index = -1;

    public String getItem() {
        return item;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.lessbookkeep, null);
            initView();
        }
        return view;
    }

    private void initView() {
        gridView = view.findViewById(R.id.gd_lesskp);
        addbks.add(new ADDBK(R.drawable.food, "三餐"));
        addbks.add(new ADDBK(R.drawable.snak, "零食"));
        addbks.add(new ADDBK(R.drawable.cloth, "衣服"));
        addbks.add(new ADDBK(R.drawable.traffic, "交通"));
        addbks.add(new ADDBK(R.drawable.travel, "旅行"));
        addbks.add(new ADDBK(R.drawable.child, "孩子"));
        addbks.add(new ADDBK(R.drawable.pet, "宠物"));
        addbks.add(new ADDBK(R.drawable.charge, "话费网费"));
        addbks.add(new ADDBK(R.drawable.alcohol, "烟酒"));
        addbks.add(new ADDBK(R.drawable.study, "学习"));
        addbks.add(new ADDBK(R.drawable.repayment, "还款"));
        addbks.add(new ADDBK(R.drawable.daily, "日用品"));
        addbks.add(new ADDBK(R.drawable.house, "住房"));
        addbks.add(new ADDBK(R.drawable.makeup, "美妆"));
        addbks.add(new ADDBK(R.drawable.medical, "医疗"));
        addbks.add(new ADDBK(R.drawable.redbag, "发红包"));
        addbks.add(new ADDBK(R.drawable.car, "汽车/加油"));
        addbks.add(new ADDBK(R.drawable.yule, "娱乐"));
        addbks.add(new ADDBK(R.drawable.digital, "电器数码"));
        addbks.add(new ADDBK(R.drawable.sport, "运动"));
        addbks.add(new ADDBK(R.drawable.other, "其他"));
        addbkAdapter = new ADDBKAdapter(getContext(), R.layout.addbk_item, addbks);
        gridView.setAdapter(addbkAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                item = addbks.get(i).getItem();
                if (index == -1) {
                    View view1 = adapterView.getChildAt(i);
                    TextView textView = view1.findViewById(R.id.tv_addbk);
                    textView.setTextColor(Color.rgb(219, 68, 55));
                    index = i;
                } else {
                    View view1 = adapterView.getChildAt(index);
                    TextView textView = view1.findViewById(R.id.tv_addbk);
                    textView.setTextColor(Color.rgb(0, 0, 0));
                    view1 = adapterView.getChildAt(i);
                    textView = view1.findViewById(R.id.tv_addbk);
                    textView.setTextColor(Color.rgb(219, 68, 55));
                    index = i;
                }
            }
        });
    }
}
