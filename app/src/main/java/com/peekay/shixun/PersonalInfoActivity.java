package com.peekay.shixun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
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
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(PersonalInfoActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                    getInfo();
                    break;
                case 2:
                    Toast.makeText(PersonalInfoActivity.this, "修改失败！", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

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
                final EditText editText = new EditText(this);
                editText.setMaxLines(3);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (charSequence.length() > 20) {
                            editText.setError("输入名字太长了！");
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                });
                new AlertDialog.Builder(this).setView(editText).setTitle("修改姓名")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (editText.length() > 20) {
                                    Toast.makeText(PersonalInfoActivity.this, "输入的名字太长了！", Toast.LENGTH_SHORT).show();
                                } else {
                                    name = editText.getText().toString();
                                    postInfo();
                                }
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).show();
                break;
            case R.id.tv_perinfo_sex:
                final String[] sexs = new String[]{"男", "女"};
                final String[] sexs1 = new String[]{"m", "f"};
                int i;
                if (sex.equals("f")) {
                    i = 1;
                } else {
                    i = 0;
                }
                dialog = new AlertDialog.Builder(this).setTitle("选择性别").
                        setSingleChoiceItems(sexs, i, new DialogInterface.OnClickListener() {
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
        name = sharedPreferences.getString("nickname", "null");
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
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .url("http://ven6.com/user/setUserinfo?token=" + token + "&sex="
                        + sex + "&birthday=" + birthday + "&nickname=" + name)
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("sss123", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.optInt("code") == 200) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("sex", sex);
                        editor.putString("birthday", birthday);
                        editor.putString("nickname", name);
                        editor.commit();
                        handler.sendEmptyMessage(1);
                    } else {
                        handler.sendEmptyMessage(2);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
