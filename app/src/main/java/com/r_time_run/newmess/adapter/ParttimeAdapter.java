package com.r_time_run.newmess.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.r_time_run.newmess.R;
import com.r_time_run.newmess.bean.ShopsBean;
import com.r_time_run.newmess.net.LoadImage;
import com.r_time_run.newmess.subactivity.ShopActivity;

import java.util.ArrayList;

/**
 * Created by nuochen on 2015/9/16.
 */
public class ParttimeAdapter extends BaseAdapter implements ListAdapter{
    private Context context;
    private ArrayList<String> list;
    private LayoutInflater li;
    ShopsBean bean;
    public ParttimeAdapter(Context context, ArrayList<String> list){
        this.context = context;
        this.list = list;
        li = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @SuppressWarnings("static-access")
    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        ViewHolder vh=null;
        if(convertView==null){
            vh=new ViewHolder();
            convertView=li.inflate(R.layout.parttime_list_item, null);
            vh.parttime_tile = (TextView) convertView.findViewById(R.id.part_time_tile);

            convertView.setTag(vh);
        }else
            vh=(ViewHolder) convertView.getTag();
        vh.parttime_tile.setText(list.get(position));

        return convertView;
    }
    class ViewHolder{
        TextView parttime_tile;
    }
    public void fillData(ArrayList<String > list){
        this.list=list;
    }
    public void addData(ArrayList<String > list){
        for (int i = 0;i<list.size();i++)
        this.list.add(list.get(i));
    }
}


