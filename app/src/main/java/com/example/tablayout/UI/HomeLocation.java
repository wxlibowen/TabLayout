package com.example.tablayout.UI;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tablayout.R;
import com.example.tablayout.util.L;
import com.example.tablayout.util.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 项目名称：TabLayout
 * 包名：com.example.tablayout.UI
 * 文件名：HomeLocation
 * 创建者：LBW
 * 创建时间：2018/1/1010:022018
 * 描述：归属地查询
 */

public class HomeLocation extends BaseActivity implements View.OnClickListener {
    private EditText et_number;

    private TextView tv_location;
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btn_del, btn_home_location_query;
    //是否查询成功
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_location);
        initView();
    }

    private void initView() {
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btn0 = (Button) findViewById(R.id.btn0);
        btn_del = (Button) findViewById(R.id.btn_del);
        btn_home_location_query = (Button) findViewById(R.id.btn_home_location_query);


        et_number = (EditText) findViewById(R.id.et_number);
        et_number.setEnabled(false);
        tv_location = (TextView) findViewById(R.id.tv_location);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn0.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_home_location_query.setOnClickListener(this);
        btn_del.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                et_number.setText("");

                return true;
            }
        });

    }


    @Override
    public void onClick(View v) {

        String str = et_number.getText().toString().trim();
        switch (v.getId()) {
            case R.id.btn_home_location_query:
                //得到输入框内容
                String number = et_number.getText().toString().trim();
                //判断是否为空
                if (!TextUtils.isEmpty(number)) {
                    String uri = "http://apis.juhe.cn/mobile/get?phone=" + number + "&key=" + StaticClass.HOME_LOCATION_APP_ID;
                    RxVolley.get(uri, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            L.e(t);
                            //解析Json
                            parsingJson(t);


                        }


                    });

                } else {

                    Toast.makeText(this, "输入框不能为空!", Toast.LENGTH_SHORT).show();
                }
                break;
            //键盘逻辑
            case R.id.btn1:
            case R.id.btn2:
            case R.id.btn3:
            case R.id.btn4:
            case R.id.btn5:
            case R.id.btn6:
            case R.id.btn7:
            case R.id.btn8:
            case R.id.btn9:
            case R.id.btn0:
                if (flag) {
                    flag = false;
                    tv_location.setText("此处显示归属地");
                    str = "";

                }

                et_number.setText(str + ((Button) v).getText());
                et_number.setSelection(str.length() + 1);


                break;
            case R.id.btn_del:
                if (!TextUtils.isEmpty(str) && str.length() > 0) {
                    et_number.setText(str.substring(0, str.length() - 1));
                    et_number.setSelection(str.length() - 1);
                }

                break;


        }
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);

            JSONObject jsonResult = jsonObject.getJSONObject("result");
            String province = jsonResult.getString("province");
            String city = jsonResult.getString("city");
            String areacode = jsonResult.getString("areacode");
            String zip = jsonResult.getString("zip");
            String company = jsonResult.getString("company");
            tv_location.setText("该电话来自:" + province + "省" + city + "市。" + "区号为：" + areacode + "，"
                    + "邮政编码:" + zip + "，" + "运营商为:" + "中国" + company + "。");
            flag = true;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
