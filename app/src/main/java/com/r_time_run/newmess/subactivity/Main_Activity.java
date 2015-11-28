package com.r_time_run.newmess.subactivity;

import android.content.Intent;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.r_time_run.newmess.R;
import com.r_time_run.newmess.adapter.GridViewAdapter;
import com.r_time_run.newmess.adapter.MyPagerAdapter;
import com.r_time_run.newmess.adapter.ShopViewAdapter;
import com.r_time_run.newmess.bean.FoodsBean;
import com.r_time_run.newmess.bean.ShopsBean;
import com.r_time_run.newmess.constant.Constant;
import com.r_time_run.newmess.listener.GuidePageChangeListener;
import com.r_time_run.newmess.net.NMParameters;
import com.r_time_run.newmess.utils.JSONUtil;
import com.r_time_run.newmess.utils.ViewPagerHomeShopsUtil;
import com.r_time_run.newmess.utils.ViewPagerHomeUtil;
import com.r_time_run.newmess.utils.ViewPagerHomeleftUtil;
import com.r_time_run.newmess.view.ElasticScrollView;
import com.r_time_run.newmess.utils.ACache;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class Main_Activity extends BaseActivity {


    private LayoutInflater li;
    private View[] viewLayout;
    private ArrayList<View> views;
    private ViewPager mViewPager;
    private ImageView imageViews[];
    private ImageView imageView;
    private ElasticScrollView elasticScrollView;
    private boolean isContinue = true;
    private AtomicInteger what = new AtomicInteger(1);
    private ViewPagerHomeleftUtil vpHomeleftClass;
    private ViewPagerHomeShopsUtil vpHomeShopClass;
    private ArrayList<FoodsBean> gvBeanPagerOneconcent;
    private ArrayList<ShopsBean> lvBeanPagerThreeconcent;
    private GridViewAdapter svAdapterOnePager;
    private ShopViewAdapter svadapterThreePager;
    private PullToRefreshGridView mPullRefreshListView;
    private PullToRefreshListView mPullRefreshShopView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_);
        li = LayoutInflater.from(this);
        mViewPager = (ViewPager) findViewById(R.id.home_activity_viewPager);

        gvBeanPagerOneconcent = getAcacheData(TAG_SELECT_FOODS);
        lvBeanPagerThreeconcent = getAcacheData(TAG_SELECT_SHOPS);



        //        初始化三个viewPager页面的显示页面对象
        viewLayout = new View[3];
        initeLayout();
//        初始化主页面
        initeViewPagerOne();
        vpHomeClass.initeViewTwoContrls();
        mViewPager.setCurrentItem(1);
        /*
        *通过服务器获取数据
        * */
        NMParameters paramsleft = new NMParameters();
        paramsleft.add("action", "refreshFoods");
        getData(TAG_SELECT_FOODS, Constant.URL_FOODS, paramsleft, "POST");
        NMParameters paramsshops = new NMParameters();
        paramsshops.add("action","refreshShops");
        getData(TAG_SELECT_SHOPS, Constant.URL_FOODS, paramsshops, "POST");
//        给适配器填充数据
        svAdapterOnePager = new GridViewAdapter(this, gvBeanPagerOneconcent);
        svadapterThreePager = new ShopViewAdapter(this,lvBeanPagerThreeconcent);

//        对第三个页面进行填充
        initeViewPangetThree();

//        对第二个页面进行填充
        initeViewPangetTwo();

    }

    private void initeLayout() {
        // TODO Auto-generated method stub
        int[] rId = new int[]{R.layout.vp_item_one,
                R.layout.vp_item_two, R.layout.vp_item_three};
        for (int i = 0; i < rId.length; i++) {
            viewLayout[i] = (View) li.inflate(rId[i], null);
        }
        views = new ArrayList<View>();
        for (int i = 0; i < viewLayout.length; i++) {
            views.add(viewLayout[i]);
        }

        // 填充页面显示的三个viewPager布局view
        mViewPager.setAdapter(new MyPagerAdapter(views));
        // ViewPager翻页的页面引导效果
    }

    /**
     * 对第一个页面进行填充
     */
    private ViewPagerHomeUtil vpHomeClass;

    private void initeViewPagerOne() {
        //使用重构类填充完善ViewPager
        vpHomeClass = new ViewPagerHomeUtil(this, views, mHandler,mViewPager, 1);//int tag临时调试用
    }

    /**
     * 对第二个页面进行填充
     */
    private void initeViewPangetTwo() {
//        使用同构类填充完善viewpager
        vpHomeleftClass = new ViewPagerHomeleftUtil(this, views, svAdapterOnePager, mHandler,mViewPager, TAG_SELECT_FOODS);
        mPullRefreshListView = vpHomeleftClass.viewPagerHomeleftctrol();
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                Log.e("TAG", "onPullDownToRefresh");
                String label = DateUtils.formatDateTime(
                        getApplicationContext(),
                        System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy()
                        .setLastUpdatedLabel(label);
                NMParameters paramsleft = new NMParameters();
                paramsleft.add("action","refreshFoods");
                getData(TAG_SELECT_FOODS, Constant.URL_FOODS, paramsleft, "POST");
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                Log.e("TAG", "onPullUpToRefresh"); // Do work to refresh
                // the list here.
                NMParameters paramsleft = new NMParameters();
                paramsleft.add("action","addFoods");
                getData(2, Constant.URL_FOODS, paramsleft, "POST");
            }
        });
    }
    /**
     * 对第三个页面进行填充
     * */
    private void initeViewPangetThree(){
        vpHomeShopClass = new ViewPagerHomeShopsUtil(this, views,svadapterThreePager, mHandler,mViewPager,301);
        mPullRefreshShopView = vpHomeShopClass.viewPagerHomeShopsctrol();
        mPullRefreshShopView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                Log.e("TAG", "onPullDownToRefresh");
                String label = DateUtils.formatDateTime(
                        getApplicationContext(),
                        System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy()
                        .setLastUpdatedLabel(label);
                NMParameters paramsleft = new NMParameters();
                paramsleft.add("action", "refreshShops");
                getData(TAG_SELECT_SHOPS, Constant.URL_FOODS, paramsleft, "POST");
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                Log.e("TAG", "onPullUpToRefresh"); // Do work to refresh
                // the list here.
                NMParameters paramsleft = new NMParameters();
                paramsleft.add("action","addShops");
                getData(302, Constant.URL_FOODS, paramsleft, "POST");
            }
        });
    }



    /**
     * 页面引导效果 的指引页面改监听器
     */
    private void whatOption() {
        what.incrementAndGet();
        if (what.get() > imageViews.length - 1) {
            what.getAndAdd(-4);
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
    }
    private ArrayList getAcacheData(int TAG){
        ACache aCache = ACache.get(this);
        String json = aCache.getAsString(TAG + "");
        if(TAG==TAG_SELECT_FOODS){
            ArrayList<FoodsBean> listBean = JSONUtil.getFoodsJson(json);
            return listBean;
        }
        else if(TAG ==TAG_SELECT_SHOPS){
            ArrayList<ShopsBean> listBean = JSONUtil.getShopsJson(json);
            return listBean;
        }
        return null;
    }


    public void handleMsg(Message msg) {
        Bundle bundle = msg.getData();
        String json = bundle.getString("json");
        if (json == null) {
            System.out.println("json1111--------" + json + "-----" + msg.what);
            return;
        }
        if (msg.what == TAG_SELECT_FOODS) {
            ArrayList<FoodsBean> listBean = JSONUtil.getFoodsJson(json);
            gvBeanPagerOneconcent = listBean;
            svAdapterOnePager.fillData(gvBeanPagerOneconcent);
            svAdapterOnePager.notifyDataSetChanged();
            mPullRefreshListView.onRefreshComplete();
        }
        else if(msg.what == 2 ){
            ArrayList<FoodsBean> listBean = JSONUtil.getFoodsJson(json);
            gvBeanPagerOneconcent = listBean;
            svAdapterOnePager.addData(gvBeanPagerOneconcent);
            svAdapterOnePager.notifyDataSetChanged();
            mPullRefreshListView.onRefreshComplete();
        }
        else if(msg.what == TAG_SELECT_SHOPS ){
            ArrayList<ShopsBean> listBean = JSONUtil.getShopsJson(json);
            lvBeanPagerThreeconcent = listBean;
            svadapterThreePager.fillData(lvBeanPagerThreeconcent);
            svadapterThreePager.notifyDataSetChanged();
            mPullRefreshShopView.onRefreshComplete();
        }
        else if(msg.what == 302){
            ArrayList<ShopsBean> listBean = JSONUtil.getShopsJson(json);
            lvBeanPagerThreeconcent = listBean;
            svadapterThreePager.addData(lvBeanPagerThreeconcent);
            svadapterThreePager.notifyDataSetChanged();
            mPullRefreshShopView.onRefreshComplete();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (mViewPager.getCurrentItem() != 1){
                mViewPager.setCurrentItem(1);
            } else {
                exitBy2Click();
            }
        }
        return true;
    }
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }
}
