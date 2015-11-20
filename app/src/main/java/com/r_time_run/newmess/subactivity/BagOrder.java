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

/**
 * 订单详情
 */
public class BagOrder extends Activity {
    private TextView bt_bagorder_back;
    private ListView lv_bagorder;
    private View popup_bagOrderView;
    private PopupWindow popup_bagOrder;
    private List<Map<String, Object>> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bag_order);

        getDataFromServlet();           //从服务器中获取数据并填充到数组中
        initActivity();     //初始化订单详情页面


        //为listView添加适配器
        if (listItems != null){
            SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems,
                    R.layout.simple_item2,
                    new String[]{"image", "title", "dir", "time"},
                    new int[]{R.id.iv_mycollection_image, R.id.tv_mycollection_title, R.id.tv_mycollection_dir, R.id.tv_mycollection_time});
            lv_bagorder.setAdapter(simpleAdapter);
        }
        //为listView添加点击事件
        lv_bagorder.setOnItemClickListener(new ItemClickListener());

        //该popupWindow"页面"能接受点击事件
        popup_bagOrderView.setFocusable(true);
        popup_bagOrderView.setFocusableInTouchMode(true);
        popup_bagOrderView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (popup_bagOrder != null) {
                        popup_bagOrder.dismiss();
                    }
                }
                return false;
            }
        });
    }

    /**
     * 初始化订单详情界面
     */
    private void initActivity() {
        bt_bagorder_back = (TextView) findViewById(R.id.bt_bagorder_back);
        //返回键点击事件
        bt_bagorder_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BagOrder.this.finish();
            }
        });
        lv_bagorder = (ListView) findViewById(R.id.lv_bagorder);
        //没有订单时显示
        lv_bagorder.setEmptyView(findViewById(R.id.tv_empty_item));
        //订单视图
        popup_bagOrderView = this.getLayoutInflater().inflate(R.layout.popup_bag_order, null);
    }

    /**
     * 从服务器获取数据填写入该数组中
     */
    private void getDataFromServlet() {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString() + "/order_message_db.db3", null);
        try {
            Cursor cursor = db.rawQuery("select * from food_info", null);
            //SimpleAdapter中的list数据
            listItems = new ArrayList<>();
            while (cursor.moveToNext()) {
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("image", R.drawable.food);
                item.put("title", cursor.getString(0));
                item.put("dir", cursor.getString(1));
                item.put("buys", cursor.getString(2));
                item.put("prices",cursor.getString(3));
                item.put("time", cursor.getString(5)+"月"+cursor.getString(6)+"日 "+cursor.getString(7)+":"+cursor.getString(8));     //测试
                listItems.add(item);
            }
        } catch (SQLiteException se) {

        }
    }

    /**
     * listView的item点击事件
     */
    private class ItemClickListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            popup_bagOrder = new PopupWindow(popup_bagOrderView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            Bundle data = new Bundle();
            data.putString("title", (String) listItems.get(position).get("title"));
            data.putString("dir", (String) listItems.get(position).get("dir"));
            data.putString("time", (String) listItems.get(position).get("time"));
            data.putString("praise", (String) listItems.get(position).get("praise"));
            data.putString("buy", (String) listItems.get(position).get("buys"));
            data.putString("price", (String) listItems.get(position).get("prices"));
            PopupWindowManager popupWindowManager = new PopupWindowManager(BagOrder.this, popup_bagOrderView, data);
            popupWindowManager.setDataToView();

            ScrollView sv_bagDialog = (ScrollView) popup_bagOrderView.findViewById(R.id.sv_bagDialog);
            sv_bagDialog.startAnimation(AnimationUtils.loadAnimation(BagOrder.this, R.anim.my_info_in));
            popup_bagOrder.setFocusable(true);
            popup_bagOrder.showAtLocation(view, Gravity.BOTTOM, 0, 0);
            popup_bagOrderView.findViewById(R.id.bt_bagOrder_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popup_bagOrder.dismiss();
                }
            });

        }
    }

}
