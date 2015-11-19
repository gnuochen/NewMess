package com.r_time_run.newmess.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.r_time_run.newmess.R;
import com.r_time_run.newmess.bean.SubcategoryBean;
import com.r_time_run.newmess.net.LoadImage;

import java.util.ArrayList;
import java.util.List;

public class SubcategoryAdapter extends BaseAdapter implements ListAdapter {

    private Context context;
    private List<SubcategoryBean> list;
    private LayoutInflater inflater;
    private Boolean state;

    public SubcategoryAdapter(Context context, List<SubcategoryBean> list, Boolean state) {
        this.list=list;
        this.context = context;
        this.state=state;
        inflater=LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
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
            convertView = inflater.inflate(R.layout.subcategory_second_item,
                    null);
            vh.iv = (ImageView) convertView.findViewById(R.id.rightmenu_item_imageview);
            vh.tvFoodName = (TextView) convertView.findViewById(R.id.tv_subcategory_foodname);
            vh.tvFoodPrice = (TextView) convertView.findViewById(R.id.tv_subcategory_price);
            vh.tvFoodLocation = (TextView) convertView.findViewById(R.id.tv_subcategory_address);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

//        ShopsBean bean=list.get(position);
//        new LoadImage(context).loadDrawable(bean.getShops_image(), vh.shop_image);
//        vh.tv_shop_name.setText(bean.getShops_name());
//        vh.tv_shop_praise.setText(bean.getPraise_scale() + "赞");
//        vh.tv_shop_decribe.setText(bean.getShops_describe());
//        new LoadImage(context).loadDrawable(bean.getShow_image1(), vh.iv_shop_image1);
//        new LoadImage(context).loadDrawable(bean.getShow_image2(), vh.iv_shop_image2);
//        new LoadImage(context).loadDrawable(bean.getShow_image3(), vh.iv_shop_image3);
//        new LoadImage(context).loadDrawable(bean.getShow_image4(), vh.iv_shop_image4);

        SubcategoryBean bean =list.get(position);
        if (state==true) {
            new LoadImage(context).loadDrawable(bean.getmSubcategoryFoodImage(), vh.iv);
        }
        vh.iv.setBackgroundResource(bean.getmSubCategoryFoodImageView());
        vh.tvFoodName.setText(bean.getmSubCategoryFoodName());
        vh.tvFoodPrice.setText(bean.getmSubCategoryFoodPrice());
        vh.tvFoodLocation.setText(bean.getmSubCategoryFoodDeatil());
//        vh.tv1.setText(list.get(position).getmSubCategoryGoodname());
//        vh.tv2.setText( list.get(position).getmSubCategoryDeatil());
//        vh.iv.setImageResource(list.get(position).getmSubCategoryGoodImageView());
        return convertView;
    }
    class ViewHolder{
        ImageView iv;
        TextView tvFoodName;
        TextView tvFoodPrice;
        TextView tvFoodLocation;
    }
    public void fillData(ArrayList<SubcategoryBean> list){
        this.list=list;
    }
    public void addData(ArrayList<SubcategoryBean> list){
        for (int i = 0;i<list.size();i++)
            this.list.add(list.get(i));
    }

    public void adddata(ArrayList<SubcategoryBean> list2) {
        // TODO Auto-generated method stub
        list=list2;
    }

    public void clear() {
        // TODO Auto-generated method stub
        list=null;
    }

}