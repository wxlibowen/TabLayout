package com.example.tablayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.tablayout.UI.SettingActivity;
import com.example.tablayout.fragment.ButlerFragment;
import com.example.tablayout.fragment.GirlFragment;
import com.example.tablayout.fragment.UserFragment;
import com.example.tablayout.fragment.WechatFragment;
import com.example.tablayout.util.L;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private List<String> mTitleList;
    private List<Fragment> mFragmentList;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        initPermission();
        //去掉阴影
        getSupportActionBar().setElevation(0);


    }

    private void initPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{android.Manifest.permission.RECEIVE_SMS},1);
        }else {
//            L.e("权限已经存在了!");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_DENIED){
                    L.d("拥有权限");
                }else {
                    L.d("没有权限");
                }
                break;

        }

    }

    private void initData() {

        mTitleList = new ArrayList<>();
        mTitleList.add("服务管家");
        mTitleList.add("微信精选");
        mTitleList.add("美女社区");
        mTitleList.add("个人中心");

        mFragmentList = new ArrayList<>();
        mFragmentList.add(new ButlerFragment());
        mFragmentList.add(new WechatFragment());
        mFragmentList.add(new GirlFragment());
        mFragmentList.add(new UserFragment());

    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.mFloatingActionButton);
        //默认隐藏FloatingActionButton
        mFloatingActionButton.setVisibility(View.GONE);
        mFloatingActionButton.setOnClickListener(this);
        //预加载
        mViewPager.setOffscreenPageLimit(mFragmentList.size());
        //适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitleList.get(position);
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mFloatingActionButton.setVisibility(View.GONE);
                } else {
                    mFloatingActionButton.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mFloatingActionButton:
                startActivity(new Intent(this, SettingActivity.class));

                break;



        }

    }


}
