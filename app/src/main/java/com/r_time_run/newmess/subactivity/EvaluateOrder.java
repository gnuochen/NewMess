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
    private String[] titles;    //标题
    private int[] images;       //图片
    private String[] dirs;      //地址
    private String[] times;     //打包时间
    private String[] praises;      //点赞数
    private String[] buys;         //购买数
    private String[] prices;        //价钱
    private String[] hours;
    private String[] minutes;
    int index = 0;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_order);

        bt_evaluateOrder_back = (TextView)findViewById(R.id.bt_evaluateorder_back);
        lv_evaluateOrder = (ListView)findViewById(R.id.lv_evaluateorder);
        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString() + "/newmessfoodbag.db3", null);
        /**
         * 返回键
         */
        bt_evaluateOrder_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EvaluateOrder.this.finish();
            }
        });

        getDataFromServlet();           //从服务器中获取数据并填充到数组中
        List<Map<String, Object>> listItems = new ArrayList<>();
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
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,listItems,
                R.layout.simple_item2,
                new String[]{"image","title","dir","time"},
                new int[]{R.id.iv_mycollection_image, R.id.tv_mycollection_title, R.id.tv_mycollection_dir, R.id.tv_mycollection_time});
        lv_evaluateOrder.setAdapter(simpleAdapter);
        final View popup_evaluateOrderView = this.getLayoutInflater().inflate(R.layout.popup_order_evaluate, null);
        lv_evaluateOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                popup_evaluateOrder = new PopupWindow(popup_evaluateOrderView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                Bundle data = new Bundle();
                data.putString("title", titles[position]);
                data.putInt("image", images[position]);
                data.putString("dir", dirs[position]);
                data.putString("time", times[position]);
                data.putString("praise", praises[position]);
                data.putString("buy", buys[position]);
                data.putString("price", prices[position]);
                data.putString("hour", hours[position]);
                data.putString("minute", minutes[position]);
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
        });

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
                        System.out.println("dimiss");
                        popup_evaluateOrder.dismiss();
                    }
                }
                return false;
            }
        });

    }

    private void getDataFromServlet() {
        /**
         * 从服务器获取数据填写入该数组中
         */
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString() + "/newmessfoodbag.db3", null);
        int cursorNum = 0;
        try {
            Cursor cursor = db.rawQuery("select * from food_evaluate", null);
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
        System.out.println("!!!!"+hour+":"+minute);

        try {
            insertData(db, foodName, foodLocation, foodNum, foodPrice, year, month, day, hour, minute);
        } catch (SQLiteException se){
            db.execSQL(sqlCreatTable);
            insertData(db, foodName, foodLocation, foodNum, foodPrice, year, month, day, hour, minute);
        }
    }

    private void insertData(SQLiteDatabase db, String foodName, String foodLocation, String foodNum, String foodPrice,String year,String month,String day,String hour,String minute) {
        db.execSQL("insert into food_collection values(?,?,?,?,?,?,?,?,?)", new String[]{foodName, foodLocation, foodNum, foodPrice, year + "", month + "", day + "", hour + "", minute + ""});
        System.out.println("!!!!!插入成功");
    }
}
