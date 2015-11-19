package com.r_time_run.newmess.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MyPagerAdapter extends PagerAdapter {
	public List<View> views;

	public MyPagerAdapter(List<View> views) {
		this.views = views;

	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return views.size();
	}
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}
	/**
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		View v = views.get(position);
		container.addView(v);
		return v;
	}
	/**
	 */
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView(views.get(position));
	}
}

