package com.peekay.shixun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.peekay.shixun.tools.APP;
import com.peekay.shixun.tools.LoadingDialog;
import com.peekay.shixun.tools.MD5Utils;
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

public class SetPWDActivity extends AppCompatActivity implements View.OnClickListener {
    String token;
    AlertDialog dialog;
    private EditText editText_pass, editText_passagain;
    private Button button_skip, button_setpwd;
    private ImageView imageView_show;
    LoadingDialog loadingDialog;
    int i = 0;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    dialog = loadingDialog.ShowDialog();
                    dialog.show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_set_pwd);
        token = getIntent().getStringExtra("token");
        loadingDialog = new LoadingDialog(this);
        initView();
    }

    private void initView() {
        editText_pass = findViewById(R.id.edit_setpass);
        editText_passagain = findViewById(R.id.edit_setpassagain);
        imageView_show = findViewById(R.id.image_setpass);
        imageView_show.setOnClickListener(this);
        button_skip = findViewById(R.id.btn_skip);
        button_skip.setOnClickListener(this);
        button_setpwd = findViewById(R.id.btn_setpwd);
        button_setpwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_setpass:
                if (i == 0) {
                    i = 1;
                    imageView_show.setImageResource(R.drawable.pass_visible);
                    editText_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    editText_passagain.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    i = 0;
                    imageView_show.setImageResource(R.drawable.pass);
                    editText_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    editText_passagain.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.btn_skip:
                finish();
                break;
            case R.id.btn_setpwd:
                if (editText_pass.getText().toString().equals(editText_passagain.getText().toString())) {
                    if (editText_pass.getText().toString().length() >= 6) {
                        setPWD();
                    } else {
                        Toast.makeText(SetPWDActivity.this, "密码长度不足6位！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SetPWDActivity.this, "两次输入的密码不一致！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //TODO 设置密码不行，可能是接口原因
    public void setPWD() {
        handler.sendEmptyMessage(1);
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .url(APP.BASE_URI + "user/setpwd?token" + token + "&pwd=" + MD5Utils.getMD5(editText_pass.getText().toString()))
                .get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("sss123", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    Log.d("sss123", "onResponse: " + jsonObject);
                    if (jsonObject.optInt("code") == 200) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SetPWDActivity.this, "设置密码成功！", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                        finish();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SetPWDActivity.this, "设置密码失败！", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        });
//        OkHttpUtils.get().url(APP.BASE_URI + "user/setpwd")
//                .addParams("token", token).
//                addParams("pwd", MD5Utils.getMD5(editText_pass.getText().toString())).build()
//                .buildCall(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        Log.d("sss123", "onError: " + e.getMessage());
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            Log.d("sss123", "onResponse: "+jsonObject);
//                            if (jsonObject.optInt("code") == 200) {
//                                Toast.makeText(SetPWDActivity.this, "设置密码成功！", Toast.LENGTH_SHORT).show();
//                                finish();
//                            } else {
//                                Toast.makeText(SetPWDActivity.this, "设置密码失败！", Toast.LENGTH_SHORT).show();
//                            }
//                            dialog.dismiss();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
    }
}
