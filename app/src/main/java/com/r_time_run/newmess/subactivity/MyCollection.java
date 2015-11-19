package com.r_time_run.newmess.subactivity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.r_time_run.newmess.R;
import com.r_time_run.newmess.utils.PopupWindowManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyCollection extends Activity {

    private TextView bt_collection_back;
    private ListView lv_collection;
    private PopupWindow popup_collection;
    private LinearLayout ll_mycollection;
    private String[] titles;    //标题
    private int[] images;       //图片
    private String[] dirs;      //地址
    private String[] times;     //打包时间
    private String[] praises;      //点赞数
    private String[] buys;         //购买数
    private String[] prices;        //价钱
    private String[] hours;
    private String[] minutes;
    private Bundle data;
    private List<Map<String, Object>> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);

        bt_collection_back = (TextView)findViewById(R.id.bt_collection_back);
        lv_collection = (ListView)findViewById(R.id.lv_collection);
        ll_mycollection = (LinearLayout) findViewById(R.id.ll_mycollection);

        bt_collection_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCollection.this.finish();
            }
        });

        getDataFromServlet();           //从服务器中获取数据并填充到数组中
        listItems = new ArrayList<>();
        if (titles == null)
            setDefaultData();
        for (int i = 0;i < titles.length;i++){
            Map<String,Object> item = new HashMap<String, Object>();
            item.put("image",images[i]);
            item.put("title",titles[i]);
            item.put("dir",dirs[i]);
            item.put("time",times[i]);
            listItems.add(item);
        }
        final SimpleAdapter simpleAdapter = new SimpleAdapter(this,listItems,
                R.layout.simple_item2,
                new String[]{"image","title","dir","time"},
                new int[]{R.id.iv_mycollection_image, R.id.tv_mycollection_title, R.id.tv_mycollection_dir, R.id.tv_mycollection_time});
        lv_collection.setAdapter(simpleAdapter);
        final View popup_collectionView = this.getLayoutInflater().inflate(R.layout.popup_my_collection,null);
        lv_collection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                popup_collection = new PopupWindow(popup_collectionView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


                data = new Bundle();
                data.putString("title", titles[position]);
                data.putInt("image", images[position]);
                data.putString("dir", dirs[position]);
                data.putString("time", times[position]);
                data.putString("praise", praises[position]);
                data.putString("buy", buys[position]);
                data.putString("price", prices[position]);
                data.putString("hour", hours[position]);
                data.putString("minute", minutes[position]);
                PopupWindowManager popupWindowManager = new PopupWindowManager(MyCollection.this, popup_collectionView, data);
                popupWindowManager.setDataToView();

                ScrollView sv_bagDialog = (ScrollView) popup_collectionView.findViewById(R.id.sv_bagDialog);
                sv_bagDialog.startAnimation(AnimationUtils.loadAnimation(MyCollection.this, R.anim.my_info_in));
                popup_collection.setFocusable(true);
                popup_collection.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                popup_collectionView.findViewById(R.id.bt_bagOrder_close).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popup_collection.dismiss();
                    }
                });
                popup_collectionView.findViewById(R.id.bt_pop_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteOrder(position, simpleAdapter);
                    }
                });


            }
        });
        /**
         * 该popupWindow"页面"能接受点击事件
         */
        popup_collectionView.setFocusable(true);
        popup_collectionView.setFocusableInTouchMode(true);
        popup_collectionView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (popup_collection != null) {
                        System.out.println("dimiss");
                        popup_collection.dismiss();
                    }
                }
                return false;
            }
        });
    }

    /**
     * 删除收藏中的订单
     * @param position
     * @param simpleAdapter
     */
    private void deleteOrder(int position, SimpleAdapter simpleAdapter) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(MyCollection.this.getFilesDir().toString() + "/newmessfoodbag.db3", null);
        String hour = data.getString("hour");
        String minute = data.getString("minute");
        if (hour != null && minute != null) {
            Cursor cursor = db.rawQuery("select * from food_collection"
                    , null);
            while (cursor.moveToNext()) {
                if (hour.equals(cursor.getString(7)) && minute.equals(cursor.getString(8))) {
                    db.execSQL("delete from food_collection where hour=? and minute=?",
                            new String[]{cursor.getString(7), cursor.getString(8)});
                }
            }
            popup_collection.dismiss();
            listItems.remove(position);
            simpleAdapter.notifyDataSetChanged();
        }
    }

    private void getDataFromServlet() {
        /**
         * 从服务器获取数据填写入该数组中
         */
        int index = 0;
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString() + "/newmessfoodbag.db3", null);
        int cursorNum = 0;
        try {
            Cursor cursor = db.rawQuery("select * from food_collection", null);
            cursorNum = cursor.getCount();
            titles = new String[cursorNum];
            images = new int[cursorNum];
            dirs = new String[cursorNum];
            times = new String[cursorNum];
            praises = new String[cursorNum];
            prices = new String[cursorNum];
            buys = new String[cursorNum];
            hours = new String[cursorNum];
            minutes = new String[cursorNum];
            while (cursor.moveToNext()) {
                titles[index] = cursor.getString(0);
                dirs[index] = cursor.getString(1);
                buys[index] = cursor.getString(2);
                prices[index] = cursor.getString(3);
                times[index] = cursor.getString(5)+"月"+cursor.getString(6)+"日 "+cursor.getString(7)+":"+cursor.getString(8);
                images[index] = R.drawable.food;
                hours[index] = cursor.getString(7);
                minutes[index] = cursor.getString(8);
                System.out.println("!!!" + titles[index] + "." + dirs[index] + ":" + buys[index] + ":");
                index++;
            }
        } catch (SQLiteException se) {

        }
    }

    private void setDefaultData() {
        titles = new String[]{"您还没有打包任何东西"};
        images = new int[]{R.drawable.food};
        dirs = new String[]{""};
        times = new String[]{""};
        praises = new String[]{""};
        buys = new String[]{""};
        prices = new String[]{""};
    }


}
