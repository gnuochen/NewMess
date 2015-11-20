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

public class EvaluateOrder extends Activity {
    private TextView bt_evaluateOrder_back;
    private ListView lv_evaluateOrder;
    private PopupWindow popup_evaluateOrder;
    private List<Map<String, Object>> listItems;
    private View popup_evaluateOrderView;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_order);

        getDataFromServlet();           //从服务器中获取数据并填充到数组中
        initActivity();         //初始化订单详情页面

        if (listItems != null){
            SimpleAdapter simpleAdapter = new SimpleAdapter(this,listItems,
                    R.layout.simple_item2,
                    new String[]{"image","title","dir","time"},
                    new int[]{R.id.iv_mycollection_image, R.id.tv_mycollection_title, R.id.tv_mycollection_dir, R.id.tv_mycollection_time});
            lv_evaluateOrder.setAdapter(simpleAdapter);
        }
        lv_evaluateOrder.setOnItemClickListener(new ItemClickListener());

        /**
         * 该popupWindow"页面"能接受点击事件
         */
        popup_evaluateOrderView.setFocusable(true);
        popup_evaluateOrderView.setFocusableInTouchMode(true);
        popup_evaluateOrderView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (popup_evaluateOrder != null) {
                        popup_evaluateOrder.dismiss();
                    }
                }
                return false;
            }
        });

    }

    /**
     * 初始化订单详情页面
     */
    private void initActivity() {
        bt_evaluateOrder_back = (TextView)findViewById(R.id.bt_evaluateorder_back);
        lv_evaluateOrder = (ListView)findViewById(R.id.lv_evaluateorder);
        //没有订单时显示
        lv_evaluateOrder.setEmptyView(findViewById(R.id.tv_empty_item));
        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString() + "/order_message_db.db3", null);
        popup_evaluateOrderView = this.getLayoutInflater().inflate(R.layout.popup_order_evaluate, null);
        //返回键
        bt_evaluateOrder_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EvaluateOrder.this.finish();
            }
        });
    }

    /**
     * 从服务器获取数据填写入该数组中
     */
    private void getDataFromServlet() {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString() + "/order_message_db.db3", null);
        try {
            Cursor cursor = db.rawQuery("select * from food_evaluate", null);
            listItems = new ArrayList<>();
            while (cursor.moveToNext()) {
                Map<String,Object> item = new HashMap<String, Object>();
                item.put("image",R.drawable.food);
                item.put("title",cursor.getString(0));
                item.put("dir",cursor.getString(1));
                item.put("time",cursor.getString(5)+"月"+cursor.getString(6)+"日 "+cursor.getString(7)+":"+cursor.getString(8));
                item.put("buys",cursor.getString(2));
                item.put("prices",cursor.getString(3));
                item.put("hours", cursor.getString(7));
                item.put("minutes", cursor.getString(8));
                listItems.add(item);
            }
        } catch (SQLiteException se) {

        }
    }

    /**
     * 保存评价订单的数据
     * 使用SQLite来保存
     * */
    private void setSaveData(Cursor cursor){
        String sqlCreatTable = "create table food_collection(name varchar(50),adress varchar(100)," +
                "number varchar(10),price varchar(10),year varchar(50),month varchar(50)" +
                ",day varchar(50),hour varchar(50),minute varchar(50))";

        String foodName = cursor.getString(0);
        String foodLocation = cursor.getString(1);
        String foodNum = cursor.getString(2);
        String foodPrice = cursor.getString(3);
        String year = cursor.getString(4);
        String month = cursor.getString(5);
        String day = cursor.getString(6);
        String hour = cursor.getString(7);
        String minute = cursor.getString(8);

        try {
            insertData(db, foodName, foodLocation, foodNum, foodPrice, year, month, day, hour, minute);
        } catch (SQLiteException se){
            db.execSQL(sqlCreatTable);
            insertData(db, foodName, foodLocation, foodNum, foodPrice, year, month, day, hour, minute);
        }
    }

    /**
     * 将数据插入到收藏数据库中
     * @param db
     * @param foodName
     * @param foodLocation
     * @param foodNum
     * @param foodPrice
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     */
    private void insertData(SQLiteDatabase db, String foodName, String foodLocation, String foodNum, String foodPrice,String year,String month,String day,String hour,String minute) {
        db.execSQL("insert into food_collection values(?,?,?,?,?,?,?,?,?)", new String[]{foodName, foodLocation, foodNum, foodPrice, year + "", month + "", day + "", hour + "", minute + ""});
    }

    private class ItemClickListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            popup_evaluateOrder = new PopupWindow(popup_evaluateOrderView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            Bundle data = new Bundle();
            data.putString("title", (String) listItems.get(position).get("title"));
            data.putInt("image", R.drawable.food);
            data.putString("dir", (String) listItems.get(position).get("dir"));
            data.putString("time", (String) listItems.get(position).get("time"));
            data.putString("praise", (String) listItems.get(position).get("praise"));
            data.putString("buy", (String) listItems.get(position).get("buys"));
            data.putString("price", (String) listItems.get(position).get("prices"));
            data.putString("hour", (String) listItems.get(position).get("hours"));
            data.putString("minute",(String) listItems.get(position).get("minutes"));
            PopupWindowManager popupWindowManager = new PopupWindowManager(EvaluateOrder.this, popup_evaluateOrderView, data);
            popupWindowManager.setDataToView();

            ScrollView sv_bagDialog = (ScrollView) popup_evaluateOrderView.findViewById(R.id.sv_bagDialog);
            sv_bagDialog.startAnimation(AnimationUtils.loadAnimation(EvaluateOrder.this, R.anim.my_info_in));
            popup_evaluateOrder.setFocusable(true);
            popup_evaluateOrder.showAtLocation(view, Gravity.BOTTOM, 0, 0);
            popup_evaluateOrderView.findViewById(R.id.bt_bagOrder_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popup_evaluateOrder.dismiss();
                }
            });
            popup_evaluateOrderView.findViewById(R.id.bt_pop_collection).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = 0;
                    Cursor cursor = db.rawQuery("select * from food_evaluate"
                            , null);

                    while (cursor.moveToNext()) {
                        if (index == position) {
                            setSaveData(cursor);
                        }
                        index ++;
                    }
                    popup_evaluateOrder.dismiss();
                }
            });
        }

    }
}
