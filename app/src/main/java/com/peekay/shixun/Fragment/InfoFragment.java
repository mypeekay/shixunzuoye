package com.peekay.shixun.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.peekay.shixun.BookkeepActivity;
import com.peekay.shixun.R;
import com.peekay.shixun.SettingActivity;
import com.peekay.shixun.adapter.LvInfoAdapter;
import com.peekay.shixun.bean.Lv_Info;

import java.util.ArrayList;
import java.util.List;

public class InfoFragment extends Fragment {
    View view;
    ListView listView;
    LvInfoAdapter lvInfoAdapter;
    private TextView textView_infoname;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    List<Lv_Info> infos = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_info, null);
            initView();
        }
        return view;
    }

    private void initView() {
        textView_infoname = view.findViewById(R.id.tv_info_name);
        sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        textView_infoname.setText(sharedPreferences.getString("phone", "手机号"));
        listView = view.findViewById(R.id.lv_info);
        infos.add(new Lv_Info("个人信息"));
        infos.add(new Lv_Info("记账小助手"));
        infos.add(new Lv_Info("设置"));
        lvInfoAdapter = new LvInfoAdapter(infos, getContext(), R.layout.lv_info_item);
        listView.setAdapter(lvInfoAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Toast.makeText(getContext(), "null", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        startActivity(new Intent(getContext(), BookkeepActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getContext(), SettingActivity.class));
                        break;
                }
            }
        });
    }
}
