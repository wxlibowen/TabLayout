package com.example.tablayout.adapter;

import android.content.Context;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tablayout.R;
import com.example.tablayout.entitly.WeChatData;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by LBW on 2018/3/2.
 * 微信精选适配器
 */

public class WeChatBaseAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<WeChatData> mList;
    private  WeChatData data;

    public WeChatBaseAdapter(Context mContext, List<WeChatData> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
//        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=inflater.inflate(R.layout.wechat_item,null);
            viewHolder.iv_weChat= (ImageView) convertView.findViewById(R.id.im_wechat);
            viewHolder.wechat_from= (TextView) convertView.findViewById(R.id.wechat_from);
            viewHolder.wechat_title= (TextView) convertView.findViewById(R.id.wechat_title);
            convertView.setTag(viewHolder);
//        }else {
//            convertView.getTag();
//        }
        data=mList.get(position);
        viewHolder.wechat_title.setText(data.getWeChatTitle());
        viewHolder.wechat_from.setText(data.getWeChatFrom());
        String url=data.getImageId();
        Picasso.with(mContext).load(url).resize(100,50).into(viewHolder.iv_weChat);
        return convertView;
    }
    class ViewHolder{
        ImageView iv_weChat;
        TextView wechat_from;
        TextView wechat_title;
    }
}
