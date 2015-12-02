package com.r_time_run.newmess.subactivity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.r_time_run.newmess.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class News_Activity extends AppCompatActivity {
    private ViewPager vpNews;
    private LinearLayout llPointgroup;
    private TextView tvNews;
    private final int[] imageIDs={R.drawable.news1,R.drawable.news1};
    private ArrayList<ImageView> imageList;
    private TextView tvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        vpNews = (ViewPager) findViewById(R.id.vp_news);
        tvBack= (TextView) findViewById(R.id.iv_back);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        llPointgroup= (LinearLayout) findViewById(R.id.ll_pointgroup);
//        初始化图片资源
        imageList=new ArrayList<ImageView>();
        for (int i=0;i<imageIDs.length;i++){
            ImageView image=new ImageView(this);
            image.setBackgroundResource(imageIDs[i]);
            imageList.add(image);
        }
        tvNews = (TextView) findViewById(R.id.tv_news);
        vpNews.setAdapter(new MyPagerAdapter());
    }
    private class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return imageList.size();
        }

        /**
         * 判断view和object的对应关系
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        /**
         * 获得相应位置的view
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageList.get(position));
            return imageList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            object=null;
//            super.destroyItem(container, position, object);
        }
    }

}
