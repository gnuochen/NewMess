package com.r_time_run.newmess.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.r_time_run.newmess.R;
import com.r_time_run.newmess.adapter.ShopViewAdapter;
import com.r_time_run.newmess.subactivity.ShopActivity;

import java.util.ArrayList;
/**
 * Created by nuochen on 2015/9/11.
 */
public class ViewPagerHomeShopsUtil {
    private LayoutInflater li;
    private ShopViewAdapter shopViewAdapter;
    private Context context;
    private ArrayList<View> views;
    private Handler mhandler;
    private int tag;
    private ViewPager mViewpager;
    private PullToRefreshListView mPullRefreshListView;
    /**
     * 初始化好店加载工具类
     */
    public ViewPagerHomeShopsUtil(Context context, ArrayList<View> views, ShopViewAdapter shopViewAdapter, Handler mhandler, ViewPager mViewpager, int tag) {
        this.context = context;
        this.views = views;
        this.shopViewAdapter = shopViewAdapter;
        this.mhandler = mhandler;
        this.tag = tag;
        this.mViewpager = mViewpager;
        li = LayoutInflater.from(context);
    }
    /**
     * 今日好店加载方法
     */
    public PullToRefreshListView viewPagerHomeShopsctrol() {
        View viewone = views.get(2); //设置viewpager第三页为今日好店页面
        mPullRefreshListView = (PullToRefreshListView) viewone.findViewById(R.id.pull_refresh_list);
        initIndicator();//上啦刷新下拉加载提示语
        //        添加headerview
        LinearLayout shop_header_return = (LinearLayout) viewone.findViewById(R.id.shop_header_return);
        shop_header_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewpager.setCurrentItem(1);
            }
        });
        mPullRefreshListView.setAdapter(shopViewAdapter);
        return mPullRefreshListView;
    }
    private void initIndicator() {
        ILoadingLayout startLabels = mPullRefreshListView
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("你可劲拉，拉...");
        startLabels.setRefreshingLabel("好赖，正在刷新...");
        startLabels.setReleaseLabel("你敢放我就敢刷新...");

        ILoadingLayout endLabels = mPullRefreshListView.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("你可劲拉，拉...");
        endLabels.setRefreshingLabel("好赖，正在刷新2...");
        endLabels.setReleaseLabel("你敢放我就敢刷新2...");
    }
}
