package com.peekay.shixun.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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

public class AddBookKeep extends Fragment {
    View view;
    GridView gridView;
    List<ADDBK> addbks = new ArrayList<>();
    ADDBKAdapter addbkAdapter;
    String item = "工资";
    int index = -1;

    public String getItem() {
        return item;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.addbookkeep, null);
            initView();
        }
        return view;
    }

    private void initView() {
        gridView = view.findViewById(R.id.gd_addkp);
        addbks.add(new ADDBK(R.drawable.wage, "工资"));
        addbks.add(new ADDBK(R.drawable.lifemoney, "生活费"));
        addbks.add(new ADDBK(R.drawable.redbag, "收红包"));
        addbks.add(new ADDBK(R.drawable.othermoney, "外快"));
        addbks.add(new ADDBK(R.drawable.stock, "股票基金"));
        addbks.add(new ADDBK(R.drawable.other, "其他"));
        addbkAdapter = new ADDBKAdapter(getContext(), R.layout.addbk_item, addbks);
        gridView.setAdapter(addbkAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                item = addbks.get(i).getItem();
                if (index == -1) {
                    Log.d("sss123", "onItemClick: "+index);
                    View view1 = adapterView.getChildAt(i);
                    TextView textView = view1.findViewById(R.id.tv_addbk);
                    textView.setTextColor(Color.rgb(219, 68, 55));
                    index = i;
                    Log.d("sss123", "onItemClick: "+index);
                } else {
                    Log.d("sss123", "onItemClick: "+index);
                    View view1 = adapterView.getChildAt(index);
                    TextView textView = view1.findViewById(R.id.tv_addbk);
                    textView.setTextColor(Color.rgb(0, 0, 0));
                    view1 = adapterView.getChildAt(i);
                    textView = view1.findViewById(R.id.tv_addbk);
                    textView.setTextColor(Color.rgb(219, 68, 55));
                    index = i;
                    Log.d("sss123", "onItemClick: "+index);
                }
            }
        });
    }
}
