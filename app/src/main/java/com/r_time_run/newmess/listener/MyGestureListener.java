package com.r_time_run.newmess.listener;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageSwitcher;

import com.r_time_run.newmess.view.Rotate3D;

public class MyGestureListener implements GestureDetector.OnGestureListener {
private ImageSwitcher imswitcher;
private int i;
private int[] imageIds; 
private View[] views;
	public MyGestureListener(ImageSwitcher imswitcher, int[] imageIds, View[] views, int i) {
		this.imswitcher=imswitcher;
		this.imageIds=imageIds;
		this.views=views;
		this.i=i;
	}
	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (velocityX > 0) {

			float halfWidth = imswitcher.getWidth() / 2.0f;
			float halfHeight = imswitcher.getHeight() / 2.0f;
			int duration = 500;
			int depthz = 0;// viewFlipper.getWidth()/2;

			Rotate3D rdin = new Rotate3D(-75, 0, 0, halfWidth, halfHeight);
			rdin.setDuration(duration);
			rdin.setFillAfter(true);
			imswitcher.setInAnimation(rdin);
			Rotate3D rdout = new Rotate3D(15, 90, 0, halfWidth, halfHeight);

			rdout.setDuration(duration);
			rdout.setFillAfter(true);
			imswitcher.setOutAnimation(rdout);

			i = (i - 1);

			int p = i % 4;
			if (p >= 0) {
				setpic(p);
				imswitcher.setImageResource(imageIds[p]);
			} else {
				int k = 4 + p;
				setpic(k);
				imswitcher.setImageResource(imageIds[k]);
			}
		}
		if (velocityX < 0) {
			// Toast.LENGTH_SHORT).show();

			float halfWidth = imswitcher.getWidth() / 2.0f;
			float halfHeight = imswitcher.getHeight() / 2.0f;
			int duration = 500;
			int depthz = 0;// viewFlipper.getWidth()/2;

			Rotate3D rdin = new Rotate3D(75, 0, 0, halfWidth, halfHeight);
			rdin.setDuration(duration);
			rdin.setFillAfter(true);
			imswitcher.setInAnimation(rdin);
			Rotate3D rdout = new Rotate3D(-15, -90, 0, halfWidth, halfHeight);

			rdout.setDuration(duration);
			rdout.setFillAfter(true);
			imswitcher.setOutAnimation(rdout);

			i = (i + 1);
			int p = i % 4;
			if (p >= 0) {
				setpic(p);
				imswitcher.setImageResource(imageIds[p]);
			} else {
				int k = 4 + p;
				setpic(k);
				imswitcher.setImageResource(imageIds[k]);

			}
		}
		return true;
	}

	@Override
	public void onLongPress(MotionEvent e) {
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		int p = i % 4;
		if (p >= 0) {
		} else {

			int k = 4 + p;
		}

		return true;
	}
	public void setpic(int m) {

		for (int i = 0; i < views.length; i++) {
			if (i == m) {
				views[i].setBackgroundColor(0xffb50202);
			} else {
				views[i].setBackgroundColor(0xffebeaea);
			}
		}
	}

}
