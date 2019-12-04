package com.peekay.shixun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.peekay.shixun.adapter.LvIntroAdapter;
import com.peekay.shixun.bean.ImageBean;
import com.peekay.shixun.bean.Lv_Intro;
import com.peekay.shixun.tools.APP;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

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

public class IntroActivity extends AppCompatActivity {
    int selfid;
    private ImageView imageView_mainpic;
    private TextView textView_name;
    LvIntroAdapter introAdapter;
    private ListView listView;
    private Button button_allpic;
    List<Lv_Intro> intros = new ArrayList<>();
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        JSONObject object = jsonObject.getJSONObject("scenicspot");
                        textView_name.setText(object.optString("name"));
                        Log.d("sss123", "onResponse: " + object.optString("main_pic"));
                        Glide.with(IntroActivity.this).load(object.optString("main_pic"))
                                .into(imageView_mainpic);
                        textView_name.setVisibility(View.VISIBLE);
                        imageView_mainpic.setVisibility(View.VISIBLE);
                        JSONArray jsonArray = jsonObject.getJSONArray("maps");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            intros.add(new Lv_Intro(
                                    jsonObject.optString("title"),
                                    jsonObject.optString("content")
                            ));
                        }
                        introAdapter = new LvIntroAdapter(intros, IntroActivity.this, R.layout.lv_intro_item);
                        listView.setAdapter(introAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_intro);
        selfid = getIntent().getIntExtra("selfid", -1);
        initView();
        loadData();
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

    private void initView() {
        imageView_mainpic = findViewById(R.id.image_intro_mainpic);
        imageView_mainpic.setVisibility(View.GONE);
        textView_name = findViewById(R.id.tv_intro_name);
        textView_name.setVisibility(View.GONE);
        listView = findViewById(R.id.lv_intro);
        button_allpic = findViewById(R.id.btn_all_pic);
        button_allpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntroActivity.this, AllPicActivity.class);
                intent.putExtra("selfid", selfid);
                startActivity(intent);
            }
        });
    }
}
