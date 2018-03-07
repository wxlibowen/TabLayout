package com.example.tablayout.util;

import android.content.Context;

import android.widget.ImageView;

import com.example.tablayout.R;
import com.squareup.picasso.Picasso;



/**
 * Created by Administrator on 2018/1/19.
 */

public class PicassoUtil {
    //默认加载图片
    public static void picasso(Context mContext,String url,ImageView imageView){
        Picasso.with(mContext).load(url).into(imageView);

    }

   //设置宽高
    public static void picassoSize(Context mContext, String mUrl, ImageView mImageView,int width,int height){
        Picasso.with(mContext)
                .load(mUrl)
                .resize(width, height)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(mImageView);
    }




}
