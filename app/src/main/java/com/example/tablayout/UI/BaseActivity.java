package com.example.tablayout.UI;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * 项目名称：TabLayout
 * 包名：com.example.tablayout.UI
 * 文件名：BaseActivity
 * 创建者：LBW
 * 创建时间：2017/12/2910:142017
 * 描述：Activity基类
 * 1、统一的属性
 * 2、统一的接口
 * 3、统一的函数和方法
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //显示返回键盘
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }
    //菜单栏操作
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
            }



        }
        return super.onOptionsItemSelected(item);

    }
}
