package com.example.tablayout.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tablayout.R;
import com.example.tablayout.adapter.ChatListAdapter;
import com.example.tablayout.entitly.CharListData;
import com.example.tablayout.util.L;
import com.example.tablayout.util.SharedUtil;
import com.example.tablayout.util.StaticClass;


import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.show.api.ShowApiRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：TabLayout
 * 包名：com.example.tablayout.fragment
 * 文件名：ButlerFragment
 * 创建者：LBW
 * 创建时间：2017/12/1811:322017
 * 描述：TODO
 */

public class ButlerFragment extends Fragment implements View.OnClickListener {
    private MyAsyncTask myAsyncTask;
    private View view;
    private ListView butler_list;
    private EditText et_butler_text;
    private Button btn_butler_send;
    private List<CharListData> mList=new ArrayList<>();
    private ChatListAdapter adapter;
    //TTS
    private SpeechSynthesizer mTts;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.butler_fragment,null);
        initView(view);





        return view;
    }

    private void initView(View view) {
        //初始化科大讯飞
        //1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener
        mTts= SpeechSynthesizer.createSynthesizer(getActivity(), null);
//2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
//设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
//保存在SD卡需要在AndroidManifest.xml添加写SD卡权限
//如果不需要保存合成音频，注释该行代码
//        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");

        butler_list= (ListView) view.findViewById(R.id.butler_list);
        et_butler_text= (EditText) view.findViewById(R.id.et_butler_text);
        btn_butler_send= (Button) view.findViewById(R.id.btn_butler_send);
        adapter=new ChatListAdapter(getActivity(),mList);
        butler_list.setAdapter(adapter);
        addLeftItem("你好我是小管家");
        btn_butler_send.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_butler_send:{
                //得到输入框内容
                String myTalk=et_butler_text.getText().toString();
                if (!TextUtils.isEmpty(myTalk)){
                    //将文字添加到右侧
                    addRightItem(myTalk);
                    //讲用户说的话传递到AsyncTask中进行POST请求
                    myAsyncTask=new MyAsyncTask();
                    myAsyncTask.execute(myTalk);
                    et_butler_text.setText("");
                }else {
                    Toast.makeText(getActivity(), "请输入内容!", Toast.LENGTH_SHORT).show();
                }




            }




        }
    }
    //添加左边文本
    private void addLeftItem(String text){
        startSpeak(text);
        CharListData data=new CharListData();
        data.setType(ChatListAdapter.VALUE_LEFT_TEXT);
        data.setText(text);
        mList.add(data);
        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        butler_list.setSelection(butler_list.getBottom());

    }
    //添加右边文本
    private void addRightItem(String text){

        CharListData data=new CharListData();
        data.setType(ChatListAdapter.VALUE_RIGHT_TEXT);
        data.setText(text);
        mList.add(data);
        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        butler_list.setSelection(butler_list.getBottom());

    }
    class MyAsyncTask extends AsyncTask<String,Integer,String>{
        //在子线程中进行数据通讯
        @Override
        protected String doInBackground(String... params) {

            String mMyTalk=params[0];
            //将data发送到服务器
            String res=new ShowApiRequest("http://route.showapi.com/60-27", StaticClass.ROBOT_MY_APP_ID,StaticClass.ROBOT_MY_APP_SECRET)
                    .addTextPara("info",mMyTalk)
                    .addTextPara("userid","userid")
                    .post();
            L.e(res);

            return res;
        }
        //更新UI等操作
        @Override
        protected void onPostExecute(String s) {
          //对得到的S进行JSON解析
            try {
                JSONObject jsonObject=new JSONObject(s);
                JSONObject json=jsonObject.getJSONObject("showapi_res_body");
                String text=json.getString("text");
                L.i(text);
                addLeftItem(text);





            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
        private void startSpeak(String text){
            boolean isVoice= SharedUtil.getBoolean(getActivity(),"isVoice",false);
            if (isVoice){
                mTts.startSpeaking(text,mSynListener);
            }

        }


    private SynthesizerListener mSynListener = new SynthesizerListener(){

        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {

        }
        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {

        }
        //开始播放
        public void onSpeakBegin() {

        }
        //暂停播放
        public void onSpeakPaused() {

        }
        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {

        }
        //恢复播放回调接口
        public void onSpeakResumed() {

        }
        //会话事件回调接口
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {

        }
    };

}
