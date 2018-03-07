package com.example.tablayout.UI;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tablayout.R;
import com.example.tablayout.adapter.ExpressAdapter;
import com.example.tablayout.entitly.ExpressData;
import com.example.tablayout.util.L;
import com.example.tablayout.util.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 项目名称：TabLayout
 * 包名：com.example.tablayout.UI
 * 文件名：Expressage
 * 创建者：LBW
 * 创建时间：2018/1/811:052018
 * 描述：TODO
 */

public class Express extends BaseActivity implements View.OnClickListener {
    private EditText express_company;
    private EditText express_number;
    private ListView express_list;
    private Button btn_query;
    List<ExpressData> mList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express);
        initView();
    }

    private void initView() {
        express_company= (EditText) findViewById(R.id.express_company);
        express_number= (EditText) findViewById(R.id.express_number);
        express_list= (ListView) findViewById(R.id.express_list);
        btn_query= (Button) findViewById(R.id.btn_query);
        btn_query.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_query:
                mList.clear();
                express_list.setVisibility(View.VISIBLE);
                //得到EditText值
               String company= express_company.getText().toString().trim();
                String number=express_number.getText().toString().trim();
                //判断是否为空
                if (!TextUtils.isEmpty(company)&&!TextUtils.isEmpty(number)){

                    RxVolley.get("http://v.juhe.cn/exp/index?key="+ StaticClass.EXPRESS_APP_ID+"&com="+company+"&no="+number, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            L.e("请求得到的数据:"+t);
                            parsingJson(t);
                        }
                    });
                }else {
                    Toast.makeText(this, "输入框不能为空！", Toast.LENGTH_SHORT).show();
                }
                break;



        }
    }
        //解析Json
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject=new JSONObject(t);
            JSONObject jsonResult=jsonObject.getJSONObject("result");
           JSONArray jsonArray=jsonResult.getJSONArray("list");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject json= (JSONObject) jsonArray.get(i);
                ExpressData expressData=new ExpressData();
                expressData.setRemark(json.getString("remark"));
                expressData.setZone(json.getString("zone"));
                expressData.setDatetime(json.getString("datetime"));
                mList.add(expressData);
            }
            //倒序处理list
            Collections.reverse(mList);
            ExpressAdapter adapter=new ExpressAdapter(this,mList);
            express_list.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
