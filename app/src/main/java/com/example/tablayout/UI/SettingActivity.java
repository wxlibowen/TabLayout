package com.example.tablayout.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.example.tablayout.R;
import com.example.tablayout.Service.MessageService;
import com.example.tablayout.util.SharedUtil;

import java.security.PrivateKey;

/**
 * Created by Administrator on 2018/1/25.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private Switch sw_is_voice;
    private Switch sw_message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();

    }

    private void initView() {
        sw_is_voice= (Switch) findViewById(R.id.sw_is_voice);
        sw_is_voice.setOnClickListener(this);
        boolean isVoice=SharedUtil.getBoolean(this,"isVoice",false);
        sw_is_voice.setChecked(isVoice);
        sw_message= (Switch) findViewById(R.id.sw_message);
        sw_message.setOnClickListener(this);
        boolean isMessage=SharedUtil.getBoolean(this,"isMessage",false);
        sw_message.setChecked(isMessage);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sw_is_voice:
                //切换switch按钮状态
                    sw_is_voice.setSelected(!sw_is_voice.isSelected());
                //保存switch按钮状态
                    SharedUtil.putBoolean(this,"isVoice",sw_is_voice.isChecked());
                break;
            case R.id.sw_message:
                sw_message.setSelected(!sw_message.isSelected());
                SharedUtil.putBoolean(this,"isMessage",sw_message.isChecked());
                if (sw_message.isChecked()){
                    startService(new Intent(this,MessageService.class));
                }else {
                    stopService(new Intent(this,MessageService.class));
                }
                break;



        }


    }
}
