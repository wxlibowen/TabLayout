package com.example.tablayout.UI;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tablayout.R;
import com.example.tablayout.entitly.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 项目名称：TabLayout
 * 包名：com.example.tablayout.UI
 * 文件名：ForgetPasswordActivity
 * 创建者：LBW
 * 创建时间：2017/12/3010:572017
 * 描述：忘记密码页面
 */

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_old_password;
    private EditText et_new_password;
    private EditText et_new_password_again;
    private EditText et_email;
    private Button btn_replace;
    private Button btn_find;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initView();

    }

    private void initView() {
        et_old_password = (EditText) findViewById(R.id.et_old_password);
        et_new_password = (EditText) findViewById(R.id.et_new_password);
        et_new_password_again = (EditText) findViewById(R.id.et_new_password_again);
        et_email = (EditText) findViewById(R.id.et_email_forget);
        btn_replace = (Button) findViewById(R.id.btn_replace_password);
        btn_find = (Button) findViewById(R.id.btn_find);
        btn_replace.setOnClickListener(this);
        btn_find.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //更改密码按钮监听
            case R.id.btn_replace_password: {
                //得到输入框内容
                String old_password=et_old_password.getText().toString().trim();
                String new_password=et_new_password.getText().toString().trim();
                String new_password_again=et_new_password_again.getText().toString().trim();
                //判断是否为空
                if (!TextUtils.isEmpty(old_password)&&!TextUtils.isEmpty(new_password)&&
                        !TextUtils.isEmpty(new_password_again)){
                    //判断两次密码是否相同
                    if (new_password.equals(new_password_again)){
                            MyUser.updateCurrentUserPassword(old_password, new_password, new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e==null){
                                        Toast.makeText(ForgetPasswordActivity.this, "修改密码成功！", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }else{
                                        Toast.makeText(ForgetPasswordActivity.this, "修改密码失败！", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                    }else {
                        Toast.makeText(this, "您输入的两次密码不同！", Toast.LENGTH_SHORT).show();
                    }

                }{
                    Toast.makeText(this, "输入框不能为空！", Toast.LENGTH_SHORT).show();
                }


                break;
            }
            //通过邮箱找回密码监听
            case R.id.btn_find: {
                //得到邮箱
                final String email = et_email.getText().toString().trim();
                //判断邮箱是否为空
                if (!TextUtils.isEmpty(email)) {
                    //发送邮件

                    MyUser.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                        if (e==null){
                            Toast.makeText(ForgetPasswordActivity.this, "重置密码请求成功，请到:"+email+"进行密码重置操作", Toast.LENGTH_SHORT).show();


                        }else {
                            Toast.makeText(ForgetPasswordActivity.this, "重制密码失败"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        }
                    });


                } else {
                    Toast.makeText(this, "输入框不能为空!", Toast.LENGTH_SHORT).show();
                }

                break;
            }


        }
    }
}
