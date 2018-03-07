package com.example.tablayout.util;

import android.content.Context;
import android.graphics.Typeface;

/**
 * 项目名称：TabLayout
 * 包名：com.example.tablayout.util
 * 文件名：SetFonts
 * 创建者：LBW
 * 创建时间：2017/12/2217:282017
 * 描述：封装设置字体
 */

public class SetFonts {
    public static Typeface setFonts(Context mContext,String fontsName){
        Typeface typeface=Typeface.createFromAsset(mContext.getAssets(),"fonts/"+fontsName+".ttf");
        return typeface;
    }
}
