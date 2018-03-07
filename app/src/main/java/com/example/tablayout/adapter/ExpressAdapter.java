package com.example.tablayout.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tablayout.R;
import com.example.tablayout.entitly.ExpressData;

import java.util.List;


/**
 * 项目名称：TabLayout
 * 包名：com.example.tablayout.adapter
 * 文件名：ExpressAdapter
 * 创建者：LBW
 * 创建时间：2018/1/99:262018
 * 描述：快递ListView适配器
 */

public class ExpressAdapter extends BaseAdapter {
    private Context mContext;
    private List<ExpressData> mList;
    //布局加载器
    private LayoutInflater inflater;
    private ExpressData data;



    public ExpressAdapter(Context mContext,List<ExpressData> mList) {
        this.mContext=mContext;
        this.mList=mList;
        //获取系统服务
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
        MyViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new MyViewHolder();
            convertView=inflater.inflate(R.layout.layout_express_item,null);
            viewHolder.tv_datetime= (TextView) convertView.findViewById(R.id.tv_datetime);
            viewHolder.tv_zone= (TextView) convertView.findViewById(R.id.tv_zone);
            viewHolder.tv_remark= (TextView) convertView.findViewById(R.id.tv_remark);
            //设置缓存
            convertView.setTag(viewHolder);

        }else {
           viewHolder= (MyViewHolder) convertView.getTag();
        }
        //设置数据
        data=mList.get(position);
        viewHolder.tv_datetime.setText(data.getDatetime());
        viewHolder.tv_zone.setText(data.getZone());
        viewHolder.tv_remark.setText(data.getRemark());



        return convertView;
    }
    class MyViewHolder{
        private TextView tv_datetime;
        private TextView tv_zone;
        private TextView tv_remark;

    }
}
