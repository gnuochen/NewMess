package com.r_time_run.newmess;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import com.r_time_run.newmess.subactivity.Main_Activity;
import com.r_time_run.newmess.subactivity.SquareActivity;


public class FirstActivity extends TabActivity {


    TabHost tabHost;
    private ImageView tv_food, tv_thing, tv_news;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        initTab();
        init();

    }

    public void init() {
        tv_thing = (ImageView) findViewById(R.id.ll_home);
        tv_news = (ImageView) findViewById(R.id.ll_user);
        tabHost.setCurrentTabByTag("thing");
        tv_thing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabHost.setCurrentTabByTag("thing");
                setGuidShow();
                tv_thing.setImageResource(R.drawable.first_home);
            }
        });
        tv_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabHost.setCurrentTabByTag("news");
                setGuidShow();
                tv_news.setImageResource(R.drawable.first_user_on);
            }
        });
    }

    public void setGuidShow() {
        tv_thing.setImageResource(R.drawable.first_home_on);
        tv_news.setImageResource(R.drawable.first_user);
    }

    public void initTab() {
        tabHost = getTabHost();
        tabHost.addTab(tabHost.newTabSpec("home").setIndicator("home")
                .setContent(new Intent(this, SquareActivity.class)));
        tabHost.addTab(tabHost.newTabSpec("thing").setIndicator("thing")
                .setContent(new Intent(this, Main_Activity.class)));
        tabHost.addTab(tabHost.newTabSpec("news").setIndicator("news")
                .setContent(new Intent(this, MyInfoActivity.class)));
    }

}
