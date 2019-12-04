package com.peekay.shixun.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.peekay.shixun.IntroActivity;
import com.peekay.shixun.R;
import com.peekay.shixun.adapter.LvHomeAdapter;
import com.peekay.shixun.bean.Lv_Home;
import com.peekay.shixun.tools.APP;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.header.DropBoxHeader;
import com.scwang.smartrefresh.header.FlyRefreshHeader;
import com.scwang.smartrefresh.header.FunGameBattleCityHeader;
import com.scwang.smartrefresh.header.FunGameHitBlockHeader;
import com.scwang.smartrefresh.header.PhoenixHeader;
import com.scwang.smartrefresh.header.TaurusHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class HomeFragment extends Fragment {
    View view;
    SmartRefreshLayout smartRefreshLayout;
    List<Lv_Home> homes = new ArrayList<>();
    LvHomeAdapter lvHomeAdapter;
    private ListView listView_home;
    List<Integer> selfid = new ArrayList<>();
    int index = 1;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    Log.d("sss123", "handleMessage: 刷新ok");
                    lvHomeAdapter.notifyDataSetChanged();
                    smartRefreshLayout.finishRefresh(1500);
                    //TODO 执行刷新数据操作
                    break;
                case 2:
                    Log.d("sss123", "handleMessage: 加载更多ok");
                    lvHomeAdapter.notifyDataSetChanged();
                    smartRefreshLayout.finishLoadMore(1500);
                    //TODO 执行加载更多操作
                    break;
                case 3:
                    Log.d("sss123", "handleMessage: error");
                    smartRefreshLayout.finishRefresh(1500);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home, null);
            initView();
            getList(index);
        }
        return view;
    }

    private void initView() {
        listView_home = view.findViewById(R.id.lv_frag_home);
        lvHomeAdapter = new LvHomeAdapter(getContext(), R.layout.fraghome_lvitem, homes);
        listView_home.setAdapter(lvHomeAdapter);
        listView_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), IntroActivity.class);
                intent.putExtra("selfid", selfid.get(position));
                startActivity(intent);
            }
        });
        smartRefreshLayout = view.findViewById(R.id.refreshlayout);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                index = 1;
                homes.clear();
                selfid.clear();
                getList(index);
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                index++;
                getMoreList(index);
            }
        });
    }

    //获取景点列表
    public void getList(int i) {
        OkHttpUtils.get().url(APP.BASE_URI + "attractions/getlist")
                .addParams("pageNum", String.valueOf(i))
                .addParams("lng", "106.486071")
                .addParams("lat", "29.53751")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.d("sss123", "onError: " + e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optInt("code") == 200) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int j = 0; j < jsonArray.length(); j++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(j);
                            homes.add(new Lv_Home(
                                    jsonObject1.getInt("selfid"),
                                    jsonObject1.getString("address"),
                                    Float.parseFloat(jsonObject1.getString("commentUserRated")),
                                    jsonObject1.getString("ename"),
                                    jsonObject1.getString("id"),
                                    jsonObject1.getString("name"),
                                    jsonObject1.getString("picture"),
                                    jsonObject1.getString("region"),
                                    jsonObject1.getString("typeName"),
                                    jsonObject1.getString("distance")
                            ));
                            selfid.add(jsonObject1.getInt("selfid"));
                        }
                        handler.sendEmptyMessage(1);
                    } else {
                        handler.sendEmptyMessage(3);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //获取更多景点列表
    public void getMoreList(int i) {
        OkHttpUtils.get().url(APP.BASE_URI + "attractions/getlist")
                .addParams("pageNum", String.valueOf(i))
                .addParams("lng", "106.486071")
                .addParams("lat", "29.53751")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.d("sss123", "onError: " + e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optInt("code") == 200) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int j = 0; j < jsonArray.length(); j++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(j);
                            homes.add(new Lv_Home(
                                    jsonObject1.getInt("selfid"),
                                    jsonObject1.getString("address"),
                                    Float.parseFloat(jsonObject1.getString("commentUserRated")),
                                    jsonObject1.getString("ename"),
                                    jsonObject1.getString("id"),
                                    jsonObject1.getString("name"),
                                    jsonObject1.getString("picture"),
                                    jsonObject1.getString("region"),
                                    jsonObject1.getString("typeName"),
                                    jsonObject1.getString("distance")
                            ));
                            selfid.add(jsonObject1.getInt("selfid"));
                        }
                        handler.sendEmptyMessage(2);
                    } else {
                        handler.sendEmptyMessage(3);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
