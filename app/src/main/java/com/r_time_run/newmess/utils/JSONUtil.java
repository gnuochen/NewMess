package com.r_time_run.newmess.utils;

import android.util.Log;

import com.r_time_run.newmess.bean.FoodsBean;
import com.r_time_run.newmess.bean.ShopsBean;
import com.r_time_run.newmess.bean.SubcategoryBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nuochen on 2015/9/17.
 */
public class JSONUtil {
    public static ArrayList<String> getVersion(String json) {
        ArrayList<String> al = new ArrayList<String>();
        try {
            JSONObject js = new JSONObject(json);
            al.add(js.getString("versioncode"));
            al.add(js.getString("versionname"));
            al.add(js.getString("location"));
            al.add(js.getString("versioncontext"));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return al;
    }

    public static int getUpVersion(String json) {
        int state = 0;
        try {
            JSONObject js = new JSONObject(json);
            state = js.getInt("state");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return state;

    }

    /**
     * private int foods_id;
     * private String foods_name;
     * private String foods_image;
     * private double new_price;
     * private double old_price;
     * private double praise_scale;
     * private String create_time;
     * private String foods_location;
     *
     * @param json
     * @return
     */
    public static ArrayList<FoodsBean> getFoodsJson(String json) {
        ArrayList<FoodsBean> goodsBeans = new ArrayList<FoodsBean>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("foods");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                int foods_id = jsonObj.getInt("foods_id");
                String foods_image = jsonObj.getString("foods_image");
                String foods_name = jsonObj.getString("foods_name");
                double new_price = jsonObj.getDouble("new_price");
                double old_price = jsonObj.getDouble("old_price");
                double praise_scale = jsonObj.getDouble("praise_scale");
                String create_time = jsonObj.getString("create_time");
                String foods_location = jsonObj.getString("foods_location");
                FoodsBean goods = new FoodsBean(create_time, old_price, new_price, foods_name, foods_location, foods_image, foods_id);
                goodsBeans.add(goods);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("GGG", e.toString());
        }
        return goodsBeans;
    }

    public static ArrayList<ShopsBean> getShopsJson(String json) {
        ArrayList<ShopsBean> shopsBeans = new ArrayList<ShopsBean>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("shops");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                int shops_id = jsonObj.getInt("shops_id");
                String shops_image = jsonObj.getString("shops_image");
                String shops_name = jsonObj.getString("shops_name");
                String shops_describe = jsonObj.getString("shops_describe");
                String shops_location = jsonObj.getString("shops_location");
                double praise_scale = jsonObj.getDouble("praise_scale");
                String show_image1 = jsonObj.getString("show_image1");
                String show_image2 = jsonObj.getString("show_image2");
                String show_image3 = jsonObj.getString("show_image3");
                String show_image4 = jsonObj.getString("show_image4");
                ShopsBean shops = new ShopsBean(praise_scale, shops_describe, shops_id, shops_image, shops_location, shops_name, show_image1, show_image2, show_image3, show_image4);
                shopsBeans.add(shops);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("GGG", e.toString());
        }
        return shopsBeans;
    }

    public static ArrayList<SubcategoryBean> getSubcategoryBeanJson(String json) {
        ArrayList<SubcategoryBean> subcategoryBeans = new ArrayList<SubcategoryBean>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("shops");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                String mSubCategoryFoodKind = jsonObj.getString("mSubCategoryFoodKind");
                int shopID=jsonObject.getInt("shopID");
                String mSubCategoryFoodImage = jsonObj.getString("mSubCategoryFoodImage");
                String mSubCategoryFoodName = jsonObj.getString("mSubCategoryFoodName");
                String mSubCategoryFoodPrice = jsonObj.getString("mSubCategoryFoodPrice");
                String mSubCategoryFoodDeatil = jsonObj.getString("mSubCategoryFoodDeatil");
                SubcategoryBean subBeans = new SubcategoryBean(0, mSubCategoryFoodKind,shopID,
                        mSubCategoryFoodName, mSubCategoryFoodPrice, mSubCategoryFoodImage,mSubCategoryFoodDeatil);
                subcategoryBeans.add(subBeans);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("GGG", e.toString());
        }
        return subcategoryBeans;
    }
}
