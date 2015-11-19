package com.r_time_run.newmess.listener;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ImageView;

import com.r_time_run.newmess.R;

public class GuidePageChangeListener implements OnPageChangeListener {

	private ImageView[] imageViews;
	public GuidePageChangeListener(ImageView[] imageViews) {
		this.imageViews=imageViews;
	}
	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	/**
	 */
	@Override
	public void onPageSelected(int arg0) {
		for (int i = 0; i < imageViews.length; i++) {
			imageViews[arg0]
					.setImageResource(R.drawable.control_circled);
			if (arg0 != i) {
				imageViews[i]
						.setImageResource(R.drawable.control_circleh);
			}
		}

	}
}
