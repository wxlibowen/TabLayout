package com.example.tablayout.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tablayout.MainActivity;
import com.example.tablayout.R;
import com.example.tablayout.View.CustomDialog;
import com.example.tablayout.entitly.MyUser;
import com.example.tablayout.util.SharedUtil;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 项目名称：TabLayout
 * 包名：com.example.tablayout.UI
 * 文件名：LoginActivity
 * 创建者：LBW
 * 创建时间：2017/12/2810:472017
 * 描述：登录页面
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //用户名输入框
    private EditText edit_user_name;
    //密码输入框
    private EditText edit_user_password;
    //登录按钮
    private Button btn_user_login;
    //注册按钮
    private Button btn_user_register;
    //记住密码选择框
    private CheckBox remember_password;
    //忘记密码
    private TextView forget_password;
    //实例化User
    private MyUser myUser=new MyUser();
    //Dialog
    private CustomDialog customDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化控件
        initView();
    }

    private void initView() {
        edit_user_name = (EditText) findViewById(R.id.edit_user_name);
        edit_user_password = (EditText) findViewById(R.id.edit_password_name);
        btn_user_login = (Button) findViewById(R.id.btn_user_login);
        btn_user_register = (Button) findViewById(R.id.btn_user_register);
        remember_password = (CheckBox) findViewById(R.id.remember_password);
        forget_password = (TextView) findViewById(R.id.forget_password);
        btn_user_login.setOnClickListener(this);
        btn_user_register.setOnClickListener(this);
        forget_password.setOnClickListener(this);
        //自定义dialog
        customDialog=new CustomDialog(this,100,100,R.layout.dialog_login,R.style.Theme_dialog, Gravity.CENTER,R.style.pop_anim_style);
//        customDialog=new CustomDialog(this,R.layout.dialog_login,R.style.pop_anim_style);
        //屏幕外点击无效
        customDialog.setCancelable(false);

        boolean isChecked=SharedUtil.getBoolean(this,"remember_password",false);
        remember_password.setChecked(isChecked);
        if (isChecked){
            String spUserName=SharedUtil.getString(this,"user_name","");
            String spUserPassword=SharedUtil.getString(this,"user_password","");

            edit_user_name.setText(spUserName);
            edit_user_password.setText(spUserPassword);
        }




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //登录按钮监听
            case R.id.btn_user_login: {
                //显示登录dialog
                customDialog.show();
                //获取输入框数据
                final String name=edit_user_name.getText().toString().trim();
                final String password=edit_user_password.getText().toString().trim();
                //判断用户名密码是否为空
                if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(password)){
                    //登录

                    myUser.setUsername(name);
                    myUser.setPassword(password);
                    myUser.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            customDialog.dismiss();
                            if (e==null){
                                //是否验证邮箱
                                if (myUser.getEmailVerified()){
                                    Toast.makeText(LoginActivity.this, "登陆成功!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                    //缓存保存状态
                                    if (remember_password.isChecked()){
                                        SharedUtil.putBoolean(LoginActivity.this,"remember_password",remember_password.isChecked());
                                    }

                                    //将用户名缓存到SharedPreference
                                    if (remember_password.isChecked()){
                                        SharedUtil.putString(LoginActivity.this,"user_name",name);
                                        SharedUtil.putString(LoginActivity.this,"user_password",password);
                                    }else {
                                       SharedUtil.deleteShared(LoginActivity.this,"user_name");
                                        SharedUtil.deleteShared(LoginActivity.this,"user_password");
                                    }



                                    finish();
                                }else{
                                    Toast.makeText(LoginActivity.this, "邮箱没有验证！", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(LoginActivity.this, "用户名或密码错误！"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }else {
                    customDialog.dismiss();
                    Toast.makeText(this, "请输入用户名和密码！", Toast.LENGTH_SHORT).show();
                }




                break;
            }
            //注册按钮监听
            case R.id.btn_user_register: {
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            }
            //忘记密码监听
            case R.id.forget_password: {
                startActivity(new Intent(this,ForgetPasswordActivity.class));
                break;
            }


        }


    }
}
