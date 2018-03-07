package com.example.tablayout.UI;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.tablayout.R;

/**
 * Created by LBW on 2018/3/2.
 */

public class WebViewActivity extends BaseActivity {
    private ProgressBar progressBar;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initView();

    }

    private void initView() {
        webView = (WebView) findViewById(R.id.web_view);
        progressBar = (ProgressBar) findViewById(R.id.news_progressBar);

//得到传递进来的URL和标题
        Intent intent = getIntent();
        final String url = intent.getStringExtra("url");
        String title = intent.getStringExtra("title");
        //设置标题
        getSupportActionBar().setTitle(title);
//       进行加载页面的逻辑
        //js
        webView.getSettings().setJavaScriptEnabled(true);
        //缩放
        webView.getSettings().setSupportZoom(true);
        //控制器
        webView.getSettings().setBuiltInZoomControls(true);
        //本地接口回调
        webView.setWebChromeClient(new WebViewClient());
        //加载网页
        webView.loadUrl(url);
        //本地显示
        webView.setWebViewClient(new android.webkit.WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }
        });
    }
    public class WebViewClient extends WebChromeClient{
        //进度变化监听
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress==100){
                progressBar.setVisibility(View.GONE);
            }

            super.onProgressChanged(view, newProgress);

        }
    }
}
