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
    private String[] titles;    //标题
    private int[] images;       //图片
    private String[] dirs;      //地址
    private String[] times;     //打包时间
    private int index = 0;
    private SQLiteDatabase db;
    private List<Map<String, Object>> listItems;
    private SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_order);

        bt_backorder_back = (TextView) findViewById(R.id.bt_backorder_back);
        lv_backorder = (ListView) findViewById(R.id.lv_backorder);
        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString() + "/newmessfoodbag.db3", null);
        /**
         * 返回键
         */
        bt_backorder_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackOrder.this.finish();
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
        simpleAdapter = new SimpleAdapter(this,listItems,
                R.layout.simple_item2,
                new String[]{"image","title","dir","time"},
                new int[]{R.id.iv_mycollection_image, R.id.tv_mycollection_title, R.id.tv_mycollection_dir, R.id.tv_mycollection_time});
        lv_backorder.setAdapter(simpleAdapter);
        lv_backorder.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteOrder(position);
                return false;
            }
        });
    }

    /**
     * 长按订单之后取消订单
     * @param position
     */
    private void deleteOrder(final int position) {
        final PopupMenu popupMenu = new PopupMenu(this,lv_backorder.getChildAt(position));
        getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
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
                                int index = 0;
                                System.out.println("确定退订");
                                Cursor cursor = db.rawQuery("select * from food_info"
                                        , null);
                                while (cursor.moveToNext()) {
                                    if (index == position) {
                                        db.execSQL("delete from food_info where hour=? and minute=?",
                                                new String[]{cursor.getString(7), cursor.getString(8)});
                                    }
                                }
                                listItems.remove(position);
                                simpleAdapter.notifyDataSetChanged();
                            }
                        });
                        builder.setNegativeButton("取消",null);
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
        int cursorNum = 0;
        try {
            Cursor cursor = db.rawQuery("select * from food_info", null);
            cursorNum = cursor.getCount();
            titles = new String[cursorNum];
            images = new int[cursorNum];
            dirs = new String[cursorNum];
            times = new String[cursorNum];
            while (cursor.moveToNext()) {
                titles[index] = cursor.getString(0);
                dirs[index] = cursor.getString(1);
                times[index] = cursor.getString(5)+"月"+cursor.getString(6)+"日 "+cursor.getString(7)+":"+cursor.getString(8);
                images[index] = R.drawable.food;
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
    }

}
