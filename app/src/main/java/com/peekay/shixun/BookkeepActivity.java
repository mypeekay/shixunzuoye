package com.peekay.shixun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.peekay.shixun.adapter.BookKeepLVAdapter;
import com.peekay.shixun.bean.BookKeepBean;
import com.peekay.shixun.tools.BookKeepDB;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//记账页面
public class BookkeepActivity extends AppCompatActivity {
    private ListView listView_bookkeep;
    private SmartRefreshLayout smartRefreshLayout;
    private TextView textView_tips, textView_jieyu, textView_shouru, textView_zhichu;
    List<BookKeepBean> bookKeepBeans = new ArrayList<>();
    BookKeepLVAdapter bookKeepLVAdapter;
    private Button button_addbk;
    BookKeepDB bookKeepDB;
    float zhichu = 0;
    float shouru = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_bookkeep);
        bookKeepDB = new BookKeepDB(this);
        initView();
    }

    private void initView() {
        listView_bookkeep = findViewById(R.id.lv_bookkeep);
        smartRefreshLayout = findViewById(R.id.smart);
        textView_tips = findViewById(R.id.tv_tips_bk);
        textView_jieyu = findViewById(R.id.tv_bk_jieyu);
        textView_shouru = findViewById(R.id.tv_shouru);
        textView_zhichu = findViewById(R.id.tv_zhichu);
        load();
        bookKeepLVAdapter = new BookKeepLVAdapter(bookKeepBeans, this, R.layout.bookeep_lv_lv);
        listView_bookkeep.setAdapter(bookKeepLVAdapter);
        button_addbk = findViewById(R.id.btn_addbk);
        button_addbk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookkeepActivity.this, ADDBKActivity.class));
            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                load();
                bookKeepLVAdapter.notifyDataSetChanged();
                smartRefreshLayout.finishRefresh();
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                load();
                bookKeepLVAdapter.notifyDataSetChanged();
                smartRefreshLayout.finishLoadMore();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        load();
        bookKeepLVAdapter.notifyDataSetChanged();
    }

    public void load() {
        bookKeepBeans.clear();
        shouru = 0;
        zhichu = 0;
        Cursor cursor = bookKeepDB.getAll();
        while (cursor != null && cursor.moveToNext()) {
            if (cursor.getInt(cursor.getColumnIndex("TYPE")) == 0) {
                zhichu += cursor.getFloat(cursor.getColumnIndex("MONEY"));
            } else {
                shouru += cursor.getFloat(cursor.getColumnIndex("MONEY"));
            }
            bookKeepBeans.add(new BookKeepBean(
                    cursor.getString(cursor.getColumnIndex("TITLE")),
                    cursor.getFloat(cursor.getColumnIndex("MONEY")),
                    cursor.getInt(cursor.getColumnIndex("TYPE")),
                    cursor.getString(cursor.getColumnIndex("REMARK"))
            ));
        }
        if (bookKeepBeans.isEmpty()) {
            textView_tips.setVisibility(View.VISIBLE);
        } else {
            textView_tips.setVisibility(View.GONE);
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        textView_zhichu.setText("总支出￥" + decimalFormat.format(zhichu));
        textView_shouru.setText("总收入￥" + decimalFormat.format(shouru));
        textView_jieyu.setText("￥" + decimalFormat.format(shouru - zhichu));
    }
}
