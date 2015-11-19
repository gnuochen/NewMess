package com.r_time_run.newmess.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.r_time_run.newmess.adapter.GridViewAdapter;

import java.util.ArrayList;

import com.r_time_run.newmess.R;
import com.r_time_run.newmess.subactivity.BabyActivity;
import com.r_time_run.newmess.subactivity.Main_Activity;

import static android.view.View.GONE;


/**
 * Created by nuochen on 2015/9/11.
 */
public class ViewPagerHomeleftUtil {

    private LayoutInflater li;
    private GridViewAdapter gridViewAdapter;
    private Context context;
    private ArrayList<View> views;
    private Handler mhandler;
    private int tag;
    private ViewPager mViewPager;
    private PullToRefreshGridView mPullRefreshListView;

    public ViewPagerHomeleftUtil(Context context, ArrayList<View> views, GridViewAdapter gridViewAdapter, Handler mhandler,ViewPager mViewPager,int tag) {
        this.context = context;
        this.views = views;
        this.gridViewAdapter = gridViewAdapter;
        this.mhandler = mhandler;
        this.tag = tag;
        this.mViewPager = mViewPager;
        li = LayoutInflater.from(context);

    }

    public PullToRefreshGridView viewPagerHomeleftctrol() {
        View viewone = views.get(0);
        mPullRefreshListView = (PullToRefreshGridView) viewone.findViewById(R.id.pull_refresh_grid);
        initIndicator();
        final View gridview_header = viewone.findViewById(R.id.gridview_header);
        mPullRefreshListView.setAdapter(gridViewAdapter);
//        设置返回button
        TextView foods_header_return = (TextView) viewone.findViewById(R.id.foods_header_return);
        foods_header_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);
            }
        });
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
