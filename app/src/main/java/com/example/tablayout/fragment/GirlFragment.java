package com.example.tablayout.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;

import com.example.tablayout.R;
import com.example.tablayout.adapter.GirlAdapter;
import com.example.tablayout.entitly.GirlsData;
import com.example.tablayout.util.L;
import com.example.tablayout.util.StaticClass;
import com.show.api.ShowApiRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：TabLayout
 * 包名：com.example.tablayout.fragment
 * 文件名：GirlFragment
 * 创建者：LBW
 * 创建时间：2017/12/1811:322017
 * 描述：TODO
 */

public class GirlFragment extends Fragment {
    private View view;
//    private GirlsData data;
    private List<GirlsData> mList=new ArrayList<>();
    private GridView gridView;
    private GirlAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
      view=inflater.inflate(R.layout.gril_fragment,null);
        initView(view);
        MyAsyncTask myAsyncTask=new MyAsyncTask();
        myAsyncTask.execute();


        return view;

    }

    private void initView(View view) {
        gridView= (GridView) view.findViewById(R.id.mGridView);


    }

    private void paresJson(String s) {
        L.e("开始解析");
        try {
            JSONObject jsonObject=new JSONObject(s);
            JSONObject result=jsonObject.getJSONObject("showapi_res_body");
            for (int i=0;i<=49;i++){
                String h= String.valueOf(i);
                JSONObject json=result.getJSONObject(h);
                String title=json.getString("title");
                String imageUrl=json.getString("thumb");
                L.e("TITLE"+title);
                L.e("IMAGEURL"+imageUrl);
                L.e("来自循环解析"+h);
                GirlsData data=new GirlsData();
                data.setImageUrl(imageUrl);
                data.setTitle(title);
                mList.add(data);

            }
            //实例化适配器
            adapter=new GirlAdapter(mList,getActivity());
            //加载适配器
            gridView.setAdapter(adapter);



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    class MyAsyncTask extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... params) {
            //获取服务器信息返回res
//            String type=params[0];
            String type="35";

            String res=new ShowApiRequest( "http://route.showapi.com/819-1", StaticClass.ROBOT_MY_APP_ID, StaticClass.ROBOT_MY_APP_SECRET)
                    .addTextPara("type",type)
                    .addTextPara("num","50")
                    .addTextPara("page"," ")
                    .post();
//            L.d("来自花瓣福利："+res);

            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            paresJson(s);

        }
    }


}
