package com.peekay.shixun;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.peekay.shixun.tools.APP;
import com.peekay.shixun.tools.LoadingDialog;
import com.peekay.shixun.tools.MD5Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

public class FindPWDActivity extends AppCompatActivity implements View.OnClickListener {
    LoadingDialog loadingDialog;
    private TextView mTvGetyzmFind;
    private EditText mEditUserFind, mEditPassFind, mEditSetpassFind, mEditSetpassagainFind;
    private ImageView mImageSetpassFind;
    private Button mBtnSure;
    AlertDialog dialog;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_find_pwd);
        loadingDialog = new LoadingDialog(this);
        initView();
    }

    private void initView() {
        mEditUserFind = findViewById(R.id.edit_user_find);
        mTvGetyzmFind = findViewById(R.id.tv_getyzm_find);
        mTvGetyzmFind.setOnClickListener(this);
        mEditPassFind = findViewById(R.id.edit_pass_find);
        mEditSetpassFind = findViewById(R.id.edit_setpass_find);
        mImageSetpassFind = findViewById(R.id.image_setpass_find);
        mImageSetpassFind.setOnClickListener(this);
        mEditSetpassagainFind = findViewById(R.id.edit_setpassagain_find);
        mBtnSure = findViewById(R.id.btn_sure);
        mBtnSure.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_getyzm_find:
                dialog = loadingDialog.ShowDialog();
                dialog.show();
                SendCode(mEditUserFind.getText().toString());
                break;
            case R.id.image_setpass_find:
                if (i == 0) {
                    i = 1;
                    mImageSetpassFind.setImageResource(R.drawable.pass_visible);
                    mEditSetpassFind.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mEditSetpassagainFind.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    i = 0;
                    mImageSetpassFind.setImageResource(R.drawable.pass);
                    mEditSetpassFind.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mEditSetpassagainFind.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.btn_sure:
                if (mEditUserFind.getText().toString().isEmpty() || mEditPassFind.getText().toString().isEmpty()) {
                    Toast.makeText(this, "请先输入账号或验证码！", Toast.LENGTH_SHORT).show();
                } else {
                    if (mEditSetpassFind.getText().toString().equals(mEditSetpassagainFind) || mEditSetpassFind.getText().toString().length() < 6) {
                        Toast.makeText(this, "两次输入的密码不一致或长度不足6位！", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog = loadingDialog.ShowDialog();
                        dialog.show();
                        FindPwd(mEditUserFind.getText().toString(), mEditPassFind.getText().toString(), mEditSetpassFind.getText().toString());
                    }
                }
                break;
        }
    }

    //重置密码
    private void FindPwd(String email, String code, String pwd) {
        OkHttpUtils.get().url(APP.BASE_URI + "user/resetpwd").addParams("email", email)
                .addParams("code", code).addParams("pwd", MD5Utils.getMD5(pwd))
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.d("sss123", "onError: " + e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                Log.d("sss123", "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int yzm = jsonObject.optInt("code");
                    if (yzm == 200) {
                        Toast.makeText(getApplicationContext(), "重置密码成功！", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "重置密码失败！", Toast.LENGTH_LONG).show();
                    }
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //获取验证码
    private void SendCode(String user) {
        OkHttpUtils.get().url(APP.BASE_URI + "user/sendcode").addParams("email", user)
                .addParams("type", "200")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.d("sss123", "onError: " + e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                Log.d("sss123", "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int yzm = jsonObject.optInt("code");
                    if (yzm == 200) {
                        Toast.makeText(getApplicationContext(), "发送验证码成功！", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "发送验证码失败！", Toast.LENGTH_LONG).show();
                    }
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
