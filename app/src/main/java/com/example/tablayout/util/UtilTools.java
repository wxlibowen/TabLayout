package com.example.tablayout.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.jar.Manifest;

/**
 * 项目名称：TabLayout
 * 包名：com.example.tablayout.util
 * 文件名：UtilTools
 * 创建者：LBW
 * 创建时间：2017/12/2217:432017
 * 描述：工具封装
 */

public class UtilTools {
    //设置字体
    public static void setFonts(Context mContext, TextView mTextView,String FontName){
        Typeface typeface=Typeface.createFromAsset(mContext.getAssets(),"fonts/"+FontName+".ttf");
        mTextView.setTypeface(typeface);
    }
    //封装图片存储
    public static void putImageToShare(Context mContext, ImageView mImageView){
        BitmapDrawable drawable= (BitmapDrawable) mImageView.getDrawable();
        Bitmap bitmap=drawable.getBitmap();
        //将Bitmap压缩成字节流输出数组
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,80,byteArrayOutputStream);
        //利用Base64将字节数组输出流转换为String
        byte[] byteArray=byteArrayOutputStream.toByteArray();
        String imageString=new String(Base64.encodeToString(byteArray,Base64.DEFAULT));
        //将String保存在shareUtil的String中
        SharedUtil.putString(mContext,"user_image_share",imageString);
    }
    //封装图片取出
    public static void getImageToShare(Context mContext,ImageView mImageView){
        String userImage=SharedUtil.getString(mContext,"user_image_share","");
        if (!userImage.equals("")){
            //利用Base64将String转换
            byte[] byteArray=Base64.decode(userImage,Base64.DEFAULT);
            ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(byteArray);
            //生成bitmap
            Bitmap bitmap=BitmapFactory.decodeStream(byteArrayInputStream);
            mImageView.setImageBitmap(bitmap);


        }
    }
    //封装权限确认
    public static void isPermission(Context context,String permissionName,int code){
        if (ContextCompat.checkSelfPermission(context, permissionName)!= PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions((Activity) context,new String[]{permissionName},code);
        }
    }
    //封装权限返回码



}
