package com.peekay.shixun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.peekay.shixun.tools.APP;
import com.peekay.shixun.tools.LoadingDialog;
import com.peekay.shixun.tools.MD5Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageView_close, imageView_pass;
    private TextView textView_phonelogin, textView_otherlogin, tv_otherlogin,
            textView_emaillogin, textView_forgetpass, textView_getyzm;
    private LinearLayout linearLayout_xieyi, linearLayout_email;
    private EditText editText_user, editText_pass;
    private Button button_login;
    int p = 0;//判断密码框是否显示
    AlertDialog dialog;
    LoadingDialog loadingDialog;
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
        setContentView(R.layout.activity_login);
        loadingDialog = new LoadingDialog(this);
        initView();
        ifExitst();
    }

    //是否存在token
    private void ifExitst() {
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        if (!sharedPreferences.getString("token", "").isEmpty()) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }
    }

    //绑定
    private void initView() {
        button_login = findViewById(R.id.btn_login);
        button_login.setOnClickListener(this);
        textView_getyzm = findViewById(R.id.tv_getyzm);
        textView_getyzm.setOnClickListener(this);
        editText_pass = findViewById(R.id.edit_pass);
        editText_user = findViewById(R.id.edit_user);
        editText_user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isLogin();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        editText_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isLogin();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        imageView_close = findViewById(R.id.image_close);
        imageView_close.setOnClickListener(this);
        imageView_pass = findViewById(R.id.image_pass);
        imageView_pass.setOnClickListener(this);
        imageView_pass.setVisibility(View.GONE);
        textView_phonelogin = findViewById(R.id.tv_phone_login);
        textView_otherlogin = findViewById(R.id.tv_other_login);
        textView_otherlogin.setVisibility(View.GONE);
        tv_otherlogin = findViewById(R.id.tv_otherlogin);
        tv_otherlogin.setOnClickListener(this);
        linearLayout_xieyi = findViewById(R.id.line_xieyi);
        linearLayout_email = findViewById(R.id.line_email);
        linearLayout_email.setVisibility(View.GONE);
        textView_emaillogin = findViewById(R.id.tv_email_login);
        textView_emaillogin.setOnClickListener(this);
        textView_forgetpass = findViewById(R.id.tv_forget_pass);
        textView_forgetpass.setOnClickListener(this);
    }

    //判断是否可登录
    private void isLogin() {
        switch (textView_phonelogin.getVisibility()) {
            case View.VISIBLE:
                if (editText_user.getText().length() >= 2 && editText_pass.getText().length() == 6) {
                    button_login.setEnabled(true);
                } else {
                    button_login.setEnabled(false);
                }
                break;
            case View.GONE:
                if (editText_user.getText().length() >= 2 && editText_pass.getText().length() >= 6) {
                    button_login.setEnabled(true);
                } else {
                    button_login.setEnabled(false);
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_otherlogin:
                textView_getyzm.setVisibility(View.GONE);
                editText_pass.setHint(getResources().getString(R.string.inputpass));
                editText_pass.setText("");
                editText_pass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editText_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                imageView_pass.setVisibility(View.VISIBLE);
                tv_otherlogin.setVisibility(View.GONE);
                textView_otherlogin.setVisibility(View.VISIBLE);
                linearLayout_email.setVisibility(View.VISIBLE);
                linearLayout_xieyi.setVisibility(View.INVISIBLE);
                textView_phonelogin.setVisibility(View.GONE);
                break;
            case R.id.tv_email_login:
                textView_getyzm.setVisibility(View.VISIBLE);
                editText_pass.setHint(getResources().getString(R.string.passhint));
                editText_pass.setText("");
                editText_pass.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                imageView_pass.setVisibility(View.GONE);
                tv_otherlogin.setVisibility(View.VISIBLE);
                textView_otherlogin.setVisibility(View.GONE);
                linearLayout_email.setVisibility(View.GONE);
                linearLayout_xieyi.setVisibility(View.VISIBLE);
                textView_phonelogin.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_forget_pass:
                startActivity(new Intent(getApplicationContext(), ForgetPassActivity.class));
                break;
            case R.id.image_pass:
                if (p == 0) {
                    p = 1;
                    imageView_pass.setImageResource(R.drawable.pass_visible);
                    editText_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    p = 0;
                    imageView_pass.setImageResource(R.drawable.pass);
                    editText_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.image_close:
                finish();
                break;
            case R.id.tv_getyzm:
                handler.sendEmptyMessage(1);
                SendCode(editText_user.getText().toString());
                break;
            case R.id.btn_login:
                handler.sendEmptyMessage(1);
                if (linearLayout_xieyi.getVisibility() == View.INVISIBLE) {
                    PassLogin(editText_user.getText().toString(), editText_pass.getText().toString());
                } else {
                    Login(editText_user.getText().toString(), editText_pass.getText().toString());
                }
                break;
        }
    }

    //获取验证码
    private void SendCode(String user) {
        OkHttpUtils.get().url(APP.BASE_URI + "user/sendcode").addParams("email", user)
                .addParams("type", "100")
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

    //验证码登录
    private void Login(String user, String code) {
        OkHttpUtils.get().url(APP.BASE_URI + "user/login").addParams("email", user)
                .addParams("code", code)
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
                    if (jsonObject.optInt("code") == 200) {
                        SaveInfo(response);
                        Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "登录失败！", Toast.LENGTH_LONG).show();
                    }
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //密码登录
    private void PassLogin(String user, String pass) {
        OkHttpUtils.get().url(APP.BASE_URI + "user/loginpwd")
                .addParams("email", user).addParams("pwd", MD5Utils.getMD5(pass))
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.d("sss123", "onError: " + e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("code") == 200) {
                        SaveInfo(response);
                        Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "登录失败！", Toast.LENGTH_LONG).show();
                    }
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //保存信息
    private void SaveInfo(String s) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(s).getJSONObject("data");
            SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String email = jsonObject.optString("email");
            Log.d("sss123", "SaveInfo: " + email);
            editor.putString("email", jsonObject.optString("email"));
            editor.putString("nickname", jsonObject.optString("nickname"));
            editor.putString("avatar", jsonObject.optString("avatar"));
            editor.putString("sex", jsonObject.optString("sex"));
            editor.putString("birthday", jsonObject.optString("birthday"));
            editor.putString("phone", jsonObject.optString("phone"));
            editor.putString("token", jsonObject.optString("token"));
            editor.putBoolean("ispwd", jsonObject.optBoolean("ispwd"));
            editor.commit();
            Log.d("sss123", "SaveInfo: " + jsonObject.optBoolean("ispwd"));
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
    }

}
