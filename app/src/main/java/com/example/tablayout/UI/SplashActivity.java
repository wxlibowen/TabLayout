package com.example.tablayout.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.tablayout.R;
import com.example.tablayout.util.SharedUtil;
import com.example.tablayout.util.StaticClass;
import com.example.tablayout.util.UtilTools;

/**
 * 项目名称：TabLayout
 * 包名：com.example.tablayout.UI
 * 文件名：SplashActivity
 * 创建者：LBW
 * 创建时间：2017/12/2110:312017
 * 描述：闪屏页
 */

public class SplashActivity extends AppCompatActivity {
    private TextView mTvSplash;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case StaticClass.HANDLER_SPLASH:{
                    //判断是否第一次运行
                    if (isFirst()){
                        startActivity(new Intent(SplashActivity.this,GuideActivity.class));
                    }else {
//                        调试
                        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
//                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    }
                    finish();
                    break;
                }
            }
        }
    };


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();








    }
    //初始化View
    private void initView() {
        //延时
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH,2000);
        mTvSplash= (TextView) findViewById(R.id.tv_splash);
        //设置字体（未封装）
//        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/wjj.ttf");
//        mTvSplash.setTypeface(typeface);
       //设置字体（封装）
        UtilTools.setFonts(this,mTvSplash,"wjj");


    }
    //判断程序是否第一次运行
    private boolean isFirst() {
      boolean isFirst=  SharedUtil.getBoolean(this,StaticClass.SHARE_IS_FIRST,true);
        //是第一次运行
        if (isFirst){
            SharedUtil.putBoolean(this,StaticClass.SHARE_IS_FIRST,false);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
//         禁止返回键
    }
}
