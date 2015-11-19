package com.r_time_run.newmess.subactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.r_time_run.newmess.R;
import com.r_time_run.newmess.view.HackyViewPager;


/**
 *显示大图界面
 */
public class ShowBigPictrue extends FragmentActivity {

	private HackyViewPager viewPager;
	private int[] resId={R.drawable.huolongguo, R.drawable.tangshui};
	private int[] resId2 = {R.drawable.cute_boy_08,R.drawable.cute_boy_18,R.drawable.cute_boy_16,R.drawable.cute_boy_18,R.drawable.cute_boy_10};
	private int position=0;
// 用于获取展示界面的选择
	private String requsetActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_big_pictrue_a);
		Intent intent=getIntent();
//		得到上一个界面显示图片的位置
		position=intent.getIntExtra("position", 0);
		requsetActivity = intent.getStringExtra("requsetActivity");
		if("shop".equals(requsetActivity)){
			resId=resId2;
		}
		initViewPager();
	}
	
private void initViewPager(){
		
		viewPager = (HackyViewPager) findViewById(R.id.viewPager_show_bigPic);
		ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(adapter);
		//跳转到第几个页面
		viewPager.setCurrentItem(position);
	}
	
	private class ViewPagerAdapter extends FragmentStatePagerAdapter {

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			int show_resId=resId[position];
			return new PictrueFragment(show_resId);
		}

		@Override
		public int getCount() {
			return resId.length;
		}

		
	}

}
