package com.r_time_run.newmess.subactivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.r_time_run.newmess.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BackOrder extends Activity {

    private TextView bt_backorder_back;
    private ListView lv_backorder;
    private SQLiteDatabase db;
    private List<Map<String, Object>> listItems;
    private SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_order);

        getDataFromServlet();           //从服务器中获取数据并填充到数组中
        initActivity();         //初始化界面


        if (listItems != null){
            simpleAdapter = new SimpleAdapter(this, listItems,
                    R.layout.simple_item2,
                    new String[]{"image", "title", "dir", "time"},
                    new int[]{R.id.iv_mycollection_image, R.id.tv_mycollection_title, R.id.tv_mycollection_dir, R.id.tv_mycollection_time});
            lv_backorder.setAdapter(simpleAdapter);
        }

        lv_backorder.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteOrder(position);
                return false;
            }
        });
    }

    private void initActivity() {
        bt_backorder_back = (TextView) findViewById(R.id.bt_backorder_back);
        lv_backorder = (ListView) findViewById(R.id.lv_backorder);
        lv_backorder.setEmptyView(findViewById(R.id.tv_empty_item));
        //返回键
        bt_backorder_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackOrder.this.finish();
            }
        });
    }

    /**
     * 长按订单之后取消订单
     *
     * @param position
     */
    private void deleteOrder(final int position) {
        final PopupMenu popupMenu = new PopupMenu(this, lv_backorder.getChildAt(position));
        getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:       //退订
                        AlertDialog.Builder builder = new AlertDialog.Builder(BackOrder.this);
                        builder.setTitle("");
                        builder.setMessage("是否退订");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Cursor cursor = db.rawQuery("select * from food_info"
                                        , null);
                                while (cursor.moveToNext()) {

                                    db.execSQL("delete from food_info where name=? and hour=? and minute=?",
                                            new String[]{(String) listItems.get(position).get("title"),
                                                    (String) listItems.get(position).get("hours"),
                                                    (String) listItems.get(position).get("minutes")});

                                }
                                listItems.remove(position);
                                simpleAdapter.notifyDataSetChanged();
                            }
                        });
                        builder.setNegativeButton("取消", null);
                        builder.show();
                        break;
                    case R.id.cancel:
                        popupMenu.dismiss();
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }


    private void getDataFromServlet() {
        /**
         * 从服务器获取数据填写入该数组中
         */
        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString() + "/order_message_db.db3", null);
        try {
            Cursor cursor = db.rawQuery("select * from food_info", null);
            listItems = new ArrayList<>();
            while (cursor.moveToNext()) {
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("image", R.drawable.food);
                item.put("title", cursor.getString(0));
                item.put("dir", cursor.getString(1));
                item.put("time", cursor.getString(5) + "月" + cursor.getString(6) + "日 " + cursor.getString(7) + ":" + cursor.getString(8));
                item.put("hours", cursor.getString(7));
                item.put("minutes", cursor.getString(8));
                listItems.add(item);
            }
        } catch (SQLiteException se) {

        }
    }
}
