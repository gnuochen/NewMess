package com.r_time_run.newmess.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.r_time_run.newmess.FirstActivity;
import com.r_time_run.newmess.R;
import com.r_time_run.newmess.bean.ShopsBean;
import com.r_time_run.newmess.net.LoadImage;
import com.r_time_run.newmess.subactivity.ShopActivity;
import com.r_time_run.newmess.subactivity.SquareActivity;

import java.util.ArrayList;

/**
 * Created by nuochen on 2015/9/16.
 */
public class SquareViewAdapter extends BaseAdapter implements ListAdapter{
    private Context context;
    private ArrayList<String > list;
    private LayoutInflater li;
    public SquareViewAdapter(Context context, ArrayList<String> list){
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
            convertView=li.inflate(R.layout.activity_square_item, null);
            vh=new ViewHolder();
            convertView.setTag(vh);

//            convertView=li.inflate(R.layout.vp_item_three_listview_item, null);
//            vh.shop_image = (ImageView) convertView.findViewById(R.id.shop_image);
////            vh.tv_shop_name = (TextView) convertView.findViewById(R.id.tv_shop_name);
////            vh.tv_shop_praise = (TextView) convertView.findViewById(R.id.tv_shop_praise);
////            vh.tv_shop_decribe = (TextView) convertView.findViewById(R.id.tv_shop_decribe);
//            vh.iv_shop_into = (ImageView) convertView.findViewById(R.id.iv_shop_into);
//            vh.iv_shop_image1 = (ImageView) convertView.findViewById(R.id.shop_image1);
//            vh.iv_shop_image2 = (ImageView) convertView.findViewById(R.id.shop_image2);
//            vh.iv_shop_image3= (ImageView) convertView.findViewById(R.id.shop_image3);
//            vh.iv_shop_image4= (ImageView) convertView.findViewById(R.id.shop_image4);
//
//            convertView.setTag(vh);
        }else vh=(ViewHolder) convertView.getTag();
//
//        ShopsBean bean=list.get(position);
//        new LoadImage(context).loadDrawable(bean.getShops_image(), vh.shop_image);
////        vh.tv_shop_name.setText(bean.getShops_name());
////        vh.tv_shop_praise.setText(bean.getPraise_scale()+"赞");
////        vh.tv_shop_decribe.setText(bean.getShops_describe());
//        new LoadImage(context).loadDrawable(bean.getShow_image1(), vh.iv_shop_image1);
//        new LoadImage(context).loadDrawable(bean.getShow_image2(), vh.iv_shop_image2);
//        new LoadImage(context).loadDrawable(bean.getShow_image3(), vh.iv_shop_image3);
//        new LoadImage(context).loadDrawable(bean.getShow_image4(), vh.iv_shop_image4);
//        vh.iv_shop_into.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(context, ShopActivity.class);
//                context.startActivity(intent);
//            }
//        });
        return convertView;
    }
    class ViewHolder{
//        ImageView shop_image;
//        ImageView iv_shop_into;
//
//        ImageView iv_shop_image1;
//        ImageView iv_shop_image2;
//        ImageView iv_shop_image3;
//        ImageView iv_shop_image4;
    }
//    public void fillData(ArrayList<ShopsBean> list){
//        this.list=list;
//    }
    public void addData(ArrayList<String> list){
        for (int i = 0;i<list.size();i++){
            this.list.add(list.get(i));
        }

    }

}


