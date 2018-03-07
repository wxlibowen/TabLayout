package com.example.tablayout.application;

import android.app.Application;

import com.example.tablayout.util.StaticClass;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;

/**
 * 项目名称：TabLayout
 * 包名：com.example.tablayout.application
 * 文件名：BaseApplication
 * 创建者：LBW
 * 创建时间：2017/12/259:372017
 * 描述：TODO
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Bugly
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, true);
        //初始化Bmob
        Bmob.initialize(this,StaticClass.BMOB_APP_ID);
        //初始化科大讯飞
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID +"="+StaticClass.KDXF_APP_ID);
    }
}
