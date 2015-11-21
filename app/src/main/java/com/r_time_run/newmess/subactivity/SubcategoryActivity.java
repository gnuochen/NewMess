package com.r_time_run.newmess.subactivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.r_time_run.newmess.R;
import com.r_time_run.newmess.adapter.SubcategoryAdapter;
import com.r_time_run.newmess.adapter.SubcategoryAdapterFirst;
import com.r_time_run.newmess.bean.SubcategoryBean;
import com.r_time_run.newmess.constant.Constant;
import com.r_time_run.newmess.net.NMParameters;
import com.r_time_run.newmess.utils.JSONUtil;
import com.r_time_run.newmess.utils.NetworkUtil;

import java.util.ArrayList;

public class SubcategoryActivity extends BaseActivity {
    private ListView mListView1, mListView2;

    SubcategoryAdapter secondAdapter;

    SubcategoryAdapterFirst firstAdapter;

    static int flag;

    View view;

    private LayoutInflater inflater;

    ArrayList<SubcategoryBean> listBean;

    int imageViewFood0[] = {R.drawable.suancairousifan, R.drawable.suancairousifan, R.drawable.suancairousifan,
            R.drawable.suancairousifan, R.drawable.suancairousifan, R.drawable.suancairousifan, R.drawable.suancairousifan,
            R.drawable.suancairousifan, R.drawable.suancairousifan};

    int imageViewFood1[] = {R.drawable.roubao, R.drawable.roubao, R.drawable.roubao,
            R.drawable.roubao, R.drawable.roubao, R.drawable.roubao, R.drawable.roubao,
            R.drawable.roubao, R.drawable.roubao};

    int imageViewFood2[] = {R.drawable.lvdoutang, R.drawable.lvdoutang, R.drawable.lvdoutang,
            R.drawable.lvdoutang, R.drawable.lvdoutang, R.drawable.lvdoutang, R.drawable.lvdoutang,
            R.drawable.lvdoutang, R.drawable.lvdoutang};

    int imageViewFood3[] = {R.drawable.naicha, R.drawable.naicha, R.drawable.naicha,
            R.drawable.naicha, R.drawable.naicha, R.drawable.naicha, R.drawable.naicha,
            R.drawable.naicha, R.drawable.naicha};

    int imageViewFood4[] = {R.drawable.shouzhuabing, R.drawable.shouzhuabing, R.drawable.shouzhuabing,
            R.drawable.shouzhuabing, R.drawable.shouzhuabing, R.drawable.shouzhuabing, R.drawable.shouzhuabing,
            R.drawable.shouzhuabing, R.drawable.shouzhuabing};

    int imageViewFood5[] = {R.drawable.binggan, R.drawable.binggan, R.drawable.binggan,
            R.drawable.binggan, R.drawable.binggan, R.drawable.binggan, R.drawable.binggan,
            R.drawable.binggan, R.drawable.binggan};

    int imageViewFood6[] = {R.drawable.bingjiling, R.drawable.bingjiling, R.drawable.bingjiling,
            R.drawable.bingjiling, R.drawable.bingjiling, R.drawable.bingjiling, R.drawable.bingjiling,
            R.drawable.bingjiling, R.drawable.bingjiling};

    int imageViewFood7[] = {R.drawable.kele, R.drawable.kele, R.drawable.kele,
            R.drawable.kele, R.drawable.kele, R.drawable.kele, R.drawable.kele,
            R.drawable.kele, R.drawable.kele};

    String categorynameFood0[] = {"酸菜肉丝饭1", "酸菜肉丝饭2",
            "酸菜肉丝饭3", "酸菜肉丝饭4", "酸菜肉丝饭5", "酸菜肉丝饭6", "酸菜肉丝饭7"
            , "酸菜肉丝饭8", "酸菜肉丝饭9"};
    String categorynameFood1[] = {"肉包1", "肉包2",
            "肉包3", "肉包4", "肉包5", "肉包6", "肉包7", "肉包8", "肉包9"};
    String categorynameFood3[] = {"奶茶1", "奶茶2",
            "奶茶3", "奶茶4", "奶茶5", "奶茶6", "奶茶7", "奶茶8", "奶茶9"};
    String categorynameFood2[] = {"绿豆汤1", "绿豆汤2",
            "绿豆汤3", "绿豆汤4", "绿豆汤5", "绿豆汤6", "绿豆汤7", "绿豆汤8", "绿豆汤9"};
    String categorynameFood4[] = {"手抓饼1", "手抓饼2",
            "手抓饼3", "手抓饼4", "手抓饼5", "手抓饼6", "手抓饼7", "手抓饼8", "手抓饼9"};
    String categorynameFood5[] = {"饼干1", "饼干2",
            "饼干3", "饼干4", "饼干5", "饼干6", "饼干7", "饼干8", "饼干9"};
    String categorynameFood6[] = {"冰激凌1", "冰激凌2",
            "冰激凌3", "冰激凌4", "冰激凌5", "冰激凌6", "冰激凌7", "冰激凌8", "冰激凌9"};
    String categorynameFood7[] = {"可乐1", "可乐2",
            "可乐3", "可乐4", "可乐5", "可乐6", "可乐7", "可乐8", "可乐9"};


    String foodPriceAry[] = {"¥ 10", "¥ 10", "¥ 10",
            "¥ 10", "¥ 10", "¥ 10", "¥ 10", "¥ 10", "¥ 10"};

    String foodDetail[] = {"第一食堂潮汕小炒"};

    private String textName[] = {"主食", "早餐", "糖水", "奶茶", "小吃", "超市", "冰激凌", "其他"};

    Boolean state = false;

    private String foodLocation;
    private String foodName;
    private String foodPrice;
    private int foodImageID[]={R.drawable.suancairousifan, R.drawable.roubao, R.drawable.lvdoutang,
            R.drawable.naicha, R.drawable.shouzhuabing, R.drawable.binggan, R.drawable.bingjiling,
            R.drawable.kele};
    private int listView1Position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategory);
        initview();
        listView1FillData();
        flag = getIntent().getIntExtra("position", -1);
        listview1onclick();
        listBean = new ArrayList<SubcategoryBean>();
        secondAdapter = new SubcategoryAdapter(this, listBean, state);
        secondAdapter.fillData(listBean);
        listView2FillData(0);
        mListView2.setAdapter(secondAdapter);

        TextView bt_subcategory_back = (TextView) findViewById(R.id.bt_subcategory_back);
        bt_subcategory_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubcategoryActivity.this.finish();
            }
        });

        mListView2IntentToOther(0);
    }

    private void initview() {
        inflater = LayoutInflater.from(this);
        view = inflater.inflate(R.layout.headview, null);
        mListView1 = (ListView) findViewById(R.id.category_activity_listview1);
        mListView2 = (ListView) findViewById(R.id.category_activity_listview2);


    }

    /**
     * 左边listview填充数据
     */
    private void listView1FillData() {
        firstAdapter = new SubcategoryAdapterFirst(this, textName);
        mListView1.setAdapter(firstAdapter);
        mListView1.setBackgroundResource(R.drawable.zhijiao);

    }

    private void listview1onclick() {
        mListView1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                Log.d("vivi", "position==" + position);
                listView2FillData(position);
                firstAdapter.changeSelected(position);
                Log.d("vivi", "mListView2IntentToOther");
                mListView2IntentToOther(position);
                listView1Position=position;
            }
        });
    }

    public void listView2FillData(int listView1Position) {
        // TODO Auto-generated method stub
        listBean.clear();
        Log.d("vivi", "mListView2.getCount()==" + mListView2.getCount());
        if (NetworkUtil.isNetwork(this) && state == true) {
            Log.e("Net............", "HaveNet");
            NMParameters subcategoryParam = new NMParameters();
            subcategoryParam.add("action", "subcategoryFoods");
            getData(TAG_SELECT_SubCategory_FOODS, Constant.URL_FOODS, subcategoryParam, "POST");
        }

        switch (listView1Position) {
            case 0: {
                for (int i = 0; i < imageViewFood0.length; i++) {
                    SubcategoryBean bean = new SubcategoryBean(imageViewFood0[i],"zhushi", 0,
                            categorynameFood0[i], foodPriceAry[i], null,foodDetail[0]);
                    listBean.add(bean);
                }
                break;
            }
            case 1: {
                for (int i = 0; i < imageViewFood1.length; i++) {
                    SubcategoryBean bean = new SubcategoryBean(imageViewFood1[i],"zhushi", 0,
                            categorynameFood1[i], foodPriceAry[i], null,foodDetail[0]);
                    listBean.add(bean);
                }
                break;
            }
            case 2: {
                for (int i = 0; i < imageViewFood2.length; i++) {
                    SubcategoryBean bean = new SubcategoryBean(imageViewFood2[i],"zhushi", 0,
                            categorynameFood2[i], foodPriceAry[i], null,foodDetail[0]);
                    listBean.add(bean);
                }
                break;
            }
            case 3: {
                for (int i = 0; i < imageViewFood3.length; i++) {
                    SubcategoryBean bean = new SubcategoryBean(imageViewFood3[i],"zhushi", 0,
                            categorynameFood3[i], foodPriceAry[i], null,foodDetail[0]);
                    listBean.add(bean);
                }
                break;
            }
            case 4: {
                for (int i = 0; i < imageViewFood4.length; i++) {
                    SubcategoryBean bean = new SubcategoryBean(imageViewFood4[i],"zhushi", 0,
                            categorynameFood4[i], foodPriceAry[i], null,foodDetail[0]);
                    listBean.add(bean);
                }
                break;
            }
            case 5: {
                for (int i = 0; i < imageViewFood5.length; i++) {
                    SubcategoryBean bean = new SubcategoryBean(imageViewFood5[i],"zhushi", 0,
                            categorynameFood5[i], foodPriceAry[i], null,foodDetail[0]);
                    listBean.add(bean);
                }
                break;
            }
            case 6: {
                for (int i = 0; i < imageViewFood6.length; i++) {
                    SubcategoryBean bean = new SubcategoryBean(imageViewFood6[i],"zhushi", 0,
                            categorynameFood6[i], foodPriceAry[i], null,foodDetail[0]);
                    listBean.add(bean);

                }
                break;
            }
            case 7: {
                for (int i = 0; i < imageViewFood7.length; i++) {
                    SubcategoryBean bean = new SubcategoryBean(imageViewFood7[i],"zhushi", 0,
                            categorynameFood7[i], foodPriceAry[i], null,foodDetail[0]);
                    listBean.add(bean);
                }
                break;
            }
        }

        mListView2.setAdapter(secondAdapter);


    }

    private void mListView2IntentToOther(int flag2) {
        Log.d("mListView2IntentToOther", "position==" + flag2);
        mListView2.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                switch (position) {
                    default:
                        foodName = listBean.get(position).getmSubCategoryFoodName();
                        foodLocation = listBean.get(position).getmSubCategoryFoodDeatil();
                        foodPrice=listBean.get(position).getmSubCategoryFoodPrice();

                        Log.e("SubcategoryActivity", foodName + "  " + foodLocation);
                        Intent intent = new Intent();
                        intent.setComponent(new ComponentName(SubcategoryActivity.this, BabyActivity.class));
                        intent.putExtra("foodName", foodName);
                        intent.putExtra("foodLocation", foodLocation);
                        intent.putExtra("foodPrice",foodPrice);
                        intent.putExtra("foodImageID",foodImageID);
                        intent.putExtra("subcategoryPosition",listView1Position);
                        startActivity(intent);
                        break;
                }

            }

        });
    }

    @Override
    public void handleMsg(Message msg) {

        Bundle bundle = msg.getData();
        String json = bundle.getString("json");
        state = bundle.getBoolean("state");

        if (json == null) {
            Log.e("eeee", "\"json1111--------\" + json + \"-----\" + msg.what");
            return;
        }
        if (msg.what == TAG_SELECT_SubCategory_FOODS && state == true) {
            listBean = JSONUtil.getSubcategoryBeanJson(json);
            secondAdapter.fillData(listBean);
            secondAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onException(int tag, String json) {
        super.onException(tag, json);
        Message msg = mHandler.obtainMessage();
        msg.what = tag;
        Bundle data = new Bundle();
        boolean state = false;
        data.putBoolean("state", state);
        msg.setData(data);
        mHandler.sendMessage(msg);
    }
}
