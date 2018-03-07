package com.example.tablayout.Service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.tablayout.util.L;
import com.example.tablayout.util.StaticClass;


/**
 * Created by Administrator on 2018/1/25.
 */

public class MessageService extends Service {
    private MyMessageReceiver receiver;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }
    //初始化
    private void init() {
        L.i("init service");
        //动态注册
        receiver=new MyMessageReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(StaticClass.MESSAGE_ACTION);
        //设置权限
        intentFilter.setPriority(Integer.MAX_VALUE);
        //注册
        registerReceiver(receiver,intentFilter);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.i("stop service");
        unregisterReceiver(receiver);
    }
    //短信广播
    public class MyMessageReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            String action=intent.getAction();
            L.d(action);
            if (StaticClass.MESSAGE_ACTION.equals(action)){
                L.i("短信来了");
                Toast.makeText(context, "短信来了！！！", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
