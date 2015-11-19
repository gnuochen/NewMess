package com.r_time_run.newmess.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 店铺详情界面的viewpager界面适配内容
 *
 * Created by nuochen on 2015/10/4.
 */
public class ViewshopPagerAdapter extends PagerAdapter {
    private List<View> viewList;
    private List<String>titleList;

    public ViewshopPagerAdapter(List<View> viewList, List<String> titleList)
    {
        this.viewList=viewList;
        this.titleList=titleList;
    }
    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        container.removeView(viewList.get(position));
    }

    public CharSequence getPageTitle(int position) {
        // TODO Auto-generated method stub
        return titleList.get(position);
    }
}
