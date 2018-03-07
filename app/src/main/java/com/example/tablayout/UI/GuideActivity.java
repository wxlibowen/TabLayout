package com.example.tablayout.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.media.RatingCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.tablayout.MainActivity;
import com.example.tablayout.R;
import com.example.tablayout.util.L;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：TabLayout
 * 包名：com.example.tablayout.UI
 * 文件名：GuideActivity
 * 创建者：LBW
 * 创建时间：2017/12/2121:352017
 * 描述：引导页
 */

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager mViewPager;
    //容器
    private List<View> mList = new ArrayList<>();
    private View view1, view2, view3;
    //小圆点
    private ImageView point1, point2, point3;
    //跳过按钮
    private Button mSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        view1 = View.inflate(this, R.layout.pager_item_one, null);
        view2 = View.inflate(this, R.layout.pager_item_two, null);
        view3 = View.inflate(this, R.layout.pager_item_three, null);
        view3.findViewById(R.id.btn_start).setOnClickListener(this);

        //跳过按钮
        mSkip= (Button) findViewById(R.id.skip);
        mSkip.setOnClickListener(this);

        mList.add(view1);
        mList.add(view2);
        mList.add(view3);
        mViewPager.setAdapter(new GuideAdapter());

        point1 = (ImageView) findViewById(R.id.point1);
        point2 = (ImageView) findViewById(R.id.point2);
        point3 = (ImageView) findViewById(R.id.point3);
        //设置默认图片
        setPointImage(true, false, false);
        //监听ViewPager滑动
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //page切换后
            @Override
            public void onPageSelected(int position) {
                L.i("position:" + position);
                switch (position) {
                    case 0: {
                        setPointImage(true, false, false);
                        mSkip.setVisibility(View.VISIBLE);
                        break;
                    }
                    case 1: {
                        setPointImage(false, true, false);
                        mSkip.setVisibility(View.VISIBLE);
                        break;
                    }
                    case 2: {
                        setPointImage(false, false, true);
                        mSkip.setVisibility(View.GONE);
                        break;
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    //监听点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start: {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            }
            case R.id.skip:{
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            }
        }
    }

    //适配器
    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(mList.get(position));
            return mList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(mList.get(position));
        }
    }

    private void setPointImage(boolean isCheck1, boolean isCheck2, boolean isCheck3) {
        if (isCheck1) {
            point1.setBackgroundResource(R.color.colorAccent);
        } else {
            point1.setBackgroundResource(R.color.colorPrimary);
        }
        if (isCheck2) {
            point2.setBackgroundResource(R.color.colorAccent);
        } else {
            point2.setBackgroundResource(R.color.colorPrimary);
        }
        if (isCheck3) {
            point3.setBackgroundResource(R.color.colorAccent);
        } else {
            point3.setBackgroundResource(R.color.colorPrimary);
        }


    }


}
