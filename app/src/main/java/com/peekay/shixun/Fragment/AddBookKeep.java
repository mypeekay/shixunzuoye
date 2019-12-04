package com.peekay.shixun.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

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
    ListView listView;
    List<ADDBK> addbks = new ArrayList<>();
    ADDBKAdapter addbkAdapter;
    String item = "工资";

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
        listView = view.findViewById(R.id.lv_addkp);
        addbks.add(new ADDBK(R.drawable.wage, "工资"));
        addbks.add(new ADDBK(R.drawable.lifemoney, "生活费"));
        addbks.add(new ADDBK(R.drawable.redbag, "收红包"));
        addbks.add(new ADDBK(R.drawable.othermoney, "外快"));
        addbks.add(new ADDBK(R.drawable.stock, "股票基金"));
        addbks.add(new ADDBK(R.drawable.other, "其他"));
        addbkAdapter = new ADDBKAdapter(getContext(), R.layout.addbk_item, addbks);
        listView.setAdapter(addbkAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                item = addbks.get(i).getItem();
            }
        });
    }
}
