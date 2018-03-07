package com.example.tablayout.UI;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.tablayout.R;
import com.example.tablayout.entitly.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 项目名称：TabLayout
 * 包名：com.example.tablayout.UI
 * 文件名：RegisterActivity
 * 创建者：LBW
 * 创建时间：2017/12/2811:182017
 * 描述：注册界面
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_user;
    private EditText et_age;
    private EditText et_intro;
    private EditText et_password;
    private EditText et_password_again;
    private EditText et_email;
    private RadioGroup mRadioGroup;
    private boolean isBoy = true;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        mRadioGroup= (RadioGroup) findViewById(R.id.mRadioGroup);
        et_user = (EditText) findViewById(R.id.et_user);
        et_age = (EditText) findViewById(R.id.et_age);
        et_intro = (EditText) findViewById(R.id.et_intro);
        et_password = (EditText) findViewById(R.id.et_password);
        et_password_again = (EditText) findViewById(R.id.et_password_again);
        et_email = (EditText) findViewById(R.id.et_email);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register: {
                //获取输入框的值
                String name = et_user.getText().toString().trim();
                String age = et_age.getText().toString().trim();
                String intro = et_intro.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String password_again = et_password_again.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                //判断是否为空
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(age) &&
                        !TextUtils.isEmpty(password) &&
                        !TextUtils.isEmpty(password_again) &&
                        !TextUtils.isEmpty(email)) {

                    //判断两次密码是否相同
                    if (password.equals(password_again)) {
                        //判断男女
                        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group,  int checkedId) {
                                if (checkedId == R.id.rb_boy) {
                                    isBoy = true;
                                } else if (checkedId == R.id.rb_girl) {
                                    isBoy = false;
                                }
                            }
                        });
                        //判断简介是否为空
                        if (TextUtils.isEmpty(intro)) {
                            intro = "这个人很懒什么都没有留下。";
                        }
                        //注册
                        MyUser user = new MyUser();
                        user.setUsername(name);
                        user.setPassword(password);
                        user.setEmail(email);
                        user.setAge(Integer.parseInt(age));
                        user.setSex(isBoy);
                        user.setIntro(intro);

                        user.signUp(new SaveListener<MyUser>() {
                            @Override
                            public void done(MyUser myUser, BmobException e) {
                                if(e==null){
                                    Toast.makeText(RegisterActivity.this, "注册成功!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(RegisterActivity.this, "注册失败!"+e.toString(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });


                    } else {
                        Toast.makeText(this, "两次密码不相同！", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(this, "输入框不能为空!", Toast.LENGTH_SHORT).show();
                }
                break;
            }


        }


    }


}
