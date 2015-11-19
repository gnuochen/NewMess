package com.r_time_run.newmess.utils;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageSwitcher;

import com.r_time_run.newmess.view.Rotate3D;


public class AutoChangePicture extends AsyncTask {

	private ImageSwitcher imswitcher;
	private View[] views;
	private int[] imageIds;
	private int i;
	public AutoChangePicture(ImageSwitcher imswitcher, int[] ImageIds, View[] views, int i) {
		this.imswitcher=imswitcher;
		this.views=views;
		this.imageIds=ImageIds;
		this.i=i;
	}
		@Override
		protected Object doInBackground(Object... arg0) {
			// TODO Auto-generated method stub

			for (int i = 0; i < Integer.MAX_VALUE; i++) {
				try {
					Thread.sleep(6000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//publishProgress(null);
			}

			return null;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void onProgressUpdate(Object... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);

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
		
		public void setpic(int m) {

			for (int i = 0; i < views.length; i++) {
				if (i == m) {
//					Log.d("ll", "setpic(int m) "+views[i]);
					views[i].setBackgroundColor(0xffb50202);
				} else {
//					Log.d("ll", "setpic(int m) "+views);
					views[i].setBackgroundColor(0xffebeaea);
				}
			}
		}
}
