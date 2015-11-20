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
    private View popup_collectionView;
    private SimpleAdapter simpleAdapter;
    private Bundle data;
    private List<Map<String, Object>> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);

        getDataFromServlet();           //从服务器中获取数据并填充到数组中
        initActivity();         //初始化界面

        if (listItems != null){
            simpleAdapter = new SimpleAdapter(this, listItems,
                    R.layout.simple_item2,
                    new String[]{"image", "title", "dir", "time"},
                    new int[]{R.id.iv_mycollection_image, R.id.tv_mycollection_title, R.id.tv_mycollection_dir, R.id.tv_mycollection_time});
            lv_collection.setAdapter(simpleAdapter);
        }
        lv_collection.setOnItemClickListener(new ItemClickListener());

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
                        popup_collection.dismiss();
                    }
                }
                return false;
            }
        });
    }

    private void initActivity() {
        bt_collection_back = (TextView) findViewById(R.id.bt_collection_back);
        lv_collection = (ListView) findViewById(R.id.lv_collection);
        lv_collection.setEmptyView(findViewById(R.id.tv_empty_item));
        //返回
        bt_collection_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCollection.this.finish();
            }
        });
        popup_collectionView = this.getLayoutInflater().inflate(R.layout.popup_my_collection, null);
    }

    /**
     * 删除收藏中的订单
     *
     * @param position
     * @param simpleAdapter
     */
    private void deleteOrder(int position, SimpleAdapter simpleAdapter) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(MyCollection.this.getFilesDir().toString() + "/order_message_db.db3", null);
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
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString() + "/order_message_db.db3", null);
        try {
            listItems = new ArrayList<>();
            Cursor cursor = db.rawQuery("select * from food_collection", null);
            while (cursor.moveToNext()) {
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("image", R.drawable.food);
                item.put("title", cursor.getString(0));
                item.put("dir", cursor.getString(1));
                item.put("buys", cursor.getString(2));
                item.put("prices", cursor.getString(3));
                item.put("time", cursor.getString(5) + "月" + cursor.getString(6) + "日 " + cursor.getString(7) + ":" + cursor.getString(8));
                item.put("hours", cursor.getString(7));
                item.put("minutes", cursor.getString(8));
                listItems.add(item);
            }
        } catch (SQLiteException se) {

        }
    }

    private class ItemClickListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            popup_collection = new PopupWindow(popup_collectionView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            data = new Bundle();
            data.putInt("image", R.drawable.food);
            data.putString("title", (String) listItems.get(position).get("title"));
            data.putString("dir", (String) listItems.get(position).get("dir"));
            data.putString("time", (String) listItems.get(position).get("time"));
            data.putString("praise", (String) listItems.get(position).get("praise"));
            data.putString("buy", (String) listItems.get(position).get("buys"));
            data.putString("price", (String) listItems.get(position).get("prices"));
            data.putString("hour", (String) listItems.get(position).get("hours"));
            data.putString("minute", (String) listItems.get(position).get("minutes"));
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
    }

}
