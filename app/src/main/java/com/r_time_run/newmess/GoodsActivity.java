package com.r_time_run.newmess;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.r_time_run.newmess.subactivity.SubcategoryActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodsActivity extends AppCompatActivity {

    List<Map<String, Object>> listItems;
    ListView listView;

    private int[] imageIds=new int[]
            {
                    R.drawable.fantang1, R.drawable.fantang2, R.drawable.fantang3, R.drawable.fantang4
            };
    private String[] names=new String[]{"第一食堂","第二食堂","第三食堂","第四食堂"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);

        listView = (ListView) findViewById(R.id.listView);

        listItems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < imageIds.length; i++) {
            Map<String, Object> listItem = new HashMap<String, Object>();
            listItem.put("image", imageIds[i]);
            listItem.put("cantingName",names[i]);
            listItems.add(listItem);
        }


         listView.setAdapter(new MyAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent intent;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        intent=new Intent(GoodsActivity.this, SubcategoryActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(GoodsActivity.this, SubcategoryActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent=new Intent(GoodsActivity.this, SubcategoryActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent=new Intent(GoodsActivity.this, SubcategoryActivity.class);
                        startActivity(intent);
                        break;

                }
            }
        });


    }
    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return listItems.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=null;
            if (convertView==null) {
                LayoutInflater inflater = GoodsActivity.this.getLayoutInflater();
                view = inflater.inflate(R.layout.simple_item, null);

            }else {
                view = convertView;
            }
            ImageView imHeader = (ImageView) view.findViewById(R.id.header);
//            TextView tvName= (TextView) view.findViewById(R.id.tv_name);

            imHeader.setImageResource((Integer) listItems.get(position).get("image"));
//            tvName.setText("食堂名称: " + listItems.get(position).get("cantingName"));

            return view;
        }
    }
}
