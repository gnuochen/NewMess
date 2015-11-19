package com.r_time_run.newmess.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Toast;

/**
 * Created by nuochen on 2015/9/6.
 */
public abstract class MyGestureDetector {
    GestureDetector detector;

    public void setGestureDetector(Context context){
        detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (Math.abs(e1.getY() - e2.getY()) > 200) {
                    return true;
                }
                if (Math.abs(velocityX) < 100) {
                    return true;
                }
                if (e2.getX() - e1.getX() > 200) {
//                  显示上一个页面
                    sliRight();
                }
                if (e1.getX() - e2.getX() > 200) {
                    // 显示下一个页面

                    sliLeft();
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }
    public GestureDetector getDetector(){
        return detector;
    }
    public abstract void sliLeft();
    public abstract void sliRight();
}
