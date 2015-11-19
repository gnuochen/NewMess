package com.r_time_run.newmess.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nuochen on 2015/9/13.
 */
public class FoodsBean implements Parcelable{
    private int foods_id;
    private String foods_name;
    private String foods_image;
    private double new_price;
    private double old_price;
    private double praise_scale;

    private String create_time;
    private String foods_location;
    public FoodsBean(){super();}

    public double getPraise_scale() {
        return praise_scale;
    }

    public void setPraise_scale(double praise_scale) {
        this.praise_scale = praise_scale;
    }

    public String getCreate_time() {
        return create_time;
    }

    public int getFoods_id() {
        return foods_id;
    }

    public String getFoods_image() {
        return foods_image;
    }

    public String getFoods_location() {
        return foods_location;
    }

    public String getFoods_name() {
        return foods_name;
    }

    public double getNew_price() {
        return new_price;
    }

    public double getOld_price() {
        return old_price;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public void setFoods_id(int foods_id) {
        this.foods_id = foods_id;
    }

    public void setFoods_image(String foods_image) {
        this.foods_image = foods_image;
    }

    public void setFoods_location(String foods_location) {
        this.foods_location = foods_location;
    }

    public void setFoods_name(String foods_name) {
        this.foods_name = foods_name;
    }

    public void setNew_price(double new_price) {
        this.new_price = new_price;
    }

    public void setOld_price(double old_price) {
        this.old_price = old_price;
    }

    @Override
    public String toString() {
        return "FoodsBean{" +
                "create_time='" + create_time + '\'' +
                ", foods_id=" + foods_id +
                ", foods_name='" + foods_name + '\'' +
                ", foods_image='" + foods_image + '\'' +
                ", new_price=" + new_price +
                ", old_price=" + old_price +
                ", foods_location='" + foods_location + '\'' +
                '}';
    }

    public FoodsBean(String create_time, double old_price, double new_price, String foods_name, String foods_location, String foods_image, int foods_id) {
        this.create_time = create_time;
        this.old_price = old_price;
        this.new_price = new_price;
        this.foods_name = foods_name;
        this.foods_location = foods_location;
        this.foods_image = foods_image;
        this.foods_id = foods_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(create_time);
        dest.writeDouble(old_price);
        dest.writeDouble(new_price);
        dest.writeString(foods_name);
        dest.writeString(foods_location);
        dest.writeString(foods_image);
        dest.writeInt(foods_id);
    }

    public static final Parcelable.Creator<FoodsBean> CREATOR = new Parcelable.Creator<FoodsBean>(){

        @Override
        public FoodsBean createFromParcel(Parcel source) {
            return new FoodsBean(source);
        }

        @Override
        public FoodsBean[] newArray(int size) {
            return new FoodsBean[size];
        }

    };
    protected FoodsBean(Parcel in) {
        foods_id = in.readInt();
        foods_name = in.readString();
        foods_image = in.readString();
        new_price = in.readDouble();
        old_price = in.readDouble();
        create_time = in.readString();
        foods_location = in.readString();
    }
}
