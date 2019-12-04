package com.peekay.shixun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class SettingActivity extends AppCompatActivity {
    private Button button_exitlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        button_exitlogin = findViewById(R.id.btn_exit_login);
        button_exitlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", "");
                editor.putString("nickname", "");
                editor.putString("avatar", "");
                editor.putString("sex", "");
                editor.putString("birthday", "");
                editor.putString("phone", "");
                editor.putString("token", "");
                editor.putBoolean("ispwd", false);
                editor.commit();
                finish();
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);//TODO 关闭所有活动打开新活动
                startActivity(intent);
            }
        });
    }
}
