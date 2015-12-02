package com.r_time_run.newmess.subactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.r_time_run.newmess.R;
import com.r_time_run.newmess.adapter.ParttimeAdapter;

import java.util.ArrayList;
import java.util.List;

public class PartTimeActivity extends AppCompatActivity {

    private LinearLayout  goback,parttime_fromparse,parttime_fromtlong;
    private View parttime_0,parttime_1;
    private PullToRefreshListView parttimelist;
    private ArrayList<String> parttime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_time);
        goback = (LinearLayout) findViewById(R.id.parttime_header_return);
        parttime_fromparse = (LinearLayout) findViewById(R.id.parttime_fromparse);
        parttime_fromtlong = (LinearLayout) findViewById(R.id.parttime_fromtlong);
        parttime_0 = findViewById(R.id.parttime_0);
        parttime_1 = findViewById(R.id.parttime_1);
        headChang();
        parttimelist = (PullToRefreshListView) findViewById(R.id.pull_refresh_parttime);
        parttime = new ArrayList<String>();
        addList();
        ParttimeAdapter parttimeAdapter = new ParttimeAdapter(PartTimeActivity.this,parttime);
        parttimelist.setAdapter(parttimeAdapter);
    }
    private void headChang(){
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PartTimeActivity.this, Main_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        parttime_fromparse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parttime_0.setVisibility(View.VISIBLE);
                parttime_1.setVisibility(View.INVISIBLE);
            }
        });
        parttime_fromtlong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parttime_0.setVisibility(View.INVISIBLE);
                parttime_1.setVisibility(View.VISIBLE);
            }
        });
    }

    private void addList(){
        parttime.add("汉味黑鸭（第二饭堂）");
        parttime.add("北京卤肉卷（第二饭堂）");
        parttime.add("粤式风味糖水（第四饭堂）");
        parttime.add("置禾超市（第二饭堂）");
        parttime.add("7years（第二食堂）");
        parttime.add("阿汤美食店（第二饭堂）");
        parttime.add("鲜果园（第二饭堂）");
        parttime.add("扬州三鲜炒饭（第一饭堂）");
        parttime.add("狗不理包子（第二食堂）");
        parttime.add("阿秀饼（第一饭堂）");
        parttime.add("四川小炒（第二饭堂）");
        parttime.add("潮汕牛肉丸（第一饭堂）");
        parttime.add("江门冒菜（第三饭堂）");
        parttime.add("广州阿妹店（第三饭堂）");
        parttime.add("兰州牛肉拉面（第一食堂）");
    }
}
