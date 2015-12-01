package com.r_time_run.newmess.subactivity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.r_time_run.newmess.R;

import org.w3c.dom.Text;

public class News_Activity extends AppCompatActivity {
    private ViewPager vpNews;
    private LinearLayout llPointgroup;
    private TextView tvNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        vpNews = (ViewPager) findViewById(R.id.vp_news);
        llPointgroup= (LinearLayout) findViewById(R.id.ll_pointgroup);
        tvNews = (TextView) findViewById(R.id.tv_news);
        vpNews.setAdapter(new MyPagerAdapter());
    }
    private class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }

}
