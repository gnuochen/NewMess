package com.r_time_run.newmess.subactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.r_time_run.newmess.R;
import com.r_time_run.newmess.adapter.Adapter_ListView_detail;
import com.r_time_run.newmess.bean.CommentBean;
import com.r_time_run.newmess.bean.ReplyBean;
import com.r_time_run.newmess.view.BabyPopWindow;
import com.r_time_run.newmess.view.HackyViewPager;

import java.util.ArrayList;
import java.util.List;


public class BabyActivity extends FragmentActivity {
    private HackyViewPager viewPager;
    private ArrayList<View> allListView;
    private int[] resId = {R.drawable.huolongguo, R.drawable.niuroufan};
    private ImageView iv_baby_collection;
    private static boolean isCollection = false;
    private int position = 0;
    private ListView listView;
    /**
     * 用于设置背景暗淡
     */
    private LinearLayout all_choice_layout = null;
    private BabyPopWindow popWindow;
    private TextView tvBuyNow;

    private int count;					//记录评论ID
    Adapter_ListView_detail adapter;
    private List<CommentBean> list;
    private int[] imgs;					//图片资源ID数组
    private TextView tvFoodName;
    private TextView tvFoodLocation;
    private TextView tvFoodPrice;
    private String foodName;
    private String foodLocation;
    private String foodPrice;
    private int foodImageID[];
    private int subcategoryPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.babydetail_a);

        tvFoodName= (TextView) findViewById(R.id.tv_babyactivity_foodname);
        tvFoodLocation= (TextView) findViewById(R.id.tv_babyactivity_foodlocation);
        tvFoodPrice= (TextView) findViewById(R.id.tv_babyactivity_foodprice);
        foodName=getIntent().getExtras().getString("foodName");
        foodLocation=getIntent().getExtras().getString("foodLocation");
        foodPrice=getIntent().getExtras().getString("foodPrice");
        foodImageID=getIntent().getExtras().getIntArray("foodImageID");
        subcategoryPosition=getIntent().getExtras().getInt("subcategoryPosition");
        tvFoodName.setText(foodName);
        tvFoodLocation.setText(foodLocation);
        tvFoodPrice.setText(foodPrice);
        listView = (ListView) findViewById(R.id.listView_Detail);
        listView.setFocusable(false);
        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter=new Adapter_ListView_detail(this,getCommentData(), R.layout.adapter_listview_detail,handler);
        listView.setAdapter(adapter);

        popWindow = new BabyPopWindow(this,foodName,foodLocation,foodPrice);
        popWindow.setOnItemClickListener(new BabyPopWindow.OnItemClickListener() {
            @Override
            public void onClickOKPop() {

            }
        });

        initView();
    }

//    private void sendValueToPopWindow(){
//        Message msg=Message.obtain();
//        Bundle b=new Bundle();
//        b.putString("foodName",foodName);
//        b.putString("foodLocation",foodLocation);
//        b.putString("foodPrice",foodPrice);
//        msg.setData(b);
//        msg.sendToTarget();
//    }

    private void initView() {
//        ((ImageView) findViewById(R.id.iv_back)).setOnClickListener(this);
//        ((ImageView) findViewById(R.id.put_in)).setOnClickListener(this);
//        ((ImageView) findViewById(R.id.buy_now)).setOnClickListener(this);
        all_choice_layout = (LinearLayout) findViewById(R.id.all_choice_layout);
        tvBuyNow= (TextView) findViewById(R.id.buy_now);
        iv_baby_collection = (ImageView) findViewById(R.id.iv_baby_collection);
        iv_baby_collection.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isCollection = !isCollection;
                if (isCollection) {
                    //如果已经收藏，则显示收藏后的效果
                    iv_baby_collection.setImageResource(R.drawable.second_2_collection);
                } else {
                    iv_baby_collection.setImageResource(R.drawable.second_2);
                }
            }
        });

        ((ImageView) findViewById(R.id.iv_back)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        刷新
        ((ImageView) findViewById(R.id.iv_refresh)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });


        tvBuyNow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("点击了打包");
                popWindow.showAsDropDown(tvBuyNow);

            }
        });


        initViewPager();
    }

//刷新的方法
    public void refresh(){

        findViewById(R.id.ll_baby).invalidate();
        Toast.makeText(this,"刷新完成",Toast.LENGTH_SHORT).show();
    }
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.iv_back:
//                //返回
//                finish();
//                break;
//            case R.id.iv_baby_collection:
//                //收藏
//                if (isCollection) {
//                    //提示是否取消收藏
//                    cancelCollection();
//                }else {
//                    isCollection=true;
//                    setSaveCollection();
//                    //如果已经收藏，则显示收藏后的效果
//                    iv_baby_collection.setImageResource(R.drawable.second_2_collection);
//                    Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case R.id.buy_now:
//                //立即购买
//                isClickBuy = true;
//                setBackgroundBlack(all_choice_layout, 0);
//                popWindow.showAsDropDown(view);
//                break;
//        }
//    }

    /**
     * 控制背景变暗 0变暗 1变亮
     */
    public void setBackgroundBlack(View view, int what) {
        switch (what) {
            case 0:
                view.setVisibility(View.VISIBLE);
                break;
            case 1:
                view.setVisibility(View.GONE);
                break;
        }
    }

    private void initViewPager() {

        if (allListView != null) {
            allListView.clear();
            allListView = null;
        }
        allListView = new ArrayList<View>();

        for (int i = 0; i < foodImageID.length; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.pic_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.pic_item);
            imageView.setImageResource(foodImageID[subcategoryPosition]);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    //挑战到查看大图界面
                    Intent intent = new Intent(BabyActivity.this, ShowBigPictrue.class);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
            });
            allListView.add(view);
        }

        viewPager = (HackyViewPager) findViewById(R.id.iv_baby);
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

    private void replyComment(){
        ReplyBean bean = new ReplyBean();
        bean.setId(count+10);
        bean.setCommentNickname(list.get(position).getCommentNickname());
        bean.setReplyNickname("我是商家");
        bean.setReplyContent("这位同学说的很对 哈哈哈哈哈哈哈哈哈哈啊哈哈");
        adapter.getReplyComment(bean, position);
        adapter.notifyDataSetChanged();
    }

    /**
     * 获取评论列表数据
     */
    private List<CommentBean> getCommentData(){
        imgs = new int[]{R.drawable.baby_detail, R.drawable.baby_detail,
                R.drawable.baby_detail, R.drawable.baby_detail};
        list = new ArrayList<CommentBean>();
        count = imgs.length;
        for(int i=0;i<imgs.length;i++){
            CommentBean bean = new CommentBean();
            bean.setId(i+1);
            bean.setCommentImgId(imgs[i]);
            bean.setCommentNickname("昵称"+i);
            bean.setCommentTime("13:"+i+"5");
            bean.setCommnetAccount("12345"+i);
            bean.setCommentContent("评论内容评论内容评论内容");
            bean.setReplyList(getReplyData());
            list.add(bean);
        }
        return list;
    }
    /**
     * 获取回复列表数据
     */
    private List<ReplyBean> getReplyData(){
        List<ReplyBean> replyList = new ArrayList<ReplyBean>();
        return replyList;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 10){
                position = (Integer)msg.obj;
                replyComment();
            }
        }
    };
}
