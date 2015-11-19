package com.r_time_run.newmess.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.r_time_run.newmess.R;
import com.r_time_run.newmess.subactivity.SubcategoryActivity;

public class SubcategoryAdapterFirst extends BaseAdapter implements ListAdapter {

    private Context context;
    private LayoutInflater inflater;
    private String listName[];
    private SubcategoryActivity subcategoryActivity;
    int mSelect = 0;   //选中项

    public SubcategoryAdapterFirst(Context context, String listName[]) {
        this.context = context;
        inflater=LayoutInflater.from(context);
        this.listName=listName;

    }


    public void changeSelected(int positon){ //刷新方法
        if(positon != mSelect){
            mSelect = positon;
            Log.e("SubcategoryAdapterFirstSelected","mSelect= "+mSelect);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 8;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh=null;
        if (convertView == null) {
            vh = new ViewHolder();

            convertView = inflater.inflate(R.layout.subcategory_first_item,
                    null);
            vh.tv1 = (TextView) convertView.findViewById(R.id.tv_subcategory_first);

            Log.e("SubcategoryAdapterFirst","mSelect= "+mSelect);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }


        vh.tv1.setText(""+listName[position]);
        if(mSelect==position){
            vh.tv1.setBackgroundResource(R.drawable.subcategory_putdown);  //选中项背景
        }else{
            vh.tv1.setBackgroundResource(R.drawable.subcategory_backgroud);  //原始背景
        }
        return convertView;
    }
    class ViewHolder{
        TextView tv1;
    }

}