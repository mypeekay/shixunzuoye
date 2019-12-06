package com.peekay.shixun;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.peekay.shixun.tools.APP;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PersonalInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textView_name, textView_sex, textView_birthday;
    private AlertDialog dialog;
    String name, sex, birthday, token;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_personal_info);
        initView();
        getInfo();
    }

    private void initView() {
        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        textView_name = findViewById(R.id.tv_perinfo_name);
        textView_name.setOnClickListener(this);
        textView_sex = findViewById(R.id.tv_perinfo_sex);
        textView_sex.setOnClickListener(this);
        textView_birthday = findViewById(R.id.tv_perinfo_birthday);
        textView_birthday.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_perinfo_name:
                break;
            case R.id.tv_perinfo_sex:
                final String[] sexs = new String[]{"男", "女"};
                final String[] sexs1 = new String[]{"m", "f"};
                dialog = new AlertDialog.Builder(this).
                        setSingleChoiceItems(sexs, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sex = sexs1[i];
                                postInfo();
                                dialog.cancel();
                            }
                        }).show();
                break;
            case R.id.tv_perinfo_birthday:
                final DatePicker datePicker = new DatePicker(this);
                dialog = new AlertDialog.Builder(this).setView(datePicker)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                birthday = datePicker.getYear() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth();
                                postInfo();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialog.cancel();
                            }
                        }).show();
                break;
        }
    }

    //获取信息
    public void getInfo() {
        name = sharedPreferences.getString("张三", "null");
        textView_name.setText(name);
        sex = sharedPreferences.getString("sex", "null");
        token = sharedPreferences.getString("token", "null");
        switch (sex) {
            case "m":
                textView_sex.setText("男");
                break;
            case "f":
                textView_sex.setText("女");
                break;
            default:
                textView_sex.setText(sex);
                break;
        }
        birthday = sharedPreferences.getString("birthday", "null");
        textView_birthday.setText(birthday);
    }

    //提交信息
    public void postInfo() {
        OkHttpClient client=new OkHttpClient.Builder().build();
        Request request=new Request.Builder().url(APP.BASE_URI+"user/setUserinfo")
                .addHeader("token", token)
                .addHeader("sex", sex)
                .addHeader("birthday", birthday)
                .addHeader("nickname", name).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("sss123", "onFailure: "+e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    JSONObject jsonObject=new JSONObject(response.body().string());
                    Log.d("sss123", "onResponse: "+jsonObject);
                    Log.d("sss123", "onResponse: "+token);
                    Log.d("sss123", "onResponse: "+sex);
                    Log.d("sss123", "onResponse: "+name);
                    Log.d("sss123", "onResponse: "+birthday);
                    if (jsonObject.optInt("code") == 200) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("sex", sex);
                        editor.putString("birthday", birthday);
                        editor.putString("nickname", name);
                        editor.commit();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PersonalInfoActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PersonalInfoActivity.this, "修改失败！", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
//        OkHttpUtils.get().url(APP.BASE_URI + "user/setUserinfo")
//                .addParams("token", token)
//                .addParams("sex", sex)
//                .addParams("birthday", birthday)
//                .addParams("nickname", name)
//                .build().buildCall(new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int i) {
//                Log.d("sss123", "onError: " + e.getMessage());
//            }
//
//            @Override
//            public void onResponse(String s, int i) {
//                try {
//                    JSONObject jsonObject = new JSONObject(s);
//                    if (jsonObject.optInt("code") == 200) {
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putString("sex", sex);
//                        editor.putString("birthday", birthday);
//                        editor.putString("nickname", name);
//                        editor.commit();
//                        Toast.makeText(PersonalInfoActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(PersonalInfoActivity.this, "修改失败！", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }
}
