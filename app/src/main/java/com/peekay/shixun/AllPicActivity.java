package com.peekay.shixun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.peekay.shixun.adapter.AllPicLVAdapter;
import com.peekay.shixun.bean.ImageBean;
import com.peekay.shixun.tools.APP;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AllPicActivity extends AppCompatActivity {
    int selfid;
    List<ImageBean> imageBeans = new ArrayList<>();
    private LinearLayout linearLayout;
    private ListView listView;
    private TextView textView_allpic;
    AllPicLVAdapter allPicLVAdapter;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        JSONArray jsonArray1 = jsonObject.getJSONArray("imgsBeans");
                        for (int i = 0; i < jsonArray1.length(); i++) {
                            JSONObject object1 = jsonArray1.getJSONObject(i);
                            imageBeans.add(new ImageBean(object1.optString("imgUrl"), object1.optInt("imgh"), object1.optInt("imgw"), linearLayout.getWidth()));
                        }
                        if (imageBeans.isEmpty()) {
                            textView_allpic.setVisibility(View.VISIBLE);
                        } else {
                            textView_allpic.setVisibility(View.GONE);
                        }
                        allPicLVAdapter = new AllPicLVAdapter(AllPicActivity.this, R.layout.allpic_lvitem, imageBeans);
                        listView.setAdapter(allPicLVAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_all_pic);
        selfid = getIntent().getIntExtra("selfid", -1);
        initView();
        loadData();
    }

    private void initView() {
        listView = findViewById(R.id.lv_allpic);
        textView_allpic = findViewById(R.id.tv_pic_allpic);
        linearLayout = findViewById(R.id.line1);
    }

    //获取Json数据
    private void loadData() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(APP.BASE_URI + "attractions/getDetais?id=" + selfid).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string()).getJSONObject("data");
                    Message message = new Message();
                    message.obj = jsonObject;
                    message.what = 1;
                    handler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}