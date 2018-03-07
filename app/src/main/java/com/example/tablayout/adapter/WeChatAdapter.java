package com.example.tablayout.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tablayout.R;
import com.example.tablayout.entitly.WeChatData;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Administrator on 2018/2/28.
 * RecyclerView使用的adapter 目前并未使用
 */

public class WeChatAdapter extends RecyclerView.Adapter<WeChatAdapter.MyViewHolder> {
    private List<WeChatData> weChatDataList;
    private Context mContext;
    public WeChatAdapter(List<WeChatData> weChatDataList,Context mContext) {
        this.weChatDataList = weChatDataList;
        this.mContext=mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext.getApplicationContext()).inflate(R.layout.wechat_item,null);
        MyViewHolder holder=new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        WeChatData weChatData=weChatDataList.get(position);
        Picasso.with(mContext.getApplicationContext()).load(weChatData.getImageId()).into(holder.im_wechat);
        holder.wechat_from.setText(weChatData.getWeChatFrom());
        holder.wechat_title.setText(weChatData.getWeChatTitle());
    }



    @Override
    public int getItemCount() {
        return weChatDataList.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView im_wechat;
        TextView wechat_from;
        TextView wechat_title;
        public MyViewHolder(View v) {
            super(v);

            im_wechat= (ImageView) v.findViewById(R.id.im_wechat);
            wechat_from= (TextView) v.findViewById(R.id.wechat_from);
            wechat_title= (TextView) v.findViewById(R.id.wechat_title);


        }
    }
}
