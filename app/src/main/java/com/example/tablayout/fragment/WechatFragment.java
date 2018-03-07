package com.example.tablayout.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tablayout.R;
import com.example.tablayout.UI.WebViewActivity;
import com.example.tablayout.adapter.WeChatBaseAdapter;
import com.example.tablayout.entitly.WeChatData;

import com.example.tablayout.util.L;
import com.show.api.ShowApiRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：TabLayout
 * 包名：com.example.tablayout.fragment
 * 文件名：WechatFragment
 * 创建者：LBW
 * 创建时间：2017/12/1811:322017
 * 描述：机器人聊天界面
 */

public class WechatFragment extends Fragment {
    private View view;
    private List<WeChatData> mList=new ArrayList<>();

    private ListView mListview;
    private List<String> titleList=new ArrayList<>();
    private List<String> urlList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.wechat_fragment,null);

        initView(view);


        return view;
    }

    private void ParseJSON(String string) {
        try {
            JSONObject object=new JSONObject(string);
            JSONObject jsonObject=object.getJSONObject("showapi_res_body");
            JSONArray jsonArray=jsonObject.getJSONArray("newslist");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject json= (JSONObject) jsonArray.get(i);
               WeChatData data=new WeChatData();
                String url=json.getString("url");
                String title=json.getString("title");
                String from=json.getString("description");
                data.setImageId(json.getString("picUrl"));
                data.setUrl(url);
                data.setWeChatFrom( from);
                data.setWeChatTitle(title);
                mList.add(data);
                titleList.add(title);
                urlList.add(url);
                WeChatBaseAdapter adapter=new WeChatBaseAdapter(getActivity(),mList);
                mListview.setAdapter(adapter);
//                L.d(from);
//                L.d(title);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void initView(View view) {
        mListview= (ListView) view.findViewById(R.id.wechat_list);
        MyAsyncTask myAsyncTask=new MyAsyncTask();
        myAsyncTask.execute();
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),WebViewActivity.class);
                intent.putExtra("title",titleList.get(position));
                intent.putExtra("url",titleList.get(position));
                startActivity(intent);
            }
        });
//        String res=new ShowApiRequest("http://route.showapi.com/181-1", "54617","7081918fd36f463cb57288a1ccad243f")
//                .addTextPara("num","10")
//                .addTextPara("rand","1")
//                .addTextPara("word","盗墓笔记")
//                .addTextPara("page","1")
//                .addTextPara("src","人民日报")
//                .post();
//        L.d("res:"+res);





    }
    class MyAsyncTask extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... params) {
            String res=new ShowApiRequest("http://route.showapi.com/181-1", "54617","7081918fd36f463cb57288a1ccad243f")
                .addTextPara("num","50")
                .addTextPara("rand","1")
                .addTextPara("word","美女")
                .addTextPara("page","1")
                .addTextPara("src","人民日报")
                .post();
//        L.d("res:"+res);

            return res;
        }
//更新UI
        @Override
        protected void onPostExecute(String s) {
            ParseJSON(s);

        }


    }
}
