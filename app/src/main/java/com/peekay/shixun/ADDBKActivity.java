package com.peekay.shixun;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.peekay.shixun.Fragment.AddBookKeep;
import com.peekay.shixun.Fragment.LessBookKeep;
import com.peekay.shixun.adapter.BookKeepVPAdapter;
import com.peekay.shixun.tools.BookKeepDB;

import java.util.ArrayList;
import java.util.List;

public class ADDBKActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private TextView textView_title;
    List<String> names = new ArrayList<>();
    List<Fragment> fragments = new ArrayList<>();
    BookKeepVPAdapter bookKeepVPAdapter;
    AddBookKeep addBookKeep = new AddBookKeep();
    LessBookKeep lessBookKeep = new LessBookKeep();
    BookKeepDB bookKeepDB;
    private EditText mEditRemark;//备注
    private TextView mTvAddMoney;//金额
    private Button mBtnNum1, mBtnNum2, mBtnNum3, mBtnDelete, mBtnNum4, mBtnNum5, mBtnNum6, mBtnCleattoo, mBtnNum7, mBtnNum8, mBtnNum9;
    private Button mBtnAddAgain, mBtnAddnum, mBtnNum0, mBtnPoint, mBtnSave;
    String item;
    int type = 0;//默认支出

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM, WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);//禁止弹出输入法
        setContentView(R.layout.activity_addbk);
        bookKeepDB = new BookKeepDB(this);
        initView();
    }

    private void initView() {
        viewPager = findViewById(R.id.viewpager_book);
        textView_title = findViewById(R.id.tv_name_book);
        names.add("支出");
        fragments.add(lessBookKeep);
        names.add("收入");
        fragments.add(addBookKeep);
        bookKeepVPAdapter = new BookKeepVPAdapter(getSupportFragmentManager(), fragments, names);
        viewPager.setAdapter(bookKeepVPAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                textView_title.setText(bookKeepVPAdapter.getPageTitle(position));
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mEditRemark = findViewById(R.id.edit_remark);
        mTvAddMoney = findViewById(R.id.tv_add_money);
        mBtnNum1 = findViewById(R.id.btn_num1);
        mBtnNum1.setOnClickListener(this);
        mBtnNum2 = findViewById(R.id.btn_num2);
        mBtnNum2.setOnClickListener(this);
        mBtnNum3 = findViewById(R.id.btn_num3);
        mBtnNum3.setOnClickListener(this);
        mBtnDelete = findViewById(R.id.btn_delete);
        mBtnDelete.setOnClickListener(this);
        mBtnNum4 = findViewById(R.id.btn_num4);
        mBtnNum4.setOnClickListener(this);
        mBtnNum5 = findViewById(R.id.btn_num5);
        mBtnNum5.setOnClickListener(this);
        mBtnNum6 = findViewById(R.id.btn_num6);
        mBtnNum6.setOnClickListener(this);
        mBtnCleattoo = findViewById(R.id.btn_cleattoo);
        mBtnCleattoo.setOnClickListener(this);
        mBtnNum7 = findViewById(R.id.btn_num7);
        mBtnNum7.setOnClickListener(this);
        mBtnNum8 = findViewById(R.id.btn_num8);
        mBtnNum8.setOnClickListener(this);
        mBtnNum9 = findViewById(R.id.btn_num9);
        mBtnNum9.setOnClickListener(this);
        mBtnAddAgain = findViewById(R.id.btn_add_again);
        mBtnAddAgain.setOnClickListener(this);
        mBtnAddnum = findViewById(R.id.btn_addnum);
        mBtnAddnum.setOnClickListener(this);
        mBtnNum0 = findViewById(R.id.btn_num0);
        mBtnNum0.setOnClickListener(this);
        mBtnPoint = findViewById(R.id.btn_point);
        mBtnPoint.setOnClickListener(this);
        mBtnSave = findViewById(R.id.btn_save);
        mBtnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                //保存按钮
                if (mTvAddMoney.getText().toString().isEmpty() || mTvAddMoney.getText().toString().equals("0")) {
                    Toast.makeText(this, "请先输入金额！", Toast.LENGTH_SHORT).show();
                } else {
                    if (saveBK(mTvAddMoney.getText().toString())) {
                        Toast.makeText(this, "保存成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "保存失败！", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            case R.id.btn_num0:
                if (!isTwo(mTvAddMoney.getText().toString())) {
                    if (!mTvAddMoney.getText().equals("0")) {
                        mTvAddMoney.setText(mTvAddMoney.getText() + "0");
                    }
                }
                break;
            case R.id.btn_num1:
                if (!isTwo(mTvAddMoney.getText().toString())) {
                    if (mTvAddMoney.getText().toString().equals("0")) {
                        mTvAddMoney.setText("1");
                    } else {
                        mTvAddMoney.setText(mTvAddMoney.getText() + "1");
                    }
                }
                break;
            case R.id.btn_num2:
                if (!isTwo(mTvAddMoney.getText().toString())) {
                    if (mTvAddMoney.getText().toString().equals("0")) {
                        mTvAddMoney.setText("2");
                    } else {
                        mTvAddMoney.setText(mTvAddMoney.getText() + "2");
                    }
                }
                break;
            case R.id.btn_num3:
                if (!isTwo(mTvAddMoney.getText().toString())) {
                    if (mTvAddMoney.getText().toString().equals("0")) {
                        mTvAddMoney.setText("3");
                    } else {
                        mTvAddMoney.setText(mTvAddMoney.getText() + "3");
                    }
                }
                break;
            case R.id.btn_num4:
                if (!isTwo(mTvAddMoney.getText().toString())) {
                    if (mTvAddMoney.getText().toString().equals("0")) {
                        mTvAddMoney.setText("4");
                    } else {
                        mTvAddMoney.setText(mTvAddMoney.getText() + "4");
                    }
                }
                break;
            case R.id.btn_num5:
                if (!isTwo(mTvAddMoney.getText().toString())) {
                    if (mTvAddMoney.getText().toString().equals("0")) {
                        mTvAddMoney.setText("5");
                    } else {
                        mTvAddMoney.setText(mTvAddMoney.getText() + "5");
                    }
                }
                break;
            case R.id.btn_num6:
                if (!isTwo(mTvAddMoney.getText().toString())) {
                    if (mTvAddMoney.getText().toString().equals("0")) {
                        mTvAddMoney.setText("6");
                    } else {
                        mTvAddMoney.setText(mTvAddMoney.getText() + "6");
                    }
                }
                break;
            case R.id.btn_num7:
                if (!isTwo(mTvAddMoney.getText().toString())) {
                    if (mTvAddMoney.getText().toString().equals("0")) {
                        mTvAddMoney.setText("7");
                    } else {
                        mTvAddMoney.setText(mTvAddMoney.getText() + "7");
                    }
                }
                break;
            case R.id.btn_num8:
                if (!isTwo(mTvAddMoney.getText().toString())) {
                    if (mTvAddMoney.getText().toString().equals("0")) {
                        mTvAddMoney.setText("8");
                    } else {
                        mTvAddMoney.setText(mTvAddMoney.getText() + "8");
                    }
                }
                break;
            case R.id.btn_num9:
                if (!isTwo(mTvAddMoney.getText().toString())) {
                    if (mTvAddMoney.getText().toString().equals("0")) {
                        mTvAddMoney.setText("9");
                    } else {
                        mTvAddMoney.setText(mTvAddMoney.getText() + "9");
                    }
                }
                break;
            case R.id.btn_add_again:
                //再记
                if (mTvAddMoney.getText().toString().isEmpty() || mTvAddMoney.getText().toString().equals("0")) {
                    Toast.makeText(this, "请先输入金额！", Toast.LENGTH_SHORT).show();
                } else {
                    if (saveBK(mTvAddMoney.getText().toString())) {
                        Toast.makeText(this, "保存成功！", Toast.LENGTH_SHORT).show();
                        mTvAddMoney.setText("");
                    } else {
                        Toast.makeText(this, "保存失败！", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            case R.id.btn_cleattoo:
                //清空输入
                mTvAddMoney.setText("");
                mEditRemark.setText("");
                break;
            case R.id.btn_point:
                //小数点，只能点一次
                if (!isTwo(mTvAddMoney.getText().toString())) {
                    if (mTvAddMoney.getText().toString().indexOf(".") == -1) {
                        mTvAddMoney.setText(mTvAddMoney.getText() + ".");
                    }
                }
                break;
            case R.id.btn_delete:
                if (mTvAddMoney.getText().length() > 0) {
                    mTvAddMoney.setText(mTvAddMoney.getText().toString().substring(0, mTvAddMoney.getText().length() - 1));
                } else {
                    Toast.makeText(this, "没有可删除的数字！", Toast.LENGTH_SHORT).show();
                }
                //删除一个字符
                break;
            case R.id.btn_addnum:
                if (mTvAddMoney.getText().toString().indexOf("+") == -1) {
                    if (mTvAddMoney.getText().length() > 0 && mTvAddMoney.getText().toString().indexOf("+") == -1) {
                        mTvAddMoney.setText(mTvAddMoney.getText().toString() + "+");
                    }
                }
                break;
        }
    }

    //判断小数点后是否是两位内
    public Boolean isTwo(String s) {
        if (s.length() > 11) {
            Toast.makeText(this, "输入的数字太大了,我猜你没这么多钱！", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            if (s.indexOf("+") != -1) {
                s = s.substring(s.indexOf("+"));
                if (s.indexOf(".") != -1) {
                    if (s.length() - s.indexOf(".") - 1 >= 2) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                if (s.indexOf(".") != -1) {
                    if (s.length() - s.indexOf(".") - 1 >= 2) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
    }

    //保存方法
    private Boolean saveBK(String s) {
        DatePicker datePicker = new DatePicker(this);
        String time = datePicker.getYear() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth();
        switch (textView_title.getText().toString()) {
            case "支出":
                item = lessBookKeep.getItem();
                type = 0;
                break;
            case "收入":
                item = addBookKeep.getItem();
                type = 1;
                break;
        }
        if (s.indexOf("+") == -1) {
            if (bookKeepDB.add(item, mEditRemark.getText().toString(), Float.valueOf(s), type, time)) {
                return true;
            } else {
                return false;
            }
        } else {
            float a, b;
            a = Float.parseFloat(s.substring(0, s.indexOf("+")));
            b = Float.parseFloat(s.substring(s.indexOf("+")));
            if (bookKeepDB.add(item, mEditRemark.getText().toString(), a + b, type, time)) {
                return true;
            } else {
                return false;
            }
        }
    }
}
