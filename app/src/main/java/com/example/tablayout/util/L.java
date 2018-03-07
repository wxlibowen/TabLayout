package com.example.tablayout.util;

import android.util.Log;

/**
 * 项目名称：TabLayout
 * 包名：com.example.tablayout.util
 * 文件名：L
 * 创建者：LBW
 * 创建时间：2017/12/2011:132017
 * 描述：TODO
 */

public class L {
    //开关
    public static final boolean DEBUG=true;
    public static final String TAG="SmartButler";


    //五个等级
    public static  void d(String text){
        if (DEBUG){
            Log.d(TAG,text);
        }
    }
    public static  void i(String text){
        if (DEBUG){
            Log.i(TAG,text);
        }
    }
    public static  void w(String text){
        if (DEBUG){
            Log.w(TAG,text);
        }
    }
    public static  void e(String text){
        if (DEBUG){
            Log.e(TAG,text);
        }
    }


}
