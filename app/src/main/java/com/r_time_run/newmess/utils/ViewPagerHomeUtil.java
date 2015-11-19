package com.r_time_run.newmess.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.r_time_run.newmess.GoodsActivity;
import com.r_time_run.newmess.R;
import com.r_time_run.newmess.constant.ConstantHome;
import com.r_time_run.newmess.listener.MyGestureListener;
import com.r_time_run.newmess.subactivity.SquareActivity;
import com.r_time_run.newmess.view.ElasticScrollView;

import java.util.ArrayList;

/**
 * Created by nuochen on 2015/9/9.
 */
public class ViewPagerHomeUtil {
    private ArrayList<View> views;
    private LayoutInflater li;
    private Context context;
    private Handler mHandler;
    private int tag;
    private ImageSwitcher imswitcher;
    private ViewPager mViewPager;
    private PopupWindow popupWindow;
    public ViewPagerHomeUtil(Context context,ArrayList<View> views, Handler mHandler,ViewPager mViewPager,int tag) {
        this.context=context;
        this.views=views;
//        this.svAdapterUp=svAdapterUp;
//        this.svAdapterDown=svAdapterDown;
        this.mHandler=mHandler;
        this.tag=tag;
        this.mViewPager=mViewPager;
        li=LayoutInflater.from(context);
    }
//    final ArrayList<ArrayList<GoodsBean>> list
/**
 *        初始化homeviewpager
 *
 * */
    @SuppressWarnings("unchecked")
    public ElasticScrollView initeViewTwoContrls() {
        View viewTwo = views.get(1);
        ElasticScrollView elasticScrollView = (ElasticScrollView) viewTwo.findViewById(R.id.scrollView1);
        View scrollView = li.inflate(R.layout.pullrefresh_layoutitem, null);
        /**
         * 设置主页的点击事件
         * */
        ImageView first_goodshop = (ImageView) scrollView.findViewById(R.id.first_goodshop);
        ImageView first_praiseselect = (ImageView) scrollView.findViewById(R.id.first_praiseselect);
        ImageView first_friends = (ImageView) scrollView.findViewById(R.id.first_friends);
        first_goodshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(2);
            }
        });
        first_praiseselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });
        first_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SquareActivity.class);
                context.startActivity(intent);
            }
        });
        // 重写的ScrollView只能通过手动添加显示view布局
        elasticScrollView.addChild(scrollView, 1);
//        设置头部进入食堂
        TextView textView = (TextView) viewTwo.findViewById(R.id.tv_goto_foodsquare);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,GoodsActivity.class);
                context.startActivity(intent);
            }
        });

        /** 头布局的3D效果 */
        int count = 0;
        imswitcher = (ImageSwitcher) viewTwo
                .findViewById(R.id.imageSwitcher1);
        View[] vs = new View[ConstantHome.Rid.length];
        for (int i = 0; i < ConstantHome.Rid.length; i++) {
            vs[i] = viewTwo.findViewById(ConstantHome.Rid[i]);
        }
        imswitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(context);
                imageView.setBackgroundColor(0xff0000);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
                        Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.FILL_PARENT));
                return imageView;
            }
        });
        imswitcher.setImageResource(ConstantHome.imageIds[0]);

        //GestureDetector 操作手势类MyGestureListener
        final GestureDetector mGestureDetector = new GestureDetector(context,
                new MyGestureListener(imswitcher, ConstantHome.imageIds, vs,
                        count));
        imswitcher.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                imswitcher.getParent().requestDisallowInterceptTouchEvent(true);//解决冲突，过滤掉父
                mGestureDetector.onTouchEvent(event);
                return true;
            }
        });

        //自动更换3D效果中的图片
        AutoChangePicture autoPic = new AutoChangePicture(imswitcher,
                ConstantHome.imageIds, vs, count);
        autoPic.execute(100);

        return elasticScrollView;
    }
}

