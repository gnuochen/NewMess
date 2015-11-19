package com.r_time_run.newmess.subactivity;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.r_time_run.newmess.FirstActivity;
import com.r_time_run.newmess.R;
import com.r_time_run.newmess.adapter.SquareViewAdapter;
import com.r_time_run.newmess.bean.FoodsBean;
import com.r_time_run.newmess.bean.ShopsBean;
import com.r_time_run.newmess.utils.ACache;
import com.r_time_run.newmess.utils.JSONUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SquareActivity extends BaseActivity {

    private PullToRefreshListView mPullRefreshSquareView;
    private ArrayList<String> list;
    private LayoutInflater li;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square);
        mPullRefreshSquareView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list_square);
        list = new ArrayList<String>();
        li = LayoutInflater.from(this);
        initUI();

    }

    private void initUI() {
        for (int i = 0; i < 10; i++) {
            list.add("" + i);
        }
        SquareViewAdapter squareViewAdapter = new SquareViewAdapter(this, list);

        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        View header = li.inflate(R.layout.activity_square_header, mPullRefreshSquareView, false);


        header.setLayoutParams(layoutParams);
        ListView lv = mPullRefreshSquareView.getRefreshableView();
        lv.addHeaderView(header);
        mPullRefreshSquareView.setAdapter(squareViewAdapter);
    }

    private ArrayList getAcacheData(int TAG) {
        ACache aCache = ACache.get(this);
        String json = aCache.getAsString(TAG + "");
        if (TAG == TAG_SELECT_FOODS) {
            ArrayList<FoodsBean> listBean = JSONUtil.getFoodsJson(json);
            return listBean;
        } else if (TAG == TAG_SELECT_SHOPS) {
            ArrayList<ShopsBean> listBean = JSONUtil.getShopsJson(json);
            return listBean;
        }
        return null;
    }

    @Override
    public void handleMsg(Message msg) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();     }
        return true;
    }

}
