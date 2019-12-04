package com.peekay.shixun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.StatusBarManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;

import com.peekay.shixun.Fragment.HomeFragment;
import com.peekay.shixun.Fragment.InfoFragment;
import com.peekay.shixun.adapter.FragmentVPAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager viewPager;
    FragmentPagerAdapter adapter;
    List<Fragment> fragments = new ArrayList<>();
    RadioButton[] rb = new RadioButton[2];
    private RadioButton radioButton_home, radioButton_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_home);
        initView();
        if (getSharedPreferences("user", MODE_PRIVATE).getBoolean("ispwd", true)==false) {
            Intent intent = new Intent(HomeActivity.this, SetPWDActivity.class);
            intent.putExtra("token", getSharedPreferences("user", MODE_PRIVATE).getString("token", ""));
            startActivity(intent);
        }
        rb[0] = radioButton_home;
        rb[1] = radioButton_info;
        for (RadioButton r : rb) {
            Drawable[] drawables = r.getCompoundDrawables();
            Rect rect = new Rect(0, 0, drawables[1].getMinimumWidth() / 3, drawables[1].getMinimumHeight() / 3);
            drawables[1].setBounds(rect);
            r.setCompoundDrawables(null, drawables[1], null, null);
        }
    }

    public void initView() {
        radioButton_home = findViewById(R.id.rb_home);
        radioButton_home.setOnClickListener(this);
        radioButton_info = findViewById(R.id.rb_info);
        radioButton_info.setOnClickListener(this);
        viewPager = findViewById(R.id.viewpager);
        fragments.add(new HomeFragment());
        fragments.add(new InfoFragment());
        adapter = new FragmentVPAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 0:
                        radioButton_home.setChecked(true);
                        radioButton_info.setChecked(false);
                        radioButton_home.setTextColor(Color.rgb(219, 68, 55));
                        radioButton_info.setTextColor(Color.GRAY);
                        break;
                    case 1:
                        radioButton_home.setChecked(false);
                        radioButton_info.setChecked(true);
                        radioButton_home.setTextColor(Color.GRAY);
                        radioButton_info.setTextColor(Color.rgb(219, 68, 55));
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_home:
                radioButton_home.setTextColor(Color.rgb(219, 68, 55));
                radioButton_info.setTextColor(Color.GRAY);
                viewPager.setCurrentItem(0);
                break;
            case R.id.rb_info:
                radioButton_home.setTextColor(Color.GRAY);
                radioButton_info.setTextColor(Color.rgb(219, 68, 55));
                viewPager.setCurrentItem(1);
                break;
        }
    }
}
