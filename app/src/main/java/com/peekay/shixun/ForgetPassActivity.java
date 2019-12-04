package com.peekay.shixun;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.peekay.shixun.tools.APP;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class ForgetPassActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editText_user, editText_code, editText_pass, editText_pass2;
    private Button button_sure;
    private TextView textView_getcode;
    String yzm = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_forget_pass);
        initView();
    }

    private void initView() {
        editText_user = findViewById(R.id.edit_forget_user);
        editText_code = findViewById(R.id.edit_forget_yzm);
        editText_pass = findViewById(R.id.edit_forget_pass);
        editText_pass2 = findViewById(R.id.edit_forget_pass2);
        button_sure = findViewById(R.id.btn_forget);
        button_sure.setOnClickListener(this);
        textView_getcode = findViewById(R.id.tv_forget_getyzm);
        textView_getcode.setOnClickListener(this);
    }

    //获取验证码
    private void SendForCode(String user) {
        OkHttpUtils.get().url(APP.BASE_URI + "user/sendcode")
                .addParams("email", user).addParams("type", "300")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.d("sss123", "onError: " + e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                Log.d("sss123", "onResponse: " + response);
                yzm = response;
            }
        });
    }

    //设置密码
    private void SetPass(String pass) {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_getyzm:
                SendForCode(editText_user.getText().toString());
                break;
            case R.id.btn_forget:
                if (editText_pass.getText().equals(editText_pass2.getText()) && editText_pass.getText().length() >= 6) {
                    SetPass(editText_pass.getText().toString());
                } else {
                    Toast.makeText(ForgetPassActivity.this, "密码不一致或长度小于6位！", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
