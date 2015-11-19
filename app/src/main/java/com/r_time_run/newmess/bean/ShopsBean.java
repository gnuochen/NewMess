package com.r_time_run.newmess.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nuochen on 2015/9/18.
 */
public class ShopsBean implements Parcelable{
    private int shops_id;
    private String shops_name;
    private String shops_image;
    private Double praise_scale;
    private String shops_location;
    private String shops_describe;
    private String show_image1;
    private String show_image2;
    private String show_image3;
    private String show_image4;

    public void setPraise_scale(Double praise_scale) {
        this.praise_scale = praise_scale;
    }

    public void setShops_describe(String shops_describe) {
        this.shops_describe = shops_describe;
    }

    public void setShops_id(int shops_id) {
        this.shops_id = shops_id;
    }

    public void setShops_image(String shops_image) {
        this.shops_image = shops_image;
    }

    public void setShops_location(String shops_location) {
        this.shops_location = shops_location;
    }

    public void setShops_name(String shops_name) {
        this.shops_name = shops_name;
    }

    public void setShow_image1(String show_image1) {
        this.show_image1 = show_image1;
    }

    public void setShow_image2(String show_image2) {
        this.show_image2 = show_image2;
    }

    public void setShow_image3(String show_image3) {
        this.show_image3 = show_image3;
    }

    public void setShow_image4(String show_image4) {
        this.show_image4 = show_image4;
    }

    public static Creator<ShopsBean> getCREATOR() {

        return CREATOR;
    }

    public Double getPraise_scale() {
        return praise_scale;
    }

    public String getShops_describe() {
        return shops_describe;
    }

    public int getShops_id() {
        return shops_id;
    }

    public String getShops_image() {
        return shops_image;
    }

    public String getShops_location() {
        return shops_location;
    }

    public String getShops_name() {
        return shops_name;
    }

    public String getShow_image1() {
        return show_image1;
    }

    public String getShow_image2() {
        return show_image2;
    }

    public String getShow_image3() {
        return show_image3;
    }

    public String getShow_image4() {
        return show_image4;
    }

    public ShopsBean(Double praise_scale, String shops_describe, int shops_id, String shops_image,
                     String shops_location, String shops_name, String show_image1, String show_image2,
                     String show_image3, String show_image4) {
        this.praise_scale = praise_scale;
        this.shops_describe = shops_describe;
        this.shops_id = shops_id;
        this.shops_image = shops_image;
        this.shops_location = shops_location;
        this.shops_name = shops_name;
        this.show_image1 = show_image1;
        this.show_image2 = show_image2;
        this.show_image3 = show_image3;
        this.show_image4 = show_image4;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(shops_id);
        dest.writeString(shops_name);
        dest.writeString(shops_image);
        dest.writeString(shops_describe);
        dest.writeString(shops_location);
        dest.writeDouble(praise_scale);
        dest.writeString(show_image1);
        dest.writeString(show_image2);
        dest.writeString(show_image3);
        dest.writeString(show_image4);
    }
    public static final Parcelable.Creator<ShopsBean> CREATOR = new Parcelable.Creator<ShopsBean>(){

        @Override
        public ShopsBean createFromParcel(Parcel source) {
            return new ShopsBean(source);
        }

        @Override
        public ShopsBean[] newArray(int size) {
            return new ShopsBean[size];
        }

    };
    protected ShopsBean(Parcel in) {
        shops_id = in.readInt();
        shops_name = in.readString();
        shops_image = in.readString();
        shops_describe = in.readString();
        shops_location = in.readString();
        praise_scale = in.readDouble();
        show_image1= in.readString();
        show_image2 = in.readString();
        show_image3 = in.readString();
        show_image4 = in.readString();

    }
}
