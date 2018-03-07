package com.example.tablayout.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.tablayout.R;
import com.example.tablayout.entitly.GirlsData;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 2018/3/4.
 */

public class GirlAdapter extends BaseAdapter {
    private List<GirlsData> mList;
    private Context mContext;
    private LayoutInflater inflater;
    private GirlsData data;
    private WindowManager wm;
    private int width;


    public GirlAdapter(List<GirlsData> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
        inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        wm= (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width=wm.getDefaultDisplay().getWidth();

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
        data=mList.get(position);
        convertView=inflater.inflate(R.layout.girl_item,null);
        MyViewHolder holder=new MyViewHolder();
        holder.imageUrl= (ImageView) convertView.findViewById(R.id.iv_girl);
        holder.title= (TextView) convertView.findViewById(R.id.tv_girl);
        //为Text设置标题
        holder.title.setText(data.getTitle());
        String url=data.getImageUrl();
        Picasso.with(mContext).load(url).resize(width/2,500).into(holder.imageUrl);



        return convertView;
    }
    class MyViewHolder{
        ImageView imageUrl;
        TextView title;



    }


}
