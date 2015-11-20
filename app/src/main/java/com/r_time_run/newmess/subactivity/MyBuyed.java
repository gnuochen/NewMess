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

public class MyBuyed extends Activity {
    private TextView bt_buyed_back;
    private ListView lv_buyed;
    private PopupWindow popup_buys;
    private List<Map<String, Object>> listItems;
    private View popup_buysView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_buyed);

        getDataFromServlet();           //从服务器中获取数据并填充到数组中
        initActivity();

        if (listItems != null){
            SimpleAdapter simpleAdapter = new SimpleAdapter(this,listItems,
                    R.layout.simple_item2,
                    new String[]{"image","title","dir","time"},
                    new int[]{R.id.iv_mycollection_image, R.id.tv_mycollection_title, R.id.tv_mycollection_dir, R.id.tv_mycollection_time});
            lv_buyed.setAdapter(simpleAdapter);
        }

        lv_buyed.setOnItemClickListener(new ItemClickListener());

        /**
         * 该popupWindow"页面"能接受点击事件
         */
        popup_buysView.setFocusable(true);
        popup_buysView.setFocusableInTouchMode(true);
        popup_buysView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (popup_buys != null) {
                        popup_buys.dismiss();
                    }
                }
                return false;
            }
        });
    }

    private void initActivity() {
        bt_buyed_back = (TextView)findViewById(R.id.bt_buyed_back);
        lv_buyed = (ListView)findViewById(R.id.lv_buyed);
        lv_buyed.setEmptyView(findViewById(R.id.tv_empty_item));
        //返回键
        bt_buyed_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyBuyed.this.finish();
            }
        });
        popup_buysView = this.getLayoutInflater().inflate(R.layout.popup_my_buy,null);
    }

    private void getDataFromServlet() {
        /**
         * 从服务器获取数据填写入该数组中
         */
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString() + "/order_message_db.db3", null);
        try {
            Cursor cursor = db.rawQuery("select * from food_buyed", null);
            listItems = new ArrayList<>();
            while (cursor.moveToNext()) {
                Map<String,Object> item = new HashMap<String, Object>();
                item.put("image",R.drawable.food);
                item.put("title",cursor.getString(0));
                item.put("dir",cursor.getString(1));
                item.put("buys",cursor.getString(2));
                item.put("prices",cursor.getString(3));
                item.put("time",cursor.getString(5)+"月"+cursor.getString(6)+"日 "+cursor.getString(7)+":"+cursor.getString(8));
                listItems.add(item);
            }
        } catch (SQLiteException se) {

        }
    }

    private class ItemClickListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            popup_buys = new PopupWindow(popup_buysView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            Bundle data = new Bundle();
            data.putString("title", (String) listItems.get(position).get("title"));
            data.putInt("image", R.drawable.food);
            data.putString("dir", (String) listItems.get(position).get("dir"));
            data.putString("time", (String) listItems.get(position).get("time"));
            data.putString("praise", (String) listItems.get(position).get("praise"));
            data.putString("buy", (String) listItems.get(position).get("buys"));
            data.putString("price", (String) listItems.get(position).get("prices"));
            PopupWindowManager popupWindowManager = new PopupWindowManager(MyBuyed.this,popup_buysView,data);
            popupWindowManager.setDataToView();

            ScrollView sv_bagDialog = (ScrollView) popup_buysView.findViewById(R.id.sv_bagDialog);
            sv_bagDialog.startAnimation(AnimationUtils.loadAnimation(MyBuyed.this, R.anim.my_info_in));
            popup_buys.setFocusable(true);
            popup_buys.showAtLocation(view, Gravity.BOTTOM, 0, 0);
            popup_buysView.findViewById(R.id.bt_bagOrder_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popup_buys.dismiss();
                }
            });
        }
    }
}
