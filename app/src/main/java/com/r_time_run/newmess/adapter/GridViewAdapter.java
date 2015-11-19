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

import com.r_time_run.newmess.bean.FoodsBean;

import java.util.ArrayList;
import com.r_time_run.newmess.R;
import com.r_time_run.newmess.net.LoadImage;
import com.r_time_run.newmess.subactivity.BabyActivity;

/**
 * Created by nuochen on 2015/9/16.
 */
public class GridViewAdapter extends BaseAdapter implements ListAdapter{
    private Context context;
    private ArrayList<FoodsBean> list;
    private LayoutInflater li;
    public GridViewAdapter(Context context,ArrayList<FoodsBean> list){
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
            convertView=li.inflate(R.layout.vp_item_one_gridview_foods, null);
            vh.iv_foods_image = (ImageView) convertView.findViewById(R.id.iv_foods_img);
            vh.tv_foods_name = (TextView) convertView.findViewById(R.id.tv_foods_name);
            vh.tv_foods_price = (TextView) convertView.findViewById(R.id.tv_foods_price);
            vh.tv_foods_praise = (TextView) convertView.findViewById(R.id.tv_foods_praise);
            convertView.setTag(vh);
        }else
            vh=(ViewHolder) convertView.getTag();

        FoodsBean bean=list.get(position);
        new LoadImage(context).loadDrawable(bean.getFoods_image(), vh.iv_foods_image);
        vh.tv_foods_name.setText(bean.getFoods_name());
        vh.tv_foods_price.setText("价格￥："+bean.getNew_price()+"元");
        vh.tv_foods_praise.setText(bean.getPraise_scale()+"赞");

        //设置图片的点击事件
        vh.iv_foods_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BabyActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("foodName","0");
                bundle.putString("foodLocation","0");
                bundle.putString("foodPrice","0");
                bundle.putIntArray("foodImageID",new int[]{0});
                bundle.putInt("subcategoryPosition",0);
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });
        return convertView;
    }
    class ViewHolder{
        ImageView iv_foods_image;
        TextView tv_foods_name;
        TextView tv_foods_praise;
        TextView tv_foods_price;
    }
    public void fillData(ArrayList<FoodsBean> list){
        this.list=list;
    }
    public void addData(ArrayList<FoodsBean> list){
        for (int i = 0;i<list.size();i++)
        this.list.add(list.get(i));
    }
}


