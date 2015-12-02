package com.r_time_run.newmess.subactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.r_time_run.newmess.R;
import com.r_time_run.newmess.adapter.Adapter_ListView_detail;
import com.r_time_run.newmess.adapter.GridViewAdapter;
import com.r_time_run.newmess.adapter.ViewshopPagerAdapter;
import com.r_time_run.newmess.bean.CommentBean;
import com.r_time_run.newmess.bean.FoodsBean;
import com.r_time_run.newmess.bean.ReplyBean;
import com.r_time_run.newmess.bean.ShopsBean;
import com.r_time_run.newmess.constant.Constant;
import com.r_time_run.newmess.net.LoadImage;
import com.r_time_run.newmess.net.NMParameters;
import com.r_time_run.newmess.utils.ACache;
import com.r_time_run.newmess.utils.JSONUtil;
import com.r_time_run.newmess.utils.ViewPagerHomeleftUtil;
import com.r_time_run.newmess.view.HackyViewPager;
import com.r_time_run.newmess.view.MyGridView;
import com.r_time_run.newmess.view.MyListView;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends BaseActivity {

    private ArrayList<View> allListView;
    private int[] resId = {R.drawable.cute_boy_08, R.drawable.cute_boy_18, R.drawable.cute_boy_16, R.drawable.cute_boy_18, R.drawable.cute_boy_10};
    private int position = 0;
    private HackyViewPager viewPager;
    private ViewPager pager;
    private List<View> viewList;
    private PullToRefreshGridView vp_foods;
    private PullToRefreshListView vp_comment;
    private LayoutInflater li;
    private List<String> titleList, imageurl;
    private TabPageIndicator indicator;
    private int[] rid;
    private ViewPagerHomeleftUtil vpHomeleftClass;
    private int whichPage = 0;

    //    对shop页面进行填充
    private ArrayList<FoodsBean> gvBeanPagershopconcent;
    private GridViewAdapter svAdapterShopPager;

    private View gvShopFoods, lvShopComment;
    //  评论区域
    private com.r_time_run.newmess.view.MyListView listView;
    Adapter_ListView_detail adapter;
    private int count;                    //记录评论ID
    private List<CommentBean> list;
    private int[] imgs;                    //图片资源ID数组

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop2);

        li = LayoutInflater.from(this);
        indicator = (TabPageIndicator) findViewById(R.id.indicator_title);
        imageurl = new ArrayList<String>();
        imageurl.add(getIntent().getExtras().getString("shop_image"));
        imageurl.add(getIntent().getExtras().getString("shop_image1"));
        imageurl.add(getIntent().getExtras().getString("shop_image2"));
        imageurl.add(getIntent().getExtras().getString("shop_image3"));
        imageurl.add(getIntent().getExtras().getString("shop_image4"));
        gvBeanPagershopconcent = getAcacheData(TAG_SELECT_FOODS);
        svAdapterShopPager = new GridViewAdapter(this, gvBeanPagershopconcent);

//       通过网络获取服务器数据
        NMParameters paramsleft = new NMParameters();
        paramsleft.add("action", "refreshFoods");
        getData(TAG_SELECT_FOODS, Constant.URL_FOODS, paramsleft, "POST");
        //        初始化界面布局
        initViewPager();
        initShopContent();
        MyGridView foodsGridView = (MyGridView) gvShopFoods.findViewById(R.id.shop_foods);
        foodsGridView.setAdapter(svAdapterShopPager);
        initfoods();
        initcomment();

    }

    //    初始化头部的图片
    private void initViewPager() {
        if (allListView != null) {
            allListView.clear();
            allListView = null;
        }
        allListView = new ArrayList<View>();
        for (int i = 0; i < imageurl.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.pic_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.pic_item);
            new LoadImage(this).loadDrawable(imageurl.get(i), imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    //跳转到查看大图页面
                    Intent intent = new Intent(ShopActivity.this, ShowBigPictrue.class);
                    intent.putExtra("position", position);
                    intent.putExtra("requsetActivity", "shop");
                    startActivity(intent);
                }
            });
            allListView.add(view);
        }
        viewPager = (HackyViewPager) findViewById(R.id.iv_shopimage);
        ViewPagerAdapter adapter = new ViewPagerAdapter();
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                position = arg0;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        viewPager.setAdapter(adapter);
    }

    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return allListView.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (View) arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = allListView.get(position);
            container.addView(view);
            return view;
        }
    }

    /**
     * viewpager引导条的设置和viewpager的填充
     */
    public void initShopContent() {
        viewList = new ArrayList<View>();
        gvShopFoods = li.inflate(R.layout.activity_shop2_foods, null);
        lvShopComment = li.inflate(R.layout.activity_shop2_comments, null);
        viewList.add(gvShopFoods);
        viewList.add(lvShopComment);

        titleList = new ArrayList<String>();
        titleList.add("食品");
        titleList.add("评论");

        pager = (ViewPager) findViewById(R.id.pager_shop_content);
        ViewshopPagerAdapter adapter = new ViewshopPagerAdapter(viewList, titleList);
        pager.setAdapter(adapter);
        indicator = (TabPageIndicator) findViewById(R.id.indicator_title);
        indicator.setViewPager(pager);
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                whichPage = position;
                Log.i("页码", "" + whichPage);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //    对食物进行填充
    private void initfoods() {
        MyGridView foodsGridView = (MyGridView) gvShopFoods.findViewById(R.id.shop_foods);
        foodsGridView.setAdapter(svAdapterShopPager);
        foodsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                intent = new Intent(ShopActivity.this, BabyActivity.class);
                startActivity(intent);
            }
        });
    }

    //    对评论进行填充
    private void initcomment() {
        listView = (com.r_time_run.newmess.view.MyListView) lvShopComment.findViewById(R.id.listView_Detail);
        listView.setFocusable(false);
        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new Adapter_ListView_detail(this, getCommentData(), R.layout.adapter_listview_detail, handler);
        listView.setAdapter(adapter);
    }

    /**
     * 获取评论列表数据
     */
    private List<CommentBean> getCommentData() {
        imgs = new int[]{R.drawable.baby_detail, R.drawable.baby_detail,
                R.drawable.baby_detail, R.drawable.baby_detail};
        list = new ArrayList<CommentBean>();
        count = imgs.length;
        for (int i = 0; i < imgs.length; i++) {
            CommentBean bean = new CommentBean();
            bean.setId(i + 1);
            bean.setCommentImgId(imgs[i]);
            bean.setCommentNickname("昵称" + i);
            bean.setCommentTime("13:" + i + "5");
            bean.setCommnetAccount("12345" + i);
            bean.setCommentContent("评论内容评论内容评论内容");
            bean.setReplyList(getReplyData());
            list.add(bean);
        }
        return list;
    }
    /**
     * 获取回复列表数据
     */
    private List<ReplyBean> getReplyData() {
        List<ReplyBean> replyList = new ArrayList<ReplyBean>();
        return replyList;
    }
    @SuppressLint("HandlerLeak")
    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 10) {
                position = (Integer) msg.obj;
                replyComment();
            }
        }
    };

    private void replyComment() {
        ReplyBean bean = new ReplyBean();
        bean.setId(count + 10);
        bean.setCommentNickname(list.get(position).getCommentNickname());
        bean.setReplyNickname("我是商家");
        bean.setReplyContent("这位同学说的很对 哈哈哈哈哈哈哈哈哈哈啊哈哈");
        adapter.getReplyComment(bean, position);
        adapter.notifyDataSetChanged();
    }


    /**
     * 获取foods缓存数据
     */
    private ArrayList getAcacheData(int TAG) {
        ACache aCache = ACache.get(this);
        String json = aCache.getAsString(TAG + "");
        if (TAG == TAG_SELECT_FOODS) {
            ArrayList<FoodsBean> listBean = JSONUtil.getFoodsJson(json);
            return listBean;
        }
        return null;
    }

    /**
     * 对comment页进行填充
     **/

    @Override
    public void handleMsg(Message msg) {
        Bundle bundle = msg.getData();
        String json = bundle.getString("json");
        if (json == null) {
            System.out.println("json1111--------" + json + "-----" + msg.what);
            return;
        }
        if (msg.what == TAG_SELECT_FOODS) {
            ArrayList<FoodsBean> listBean = JSONUtil.getFoodsJson(json);
            gvBeanPagershopconcent = listBean;
            svAdapterShopPager.fillData(gvBeanPagershopconcent);
            svAdapterShopPager.notifyDataSetChanged();
        }
    }

}



